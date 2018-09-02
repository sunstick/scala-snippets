name := "scala-snippets"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.5",
  "de.javakaffee" % "kryo-serializers" % "0.42",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)