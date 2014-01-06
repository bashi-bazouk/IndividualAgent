import sbt._
import Keys._
import complete.DefaultParsers._
import com.typesafe.config._
import com.typesafe.config.ConfigException._
import scala.collection.JavaConverters._
import scala.io._

import java.net._
import java.io._

object IABuild extends Build {

  // Project Definitions
  lazy val ia = Project(id = "ia", base = file("."), settings = customSettings) aggregate(specialk, diesel, gloseval)
  lazy val specialk = Project(id = "specialk", base = file("specialk"))
  lazy val diesel = Project(id = "diesel", base = file("diesel")) dependsOn(specialk)
  lazy val gloseval = Project(id = "gloseval", base = file("gloseval")) dependsOn(diesel)

  // Simple Commands
  val startMongoDB = "pgrep mongod" #|| "mongod"
  val startRabbitMQ = "pgrep epmd" #|| "rabbitmq-server"

  // Tasks
  val tasks = Seq(

    InputKey[Unit]("start") := {
      val args = spaceDelimited("""[rabbit | mongo | server | client]"""").parsed

      def startDiesel() {
        println("Starting server.")
        (startMongoDB ### startRabbitMQ).run;
        "sbt diesel/run" ! }

      args.toList match {
        case "rabbit"::Nil =>
	  startRabbitMQ.run
	case "mongo"::Nil =>
	  startMongoDB.run
        case "client"::Nil =>
          println("Starting client.")
          "sbt gloseval/run" !
        case "server"::Nil =>
          startDiesel
        case _ =>
          println("""Type: sbt "start [rabbit | mongo | server | client]"""")
          startDiesel
      }
    },

    TaskKey[Unit]("configure", "Configures Individual Agent") := {
      def query(prompt: String): String = { print(prompt + ": "); readLine }
      def queryOrElse(prompt: String, default: String): String = {
        print(prompt + s"[$default]: ")
        readLine match {
          case "" => default
          case res => res
        }
      }

      val config = ConfigFactory.parseMap(Map(
        "DSLCommLinkClientHost" -> queryOrElse("Input your static IP address", InetAddress.getLocalHost.getHostAddress), 
        "DLSCommLinkClientPort" -> queryOrElse("Input an open port to run on: ", "5676"), 
	"DSLCommLinkServerHost" -> query("Input the remote IP address"), 
	"DSLCommLinkClientPort" -> queryOrElse("Enter the remote port", "5676")).asJava)

      val renderOptions = ConfigRenderOptions.defaults().setJson(false).setOriginComments(false)

      def writeConfig(file: String) {
        val subConfig = config withFallback(ConfigFactory.parseFile(new File(file)))
	val writer = new PrintWriter(file)
	writer.print(subConfig.root.render(renderOptions))
	writer.close()
      }

      writeConfig("diesel/eval.conf")
      writeConfig("gloseval/eval.conf")
    },

    TaskKey[Unit]("build-local", "Initialize the workbench") := {
      val configuration = ConfigFactory.parseFile(new File("application.conf"))
      val projects = configuration.getObject("projects")

      ("rm -r specialk/ gloseval/ diesel/" !)

      for(name <- projects.keySet.asScala) {
        println("processing name " + name)
        if(!Path(name).exists || name.equals("diesel") || name.equals("gloseval") || name.equals("specialk")) {
          println("name doesn't exist")

          var origin: Option[String] = None
          var upstream: Option[String] = None
          var branch: Option[String] = None
          for((k,v) <- projects.get(name).unwrapped.asInstanceOf[java.util.Map[String,String]].asScala) {
            k.toString match {
              case "origin" =>
                origin = Some(v)
              case "upstream" =>
                upstream = Some(v)
              case "branch" =>
                branch = Some(v)
              case _ => ;
            }
          }

          if(origin.isEmpty) {
            throw new RuntimeException(s"Project $name must specify an origin.")
          } else {
            if(upstream.isEmpty)
              upstream = origin
            if(branch.isEmpty)
              branch = Some("master")
          }

          def gitignoreProjectFolder(name: String) {
            for(line <- Source.fromFile(".gitignore").getLines) {
              if(line.equals(s"/$name/")) {
                return
              }
            }
            (s"""echo /$name/""" #>> file(".gitignore") !)
          }

          gitignoreProjectFolder(name)

          Git.remoteAdd(s"${name}-origin", origin.get)
          Git.remoteAdd(s"${name}-upstream", upstream.get)
          Git.add("-A")
          Git.commit(s"clean commit before adding subtree $name.")
          Git.subtreeAdd(name, branch.get)
          Git.remove(name)
          println("done processing " + name)
        } else {
          println("name " + name + " already exists")
        }
      }

    }

  )

  // Settings
  val customSettings = Defaults.defaultSettings ++ tasks

}
