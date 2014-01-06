resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0-SNAPSHOT")

addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.2")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")


