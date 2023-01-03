import scala.math.random
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.functions._

object BqDemo {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName("Bq Demo")
      // .config("spark.master", "local[*]")
      // .config(
      //   "spark.hadoop.fs.AbstractFileSystem.gs.impl",
      //   "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS"
      // )
      // .config("spark.hadoop.fs.gs.project.id", "cf-data-analytics")
      // .config("spark.hadoop.google.cloud.auth.service.account.enable", "true")
      // .config(
      //   "spark.hadoop.google.cloud.auth.service.account.json.keyfile",
      //   "/Users/chasf/Desktop/cf-data-analytics-1ff73e9e3f7a.json"
      // )
      .getOrCreate()

    val df = spark.sqlContext
      .range(0, 1000000001)

    val df2 = df
      .withColumn("even_distribution", rand(seed = 10))
      .withColumn("normal_distribution", randn(seed = 10))

    df2.describe().show()

    print("test")

    // val df =
    //   (spark.read
    //     .format("bigquery")
    //     .option("table", "bigquery-public-data.stackoverflow.users")
    //     .load()
    //     .cache())

    df2.printSchema()

    df2.write
      .format("bigquery")
      .option("temporaryGcsBucket", "cf-spark-temp")
      .mode("overwrite")
      .save("cf-data-analytics.dataproc.distribution")
  }
}
