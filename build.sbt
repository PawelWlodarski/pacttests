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
    akka, akkaStream,akkaHttp,sprayJson,akkaXml,
    scalapact,scalatest,pactcirce,pacthttp4s
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
    scalapact, scalatest,pactcirce,pacthttp4s
    , json4sNative
  ))
  .settings(commonDependencies)



lazy val akka = "com.typesafe.akka" %% "akka-actor" % "2.5.13"
lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.13"

lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.3"
lazy val sprayJson="com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3"
lazy val akkaXml="com.typesafe.akka" %% "akka-http-xml" % "10.1.3"
lazy val xml="org.scala-lang.modules" %% "scala-xml" % "1.1.0"

lazy val scalaj = "org.scalaj" %% "scalaj-http" % "2.4.0"
lazy val json4sNative = "org.json4s" %% "json4s-native" % "3.5.4" % "test"

lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
lazy val slf4j = "org.slf4j" % "slf4j-api" % "1.7.25"

lazy val scalapact = "com.itv" %% "scalapact-scalatest" % "2.3.0-RC1" % "test"
lazy val pactcirce = "com.itv"       %% "scalapact-circe-0-9"     % "2.3.0-RC1" % "test"
lazy val pacthttp4s= "com.itv"       %% "scalapact-http4s-0-18"   % "2.3.0-RC1" % "test"
lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.5" % "test"

val pactLocalCopy = taskKey[Unit]("copy pact to producer")

pactLocalCopy := {
  val pactsFiles: PathFinder = (target.value / "pacts") * "*.json"
  val destinationPath = baseDirectory.value / "pacts"
  if(destinationPath.exists()) destinationPath.listFiles().foreach(_.delete())
  pactsFiles.get.foreach { pact =>
    sbt.IO.copyFile(pact, (destinationPath / pact.getName))
  }
  streams.value.log.info(s"pact files copied to $destinationPath")
}


/// PACT BROKER - https://github.com/ehdez73/docker-pact-broker
//pactBrokerAddress := "http://localhost"
//pactContractVersion := "2.0.0"
//

//first example
//providerName := "uslugaZewnetrzna"
//consumerNames := Seq("tenTutajKonsument")

//service client
//providerName := "calculation service"
//consumerNames := Seq("scala client")