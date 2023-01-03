name := "normalized-data-gen"
organization := "chasf"
version := "3.0"

scalaVersion := "2.12.14"

val sparkVersion = "3.1.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "com.google.cloud.spark" %% "spark-bigquery-with-dependencies" % "0.27.1"
  // "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.6" % "provided"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
