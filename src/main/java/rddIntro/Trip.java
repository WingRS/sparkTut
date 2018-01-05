package first;

/**
 * Created by StasMaster on 28.12.17.
 */
public class Trip {

    private double distance;
    private String destination;
    private int driverId;

    public Trip(double distance, String destination, int driverId) {
        this.distance = distance;
        this.destination = destination;
        this.driverId = driverId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
}
