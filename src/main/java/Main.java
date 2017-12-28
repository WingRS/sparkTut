import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.SparkContext$;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.Iterator;

/**
 * Created by StasMaster on 27.12.17.
 */
public class Main {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("Tutorial").setMaster("local[2]");

        SparkContext sc = new SparkContext(conf);
//        JavaRDD<String> javaRDD = sc.textFile("text.txt", 2).toJavaRDD();
//        long count =  javaRDD.map(a -> a.split(";")[1]).filter(f -> f.equals("asda")).count();
//        System.out.println(count);



        JavaRDD<Trip> tripJavaRDD = sc.textFile("data/trips.txt",2).toJavaRDD().map( a -> {
            String[] tripString = a.split(" ");
                return new Trip(Double.parseDouble(tripString[2]), tripString[1], Integer.parseInt(tripString[0]));
            } );

        JavaPairRDD<Integer, Double>  pair = tripJavaRDD.mapToPair(trip -> new Tuple2<>(trip.getDriverId(), trip.getDistance()))
                                                        .reduceByKey((v1,v2) -> v1.doubleValue() + v2.doubleValue());
        pair.

        System.out.println(pair.collect());

        long overalDistToBoston = 0;
        long overalTripsToBoston = tripJavaRDD.filter( trip -> trip.getDistance() > 10).filter( trip -> trip.getDestination().toLowerCase().equals("boston")).count();
        System.out.println("trips to bost "+overalTripsToBoston);

        Double tripsDist = tripJavaRDD.filter(trip -> trip.getDestination().toLowerCase().equals("boston")).mapToDouble(Trip::getDistance).sum();
        System.out.println("total dist "+tripsDist);

//
//        JavaRDD<String> javaRDD = sc.textFile("data/trips.txt",2).toJavaRDD();
//        System.out.println(javaRDD.map(a->a.split(" ")).count());
        driverRdd.foreach( f -> System.out.println(f.getDist()));

    }
}
