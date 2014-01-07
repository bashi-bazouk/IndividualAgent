import scala.collection.mutable.HashMap
import scala.collection.Set
import scala.io._

import sbt._
import scala.collection.JavaConverters._
import com.typesafe.config._

import java.io._

class SubProject(val name: String, val origin: String, val upstream: String, val branch: String) {

  def init() {

    (s"rm -r $name/" !) 

    def gitignoreProjectFolder(name: String) {
      for(line <- Source.fromFile(".gitignore").getLines) {
        if(line.equals(s"/$name/")) {
          return
        }
      }
      (s"""echo /$name/""" #>> file(".gitignore") !)
    }

    gitignoreProjectFolder(name)

    Git.remoteAdd(s"${name}-origin", origin)
    Git.remoteAdd(s"${name}-upstream", upstream)
    Git.add("-A")
    Git.commit(s"clean commit before adding subtree $name.")
    Git.subtreeAdd(name, s"${name}-origin", branch)
    Git.remove(name)

  }

  def pullFromOrigin() = Git.subtreePull(name, origin, branch)

  def pullFromUpstream() = Git.subtreePull(name, upstream, branch)

  def pushToOrigin() {}

}

object SubProjects extends Iterable[SubProject] {
  private val subProjects = new HashMap[String, SubProject]()

  private val projectsObject = ConfigFactory.parseFile(new File("application.conf")).getObject("projects")
  for(name <- projectsObject.keySet.asScala) {
    var origin: Option[String] = None
    var upstream: Option[String] = None
    var branch: Option[String] = None
    for((k,v) <- projectsObject.get(name).unwrapped.asInstanceOf[java.util.Map[String,String]].asScala) {
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

    subProjects.put(name, new SubProject(name, origin.get, upstream.get, branch.get))
  }

  def get(name: String) = subProjects.get(name)

  def iterator(): Iterator[SubProject] = subProjects.valuesIterator

  def keySet(): Set[String] = subProjects.keySet

}
