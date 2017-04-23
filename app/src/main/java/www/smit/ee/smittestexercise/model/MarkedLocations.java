package www.smit.ee.smittestexercise.model;

import java.util.ArrayList;

/**
 * Created by Andreas on 23.04.2017.
 */

public class MarkedLocations {
    private static ArrayList<MarkedLocation> markedLocations;

    public static ArrayList<MarkedLocation> getMarkedLocations() {
        return markedLocations;
    }

    public static void setMarkedLocations(ArrayList<MarkedLocation> markedLocations) {
        MarkedLocations.markedLocations = markedLocations;
    }
}
