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
      .appName("Spark Pi")
      .config("spark.master", "local[*]")
      .getOrCreate()

    val df = spark.sqlContext
      .range(0, 750000000)

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

    // val df2 = df.limit(10).select("display_name")

    // df2.show()

    // print("total row count: " + df2.count())

    df2.printSchema()

    // .option("writeMethod", "direct")

    df2.write
      .format("bigquery")
      // .option("writeMethod", "direct")
      .option("temporaryGcsBucket", "gs://cf-spark-temp")
      .mode("overwrite")
      .save("cf-data-analytics.dataproc.distribution")

  }
}
