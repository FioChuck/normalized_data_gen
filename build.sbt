name := "normalized-data-gen"
organization := "chasf"
version := "3.0"

scalaVersion := "2.12.14"

val sparkVersion = "3.1.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql_2.11" % sparkVersion % "provided",
  "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.6" % "provided",
  "com.google.cloud.spark" %% "spark-bigquery-with-dependencies" % "0.27.1"
)

assemblyShadeRules in assembly := Seq(
  ShadeRule
    .rename("com.google.common.**" -> "repackaged.com.google.common.@1")
    .inAll
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}

// assemblyMergeStrategy in assembly := {
//   case PathList("org", "apache", xs @ _*) => MergeStrategy.last
//   case PathList("com", "google", xs @ _*) => MergeStrategy.last
//   case x =>
//     val oldStrategy = (assemblyMergeStrategy in assembly).value
//     oldStrategy(x)
// }

// lazy val commonSettings = Seq(
//   organization := "chasf",
//   name := "normalized-data-gen",
//   version := "3.0",
//   scalaVersion := "2.12.14"
// )

// lazy val shaded = (project in file("."))
//   .settings(commonSettings)

// mainClass in (Compile, packageBin) := Some("BqDemo")

// libraryDependencies ++= Seq(
//   "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
//   "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
//   "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
//   "com.google.cloud.spark" %% "spark-bigquery-with-dependencies" % "0.27.1",
//   "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.6" % "provided"
// )

// assemblyShadeRules in assembly := Seq(
//   ShadeRule
//     .rename("com.google.common.**" -> "repackaged.com.google.common.@1")
//     .inAll
// )

// assemblyMergeStrategy in assembly := {
//   case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//   case x                             => MergeStrategy.first
// }
