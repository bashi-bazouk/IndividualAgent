checksums := Nil

scalaVersion := "2.10.2"

organization := "com.biosimilarity.lift"

name := "specialk"

version := "1.1.8.0"

resolvers ++= Seq(
  "basex" at "http://files.basex.org/maven",
  "biosim" at "http://biosimrepomirror.googlecode.com/svn/trunk",
  "codehaus.org" at "http://repository.codehaus.org/com/thoughtworks",
  "eaio.com" at "http://eaio.com/maven2",
  "gwt.rewraps" at "http://gwt-maven-rewraps.googlecode.com/hg/",
  "ibiblio" at "http://mirrors.ibiblio.org/pub/mirrors/maven2",
  "milton" at "http://milton.io/maven/com/ettrema/milton",
  "scala-tools.releases" at "http://scala-tools.org/repo-releases",
  "scala-tools" at "http://scala-tools.org/repo-snapshots",
  "scalesxml.com" at "http://scala-scales.googlecode.com/svn/repo",
  "sonatype" at "https://oss.sonatype.org/content/repositories/Snapshots",
  "xqj" at "http://xqj.net/maven"
)

libraryDependencies ++= Seq(
  "com.google.zxing" % "core" % "2.0",
  "org.mongodb" % "casbah_2.10" % "2.5.1",
  "org.json4s" % "json4s-jackson_2.10" % "3.2.4",
  "biz.source_code" % "base64coder" % "2010-09-21",
  "org.coconut.forkjoin" % "jsr166y" % "070108",
  "com.h2database" % "h2" % "1.2.138",
  "junit" % "junit" % "4.7",
  "org.specs2" % "specs2_2.10" % "1.14",
  "org.scalatest" % "scalatest_2.10" % "2.0.M5b",
  "org.slf4j" % "slf4j-log4j12" % "1.6.1",
  "org.eclipse.jetty.orbit" % "javax.servlet.jsp" % "2.2.0.v201112011158",
  "org.eclipse.jetty" % "jetty-server" % "8.0.4.v20111024",
  "org.eclipse.jetty" % "jetty-http" % "8.0.4.v20111024",
  "org.eclipse.jetty" % "jetty-servlets" % "8.0.4.v20111024",
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.4.v20111024",
  "org.eclipse.jetty" % "jetty-websocket" % "8.0.1.v20110908",
  "com.typesafe" % "config" % "1.0.0",
  "org.prolog4j" % "prolog4j-api" % "0.2.1-SNAPSHOT",
  "org.prolog4j" % "prolog4j-tuprolog" % "0.2.1-SNAPSHOT",
  "jlex" % "JLex-local" % "local",
  "cup" % "java-cup-11a" % "local",
  "cup" % "java-cup-11a-runtime" % "local",
  "com.rabbitmq" % "amqp-client" % "2.6.1",
  "org.codehaus.jettison" % "jettison" % "1.3",
  "com.google.code.gson" % "gson" % "2.1",
  "xpp3" % "xpp3_min" % "1.1.4c",
  "com.thoughtworks.xstream" % "xstream" % "1.4.4",
  "org.scalaxb" % "scalaxb" % "local-update",
  "javax.persistence" % "persistence-api" % "1.0",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "org.apache.ws.commons.util" % "ws-commons-util" % "1.0.2",
  "commons-pool" % "commons-pool" % "1.6",
  "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.2",
  "org.apache.xmlrpc" % "xmlrpc-common" % "3.1.2",
  "org.apache.xmlrpc" % "xmlrpc-server" % "3.1.2",
  "org.apache.httpcomponents" % "httpcore" % "4.2.4",
  "org.apache.httpcomponents" % "httpcore-nio" % "4.2.4",
  "org.apache.httpcomponents" % "httpasyncclient" % "4.0-beta3",
  "org.xmldb" % "xmldb-api" % "1.0",
  "javax.xml.xquery" % "xqj-api" % "1.0",
  "org.basex" % "basex-api" % "7.6",
  "com.sleepycat" % "db" % "local",
  "org.jivesoft" % "smack" % "local",
  "org.jivesoft" % "smackx" % "local",
  "org.hypergraphdb" % "hgdbfull" % "local",
  "net.lag" % "configgy" % "local",
  "log4j" % "log4j" % "1.2.16",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.scalesxml" % "scales-xml_2.10" % "0.4.5",
  "org.scalesxml" % "scales-jaxen_2.10" % "0.4.5" exclude("jaxen", "jaxen"),
  "org.ow2.sirocco.vmm" % "sirocco-vmm-agent-monitoring-driver-collectd" % "0.7.1",
  "org.scala-lang" % "scala-library" % "2.10.2",
  "org.scala-lang" % "scala-actors" % "2.10.2",
  "org.scala-lang" % "scala-compiler" % "2.10.2", 
  "org.scala-lang" % "jline" % "2.10.2",
  "org.scala-lang" % "scala-reflect" % "2.10.2"
)
