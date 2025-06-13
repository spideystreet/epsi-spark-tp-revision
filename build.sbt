name := "epsi-spark-tp-revision"
version := "0.1"
scalaVersion := "2.12.18"

val sparkVersion = "3.5.0"

// Add Spark dependency
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion 