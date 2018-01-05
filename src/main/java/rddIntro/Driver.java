package first;

/**
 * Created by StasMaster on 28.12.17.
 */
public class Driver {

    private int id;
    private String name;
    private String address;
    private String email;
    private double dist = 0;

    public Driver() {

    }

    public Driver(int id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addDist(double dist) {
        this.dist += dist;
    }

    public double getDist() {
        return dist;
    }
}
