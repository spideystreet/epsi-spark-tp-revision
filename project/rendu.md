# TP Spark - Manipulation de Données

Ce document présente le code Scala utilisé pour manipuler des données avec Apache Spark, ainsi que les résultats de l'exécution du script.

## 1. Code Scala

Voici le contenu du fichier `src/main/scala/ScalaApp.scala`.

```scala
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
```

## 2. Résultats de l'Exécution

Voici les sorties générées par le script lors de son exécution.

### DataFrame Original (5 premières lignes)

```
--- Original DataFrame ---
+--------------+----------+------------+------------+--------+-------------+------------+------+---------+
|ID_Transaction|      Date|     Produit|   Catégorie|Quantité|Prix_Unitaire|Revenu_Total|Région|Client_ID|
+--------------+----------+------------+------------+--------+-------------+------------+------+---------+
|             1|2024-01-01|    Laptop A|Électronique|       5|       1200.0|      6000.0|  Nord|      101|
|             2|2024-01-02|    Souris B|Électronique|      20|         25.0|       500.0|   Sud|      102|
|             3|2024-01-03|   Clavier C|Électronique|      10|         75.0|       750.0|   Est|      103|
|             4|2024-01-04|  Moniteur D|Électronique|       3|        300.0|       900.0| Ouest|      104|
|             5|2024-01-05|Smartphone E|Électronique|       8|        800.0|      6400.0|  Nord|      105|
+--------------+----------+------------+------------+--------+-------------+------------+------+---------+
only showing top 5 rows
```

### Schéma du DataFrame

```
--- DataFrame Schema ---
root
 |-- ID_Transaction: integer (nullable = true)
 |-- Date: date (nullable = true)
 |-- Produit: string (nullable = true)
 |-- Catégorie: string (nullable = true)
 |-- Quantité: integer (nullable = true)
 |-- Prix_Unitaire: double (nullable = true)
 |-- Revenu_Total: double (nullable = true)
 |-- Région: string (nullable = true)
 |-- Client_ID: integer (nullable = true)
```

### DataFrame Filtré (Quantité > 30)

```
--- Filtered DataFrame (Quantité > 30) ---
+--------------+----------+------------------+------------+--------+-------------+------------+------+---------+
|ID_Transaction|      Date|           Produit|   Catégorie|Quantité|Prix_Unitaire|Revenu_Total|Région|Client_ID|
+--------------+----------+------------------+------------+--------+-------------+------------+------+---------+
|            11|2024-01-11|  Livre de Cuisine|      Livres|      50|         15.0|       750.0|   Est|      111|
|            22|2024-01-22|         T-shirt R|   Vêtements|      40|         12.0|       480.0|   Sud|      122|
|            24|2024-01-24|          Lait UHT|Alimentation|     100|          1.5|       150.0| Ouest|      124|
|            25|2024-01-25|       Pain de Mie|Alimentation|      50|          2.0|       100.0|  Nord|      125|
|            28|2024-01-28|             Chips|Alimentation|      60|          2.5|       150.0| Ouest|      128|
|            41|2024-02-11|  Livre d'Histoire|      Livres|      40|         18.0|       720.0|  Nord|      141|
|            52|2024-03-02| Chargeur Portable|Électronique|      40|         15.0|       600.0|   Sud|      102|
|            53|2024-03-03|         Câble USB|Électronique|      50|          5.0|       250.0|   Est|      103|
|            57|2024-03-07|Livre pour Enfants|      Livres|      50|         10.0|       500.0|   Est|      107|
|            65|2024-03-15|       Chaussettes|   Vêtements|     100|          5.0|       500.0|   Est|      115|
|            67|2024-03-17|      Céréales Bio|Alimentation|      70|          3.5|       245.0|  Nord|      117|
|            68|2024-03-18|     Yaourt Nature|Alimentation|     120|          1.0|       120.0|   Sud|      118|
|            69|2024-03-19|             Pâtes|Alimentation|      80|          1.8|       144.0|   Est|      119|
|            70|2024-03-20|               Riz|Alimentation|      90|          2.2|       198.0| Ouest|      120|
|            81|2024-04-07|                BD|      Livres|      40|         15.0|       600.0|   Est|      131|
|            82|2024-04-08|          Magazine|      Livres|      60|          5.0|       300.0| Ouest|      132|
|            93|2024-04-19|            Jambon|Alimentation|      50|          5.0|       250.0|   Est|      143|
|            94|2024-04-20|           Fromage|Alimentation|      40|          6.0|       240.0| Ouest|      144|
|            95|2024-04-21|             Oeufs|Alimentation|      80|          3.0|       240.0|  Nord|      145|
|            96|2024-04-22|            Pommes|Alimentation|     100|          2.0|       200.0|   Sud|      146|
+--------------+----------+------------------+------------+--------+-------------+------------+------+---------+
only showing top 20 rows
```

### DataFrame Agrégé (Revenu total par région)

```
--- Aggregated DataFrame (Total Revenue by Region) ---
+------+-----------------------+
|Région|Revenu_Total_Par_Region|
+------+-----------------------+
|  Nord|                24365.0|
|   Sud|                13235.0|
|   Est|                10506.0|
| Ouest|                10303.0|
+------+-----------------------+
```

### Enregistrement des Fichiers Parquet

```
Aggregated DataFrame saved in Parquet format at: output/ventes_par_region
Filtered DataFrame saved in Parquet format at: output/ventes_filtrees
``` 