val commonDependencies = libraryDependencies ++= Seq(
  slf4j, logback
)

lazy val calculationService = project.in(file("calculation"))
  .settings(
    name := """calculationService""",
    version := "1.0",
    scalaVersion := "2.12.1"
  )
  .settings(libraryDependencies ++= Seq(
    akkaHttp,
    scalapact,scalatest
  ))
  .settings(commonDependencies)


lazy val scalaClient = project.in(file("scalaClient"))
  .settings(
    name := """scalaClient""",
    version := "1.0",
    scalaVersion := "2.12.1"
  )
  .settings(libraryDependencies ++= Seq(
    scalaj,
    scalapact, scalatest , json4sNative
  ))
  .settings(commonDependencies)

lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.0.1"
lazy val scalaj = "org.scalaj" %% "scalaj-http" % "2.3.0"
lazy val json4sNative = "org.json4s" %% "json4s-native" % "3.5.0" % "test"

lazy val logback = "ch.qos.logback" % "logback-classic" % "1.1.8"
lazy val slf4j = "org.slf4j" % "slf4j-api" % "1.7.22"

lazy val scalapact = "com.itv" %% "scalapact-scalatest" % "2.1.0" % "test"
lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.1" % "test"

val pactLocalCopy = taskKey[Unit]("copy pact to producer")

pactLocalCopy := {
  val pactsFiles: PathFinder = (target.value / "pacts") * "*.json"
  val destinationPath = baseDirectory.value / "pacts"
  pactsFiles.get.foreach { pact =>
    sbt.IO.copyFile(pact, (destinationPath / pact.getName))
  }
  streams.value.log.info(s"pact files copied to $destinationPath")
}