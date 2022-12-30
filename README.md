# TLl;DR

A simple Scala Spark application that generates random mock data with a normal distribution. The resulting mock data is written to BigQuery using the Apache Spark SQL connector for Google BigQuery. See connector details [here](https://github.com/GoogleCloudDataproc/spark-bigquery-connector).

# Setup

This Scala project is designed to run as a standalone fat Jar. A yaml file in the `.github/workflows/` folder automate the assembly process using Github Actions and the [sbt](https://www.scala-sbt.org/) build tool _(compile and assemble uber Jar)_. This deployment script requires a single Github repo secret _(listed below)_.

| Action Secret | Value                                                          |
| ------------- | -------------------------------------------------------------- |
| GCP_SA_KEY    | Service Account Key used to authenticate GitHub to GCP Project |

# Local Development

When developing locally using [Metals](https://scalameta.org/metals/) or IntelliJ, credentials must be available for authentication to BigQuery. Set the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to the service account key json file path. More info [here](https://cloud.google.com/docs/authentication/application-default-credentials).

> //TODO switch credential configuration file for workload identity federation.

```bash
export GOOGLE_APPLICATION_CREDENTIALS=/Users/chasf/df-credentials.json
```

Next the master URL for the cluster must be defined as _local_. Make sure the .config parameters are commented out when deploying to productino.

_Ex:_

```scala
 val spark = SparkSession.builder
      .appName("Spark Pi")
      .config("spark.master", "local[*]") // comment out when deploying
      .getOrCreate()
```

The `local[*]` configuration sets the worker threads equal to the logical cores on your machine. More information here: https://spark.apache.org/docs/latest/submitting-applications.html#master-urls.

# Reference

This application was build using several articles as reference. These sources are listed below.

## GCS Actions

https://github.com/google-github-actions/upload-cloud-storage

This library was used in the deployment script to move the fat Jar into Google Cloud Storage. The Jar can be deployed to Dataproc using `gcloud`.

_For example_:

```shell
gcloud dataproc jobs submit spark
```

Considerations:

1. Turn off gzip content encoding. Spark isn't configured to read compressed Jar files.

```yaml
gzip: false
```

2. Specify a Glob Pattern _(wildcard)_ to reference the latest version number declared in `build.sbt`.

```yaml
glob: "*.jar"
```

## Setup Java Runner for SBT Deployment

https://github.com/actions/setup-java

This repo documents yaml instructions for setting up a Java runner. The sbt Scala build takes place on the Java runner.

## Configure SBT Build

These two articles document `build.sbt` and `plugins.sbt` configurations.

GCP instructions for setting up SBT:

https://cloud.google.com/dataproc/docs/guides/manage-spark-dependencies

Article where SBT was used with Github Actions to deploy uber Jars to S3:

https://medium.com/alterway/building-a-ci-cd-pipeline-for-a-spark-project-using-github-actions-sbt-and-aws-s3-part-1-c7d43658832d
