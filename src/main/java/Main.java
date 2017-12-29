import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext$;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.Iterator;

/**
 * Created by StasMaster on 27.12.17.
 */
public class Main {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Tutorial").setMaster("local[2]");

        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);


        SparkContext sc = new SparkContext(conf);




        JavaRDD<Trip> tripJavaRDD = sc.textFile("data/trips.txt",2).toJavaRDD().map( a -> {
            String[] tripString = a.split(" ");
                return new Trip(Double.parseDouble(tripString[2]), tripString[1], Integer.parseInt(tripString[0]));
            } );


        tripJavaRDD.persist(StorageLevel.MEMORY_AND_DISK());

        JavaPairRDD<Integer, Double>  pair = tripJavaRDD.mapToPair(trip -> new Tuple2<>(trip.getDriverId(), trip.getDistance()))
                .persist(StorageLevel.MEMORY_AND_DISK())
                .reduceByKey((v1,v2) -> v1.doubleValue() + v2.doubleValue())
                .mapToPair(tripPairRDD ->  new Tuple2<Double, Integer>( tripPairRDD._2, tripPairRDD._1 )).sortByKey(false)
                .mapToPair(tripReturn -> new Tuple2<Integer, Double>( tripReturn._2, tripReturn._1 ));

        System.out.println(pair.take(3).toString());


        long overalTripsToBoston = tripJavaRDD.filter( trip -> trip.getDistance() > 10).filter( trip -> trip.getDestination().toLowerCase().equals("boston")).count();
        System.out.println("trips to bost "+overalTripsToBoston);

        Double tripsDist = tripJavaRDD.filter(trip -> trip.getDestination().toLowerCase().equals("boston")).mapToDouble(Trip::getDistance).sum();
        System.out.println("total dist to boston made "+tripsDist);



    }
}
