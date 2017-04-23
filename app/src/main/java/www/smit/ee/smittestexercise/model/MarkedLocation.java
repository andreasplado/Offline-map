package www.smit.ee.smittestexercise.model;

/**
 * Created by Andreas on 23.04.2017.
 */

public class MarkedLocation {

    private double latitude;
    private double longitude;
    private String description;
    private String subdescription;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubdescription() {
        return subdescription;
    }

    public void setSubdescription(String subdescription) {
        this.subdescription = subdescription;
    }
}
