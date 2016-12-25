name := """pacttest"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"



lazy val calculationService = project.in(file("calculation"))
  .settings(
    name := """calculationService""",
    version := "1.0",
    scalaVersion := "2.12.1"
  )
  .settings(libraryDependencies ++= Seq(
    akkaHttp
  ))


lazy val scalaClient=project.in(file("scalaClient"))
    .settings(
      name := """scalaClient""",
      version := "1.0",
      scalaVersion := "2.12.1"
    )

lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.1"