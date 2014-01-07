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

    TaskKey[Unit]("initialize-subprojects", "Initialize the workbench") := {
      val configuration = ConfigFactory.parseFile(new File("application.conf"))
      val projects = configuration.getObject("projects")

      for(subProject <- SubProjects) {
        subProject.init()
      }

    },

    InputKey[Unit]("pull") := {
      val args = spaceDelimited("<subProject> [upstream | origin]").parsed

      args.toList match {
        case subProject::"upstream"::Nil =>
          SubProjects.get(subProject).get.pullFromUpstream
        case subProject::"origin"::Nil =>
          SubProjects.get(subProject).get.pullFromOrigin
        case _ =>
          println("Usage: sbt pull <subProject> [upstream | origin]")
      }
    },

    InputKey[Unit]("push") := {
      val args = spaceDelimited("<subProject>").parsed

      args.toList match {
        case subProject::Nil =>
          SubProjects.get(subProject).get.pushToOrigin
        case _ => 
          println("Usage: sbt push <subProject>")
      }
    }

  )

  // Settings
  val customSettings = Defaults.defaultSettings ++ tasks

}
