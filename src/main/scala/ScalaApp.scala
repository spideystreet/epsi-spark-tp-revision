import org.apache.spark.sql.{SparkSession, functions}

object ScalaApp {
  def main(args: Array[String]): Unit = {
    // Create a SparkSession
    val spark = SparkSession.builder()
      .appName("TP Spark Scala - Ventes")
      .master("local[*]") // Use local machine as the cluster
      .getOrCreate()

    // Import spark implicits for '$' syntax
    import spark.implicits._

    // Load the CSV file into a DataFrame
    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/ventes_fictives.csv")

    println("--- Original DataFrame ---")
    df.show(5)

    // Display the schema
    println("--- DataFrame Schema ---")
    df.printSchema()

    // Filter data (e.g., quantity > 30)
    val filteredDf = df.filter($"Quantité" > 30)
    println("--- Filtered DataFrame (Quantité > 30) ---")
    filteredDf.show()

    // Perform an aggregation (sum of sales per region)
    val aggregatedDf = df.groupBy("Région")
      .agg(functions.sum("Revenu_Total").as("Revenu_Total_Par_Region"))
      .orderBy($"Revenu_Total_Par_Region".desc)

    println("--- Aggregated DataFrame (Total Revenue by Region) ---")
    aggregatedDf.show()

    // Save the aggregated DataFrame in Parquet format
    val aggregatedOutputPath = "output/ventes_par_region"
    aggregatedDf.write.mode("overwrite").parquet(aggregatedOutputPath)
    println(s"Aggregated DataFrame saved in Parquet format at: $aggregatedOutputPath")

    // Save the filtered DataFrame in Parquet format
    val filteredOutputPath = "output/ventes_filtrees"
    filteredDf.write.mode("overwrite").parquet(filteredOutputPath)
    println(s"Filtered DataFrame saved in Parquet format at: $filteredOutputPath")


    // Stop the SparkSession
    spark.stop()
  }
}
