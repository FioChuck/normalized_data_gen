# TLl;DR

A simple Scala Spark application that generates random mock data with a normal distribution. Next the data is written to BigQuery using the Apache Spark SQL connector for Google BigQuery. See connector details here: https://github.com/GoogleCloudDataproc/spark-bigquery-connector.

# Setup

This Scala project is designed to be built/deployed as a fat Jar file. A yaml file is included in `.github/workflows/` to automate build and deployment with Github Actions.

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
