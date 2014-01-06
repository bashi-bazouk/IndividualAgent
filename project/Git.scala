import sbt._
import java.util.Date

object Git {

  def add(files: String*) {
    (s"""git add ${files.mkString(" ")}""" !)
  }

  def commit(message: String) {
    Process("git"::"commit"::"-m"::s"""\"$message\""""::Nil) !
  }

  def remoteAdd(name: String, location: String) {
    Process("git"::"remote"::"add"::"-f"::name::location::Nil) !
  }

  def subtreeAdd(name: String, branch: String) {
    (s"git subtree add --prefix $name ${name}-upstream/$branch --squash" !)
  }

  def remove(name: String) {
    (s"git rm -r --cached $name" !)
  }

}
