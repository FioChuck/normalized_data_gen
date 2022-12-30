import scala.math.random
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructType

object BqDemo {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName("Spark Pi")
      // .config("spark.master", "local[*]")
      .getOrCreate()

    val df =
      (spark.read
        .format("bigquery")
        .option("table", "bigquery-public-data.stackoverflow.users")
        .load()
        .cache())

    val df2 = df.limit(10).select("display_name")

    df2.show()

    print("total row count: " + df2.count())

    df2.printSchema()

    df2.write
      .format("bigquery")
      .option("writeMethod", "direct")
      .mode("overwrite")
      .save("cf-data-analytics.dataproc.strng")
  }
  println("Test Result Number 15")

}
