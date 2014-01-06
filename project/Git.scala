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

  def subtreeAdd(prefix: String, remote: String, branch: String) {
    (s"git subtree add --prefix $prefix ${remote}/$branch --squash" !)
  }

  def subtreePull(prefix: String, remote: String, branch: String) {
    (s"git fetch $remote $branch" !)
    (s"git subtree pull --prefix $prefix $remote $branch --squash" !)
  }

  def remove(name: String) {
    (s"git rm -r --cached $name" !)
  }

}
