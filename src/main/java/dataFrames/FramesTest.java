package dataFrames;



import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.bouncycastle.util.Arrays;
import rddIntro.Trip;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.spark.sql.functions.*;

/**
 * Created by StasMaster on 31.12.17.
 */
public class FramesTest {

    public static void main(String[] args) {


        SparkConf conf = new SparkConf().setAppName("Tutorial").setMaster("local[1]");



        SparkContext sc = new SparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        SparkSession sparkSession = SparkSession.builder().getOrCreate();

        Dataset<Row> rowDataset = sparkSession.read().json("data/profiles.txt");


        // print all columns
        rowDataset.printSchema();
        rowDataset = rowDataset.withColumn("salary",
                col("age").multiply(size(col("keywords"))).multiply(10));
        rowDataset.show();
        Dataset<Row> keyWords = rowDataset.withColumn("keywords", explode(col("keywords")));
        keyWords.show();

        Dataset<Row> keyWordsSet = keyWords.groupBy(col("keywords")).agg(count("keywords").as("count")).sort(col("count").desc());
        keyWordsSet.show();
        String rowString =  keyWordsSet.first().getAs("keywords");
        System.out.println(rowString);

        Dataset<Row> profDataSet = rowDataset.filter(col("salary").$greater(1200)).filter(array_contains(col("keywords"), rowString));
        profDataSet.show();

    }

}
