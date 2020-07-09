enablePlugins(JlinkPlugin)

name := "homeschool"
organization := "objektwerks"
version := "0.4-SNAPSHOT"
scalaVersion := "2.13.2"
maintainer := "objektwerks@runbox.com"
libraryDependencies ++= {
  val slickVersion = "3.3.2"
  Seq(
    "org.scalafx" %% "scalafx" % "14-R19",
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.h2database" % "h2" % "1.4.200",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.scalatest" %% "scalatest" % "3.2.0" % Test
  )
}
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m => "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName )
jlinkModules := {
  jlinkModules.value :+ "jdk.unsupported"
}
jlinkIgnoreMissingDependency := JlinkIgnore.everything