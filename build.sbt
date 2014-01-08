
scalaVersion := "2.10.3"

sbtVersion := "0.13.1"

organization := "net.softarc"

name := "individual-agent"

version := "1.0"

seq(Revolver.settings: _*)

autoCompilerPlugins := true
 
addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.1")
 
scalacOptions += "-P:continuations:enable"
