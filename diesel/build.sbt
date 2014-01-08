checksums := Nil

scalaVersion := "2.10.3"

organization := "com.protegra-ati"

name := "agentservices-store-ia"

version := "1.9.2-SNAPSHOT"

resolvers ++= Seq(
  "scala-tools.org" at "https://oss.sonatype.org/content/groups/scala-tools/",
  "biosim" at "http://biosimrepomirror.googlecode.com/svn/trunk",
  "spy" at "http://files.couchbase.com/maven2/",
  "basex" at "http://files.basex.org/maven")

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.0.0",
  "org.scala-lang" % "scala-library" % "2.10.2",
  "org.scala-lang" % "scala-actors" % "2.10.2",
  "org.scala-lang" % "scala-compiler" % "2.10.2",
  "org.scala-lang" % "jline" % "2.10.2",
  "org.scala-lang" % "scala-reflect" % "2.10.2",
  "com.biosimilarity.lift" %% "specialk" % "1.1.8.0",
  "it.unibo.alice.tuprolog" % "tuprolog" % "2.1.1",
  "org.prolog4j" % "prolog4j-api" % "0.2.1-SNAPSHOT",
  "org.prolog4j" % "prolog4j-tuprolog" % "0.2.1-SNAPSHOT",
  "net.lag" % "configgy" % "local",
  "org.basex" % "basex-api" % "7.6",
  "org.mongodb" %% "casbah" % "2.5.0",
  "org.json4s" % "json4s-jackson_2.10" % "3.2.4",
  "org.json4s" % "json4s-native_2.10" % "3.2.4",
  "org.scala-lang" % "scala-pickling_2.10" % "0.8.0-Local",
  "com.thoughtworks.xstream" % "xstream" % "1.4.4",
  "biz.source_code" % "base64coder" % "2010-09-21",
  "org.xmldb" % "xmldb-api" % "1.0",
  "log4j" % "log4j" % "1.2.16",
  "org.apache.ws.commons.util" % "ws-commons-util" % "1.0.2",
  "commons-pool" % "commons-pool" % "1.6",
  "spy" % "spymemcached" % "2.8.4",
  "jlex" % "JLex-local" % "local",
  "cup" % "java-cup-11a" % "local",
  "cup" % "java-cup-11a-runtime" % "local",
  "com.rabbitmq" % "amqp-client" % "3.2.2",
  "org.codehaus.jettison" % "jettison" % "1.3.3",
  "org.scalaxb" % "scalaxb" % "local-update",
  "org.scalesxml" % "scales-xml_2.10" % "0.4.5",
  "org.scalesxml" % "scales-jaxen_2.10" % "0.4.5" exclude("jaxen", "jaxen"),
  "junit" % "junit" % "4.8.1",
  "org.specs2" % "specs2_2.10" % "1.12.3",
  "org.scalatest" % "scalatest_2.10" % "2.0.M5b")

autoCompilerPlugins := true

libraryDependencies +=
    compilerPlugin("org.scala-lang.plugins" % "continuations" % scalaVersion.value)

scalacOptions += "-P:continuations:enable"
