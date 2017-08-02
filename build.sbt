name := "goingok-server"
version := "1.0.1"
scalaVersion := "2.12.3"
organization := "org.goingok"

//Enable this only for local builds - disabled for Travis
//enablePlugins(JavaAppPackaging)

//Scala library versions
val akkaVersion = "2.5.3"
val akkaStreamVersion = "2.5.3"
val akkaHttpVersion = "10.0.9"
val json4sVersion = "3.5.3"
val slickVersion = "3.2.1"
val slickpgVersion = "0.15.3"
val slf4jVersion = "1.7.25"
//Java library versions
val googleClientApiVersion = "1.22.0"
val postgresDriverVersion = "42.1.4"

//Akka
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-stream_2.12" % akkaStreamVersion,
  "com.typesafe.akka" % "akka-stream-testkit_2.12" % akkaStreamVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
)
//Google dependencies
libraryDependencies += "com.google.api-client" % "google-api-client" % googleClientApiVersion
//Sessions
libraryDependencies ++= Seq(
  "com.softwaremill.akka-http-session" %% "core" % "0.5.1",
  "com.softwaremill.akka-http-session" %% "jwt"  % "0.5.1"
)

//Slick
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % postgresDriverVersion,
  "com.github.tminglei" %% "slick-pg" % slickpgVersion,
  "com.github.tminglei" %% "slick-pg_json4s" % slickpgVersion
)
//General
libraryDependencies ++= Seq(
  "io.nlytx" %% "commons" % "0.1.1",
//  "com.typesafe" % "config" % "1.3.1",
    "org.json4s" %% "json4s-jackson" % json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % "1.17.0",
//  "org.scalactic" %% "scalactic" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.slf4j" % "jcl-over-slf4j" % slf4jVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/src/main/scala/root-doc.md")

resolvers += Resolver.bintrayRepo("nlytx", "nlytx_commons")

//Generate build info file
//lazy val root = (project in file(".")).
enablePlugins(BuildInfoPlugin)
  buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
  buildInfoPackage := "org.goingok"
  buildInfoOptions += BuildInfoOption.BuildTime
