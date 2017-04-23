package www.smit.ee.smittestexercise.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.smit.ee.smittestexercise.data.JSONData;
import www.smit.ee.smittestexercise.model.MarkedLocation;
import www.smit.ee.smittestexercise.model.MarkedLocations;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Andreas on 23.04.2017.
 */

public class GPSUtils {


    public static boolean isGpsEnabled(Activity activity){
        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return true;
        }else{
            return false;
        }

    }
    private static void showGPSDisabledAlertToUser(final Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("GPSUtils is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPSUtils",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void parseJSONData(){
        JSONArray jsonarray = null;
        try {
            ArrayList<MarkedLocation> markedLocations = new ArrayList<>();
            jsonarray = new JSONArray(JSONData.getJsonData());
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String description = jsonobject.getString("description");
                String subDescription = jsonobject.getString("subdescription");
                double latitude = jsonobject.getDouble("latitude");
                double longitude = jsonobject.getDouble("longitude");

                MarkedLocation markedLocation = new MarkedLocation();
                markedLocation.setLatitude(latitude);
                markedLocation.setLongitude(longitude);
                markedLocation.setSubdescription(subDescription);
                markedLocation.setDescription(description);
                markedLocations.add(markedLocation);
            }
            MarkedLocations.setMarkedLocations(markedLocations);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
