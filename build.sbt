name := "recommender-engine"
organization in ThisBuild := "com.aleksandarskrbic"
scalaVersion in ThisBuild := "2.13.1"
resolvers in ThisBuild += "Confluent Maven Repository" at "https://packages.confluent.io/maven/"

val basePath      = new java.io.File("").getAbsolutePath
val generatedAvro = "src/main/scala/avro"

lazy val global = project
  .in(file("."))
  .aggregate(producers, ingestion)

lazy val producers = (project in file("event-producer"))
  .settings(
    name := "event-producer",
    libraryDependencies ++= Seq(
      dependencies.akkaActors,
      dependencies.akkaStreams,
      dependencies.alpakkaKafka,
      dependencies.alpakkaCsv,
      dependencies.avro,
      dependencies.avroSerializer,
      dependencies.logback
    ),
    avroSpecificScalaSource in Compile := new java.io.File(basePath + "/event-producer/" + generatedAvro)
  )

lazy val ingestion = (project in file("ingestion-pipeline"))
  .settings(
    name := "ingestion-pipeline",
    libraryDependencies ++= Seq(
      dependencies.akkaActors,
      dependencies.akkaStreams,
      dependencies.alpakkaKafka,
      dependencies.alpakkaCassandra,
      dependencies.avro,
      dependencies.avroSerializer,
      dependencies.scalaLogging,
      dependencies.logback
    ),
    avroSpecificScalaSource in Compile := new java.io.File(basePath + "/ingestion-pipeline/" + generatedAvro)
  )

lazy val dependencies = new {
  val akkaVersion             = "2.6.0"
  val alpakkaVersion          = "2.0.2"
  val alpakkaCsvVersion       = "2.0.0-RC2"
  val alpakkaCassandraVersion = "2.0.0"
  val avroVersion             = "1.9.0"
  val confluentAvroVersion    = "5.3.0"
  val logbackVersion          = "1.2.2"
  val scalaLoggingVersion     = "3.9.2"

  val akkaActors       = "com.typesafe.akka"  %% "akka-actor-typed"              % akkaVersion
  val akkaStreams      = "com.typesafe.akka"  %% "akka-stream"                   % akkaVersion
  val alpakkaKafka     = "com.typesafe.akka"  %% "akka-stream-kafka"             % alpakkaVersion
  val alpakkaCassandra = "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % alpakkaCassandraVersion
  val alpakkaCsv       = "com.lightbend.akka" %% "akka-stream-alpakka-csv"       % alpakkaCsvVersion

  val avro           = "org.apache.avro" % "avro"                  % avroVersion
  val avroSerializer = "io.confluent"    % "kafka-avro-serializer" % confluentAvroVersion

  val logback      = "ch.qos.logback"             % "logback-classic" % logbackVersion
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging"  % scalaLoggingVersion
}

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

// add scala compiler options
// add docker sbt

/*avroSpecificSourceDirectories in Compile += (sourceDirectory in Compile).value / "resources" / "avro"
avroSpecificScalaSource in Compile := new java.io.File("myScalaSource")*/

/*
sourceGenerators in Compile += (avroScalaGenerate in Compile).taskValue
(avroScalaSource in Compile) := new java.io.File(s"${baseDirectory.value}/myoutputdir")
(avroSourceDirectories in Compile) += new java.io.File(s"${baseDirectory.value}/src/main/myavro")*/
