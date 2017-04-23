package www.smit.ee.smittestexercise.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.io.File;
import java.util.ArrayList;
import www.smit.ee.smittestexercise.DownloadActivity;
import www.smit.ee.smittestexercise.R;
import www.smit.ee.smittestexercise.model.MarkedLocation;
import www.smit.ee.smittestexercise.model.MarkedLocations;
import www.smit.ee.smittestexercise.utils.GPSUtils;
import www.smit.ee.smittestexercise.utils.SDCardUtils;

import static www.smit.ee.smittestexercise.utils.Constants.PERMISSION_REQUEST_CODE;


/**
 * Created by Andreas on 21.04.2017.
 */

public class MainController {

    private Context context;
    private Activity activity;
    private MapView mapView;
    private TextView textView;

    public MainController(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }



    public void addMap(){
        mapView = new MapView(context);
        setTileSource();
        addUploadingDataInformation();
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        activity.setContentView(mapView); //displaying the MapView
        mapView.setUseDataConnection(false); //keeps the mapView from loading online tiles using network connection.

    }

    private void addUploadingDataInformation() {
        textView = new TextView(context);
        textView.setText("");
        activity.setContentView(textView);
    }

    private void setTileSource(){
        String atlasName = "4uMaps";
        String atlasExtension = ".png";
        int tileSizePixels = 256;
        int minZoom = 2;
        int maxZoom = 15;
        int defaultZoom = 8;

        mapView.setTileSource(new XYTileSource(atlasName, minZoom, maxZoom, tileSizePixels, atlasExtension, new String[] {}));
        mapView.setBuiltInZoomControls(true);
        mapView.getController().setZoom(defaultZoom);
        mapView.setClickable(true);
        mapView.setMaxZoomLevel(13);
        mapView.setMinZoomLevel(9);
        mapView.setScrollableAreaLimitDouble(new BoundingBox(59.946758, 28.223877, 57.512873, 21.373901));

        mapView.getController().setCenter(new GeoPoint(59.436962, 24.753574));
    }

    public void requestPermissonCheck(){
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {
            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public boolean fileExists(String path, String file){
        File extStore = Environment.getExternalStorageDirectory();
        File myFile = new File(extStore.getAbsolutePath() + path, file);
        if(myFile.exists() && !myFile.isDirectory()){
            return true;
        }else{
            return false;
        }
    }

    public void initialCheck(){
        if(fileExists("/osmdroid/", "tile.zip") && fileExists("/smit/", "mapped_locations.json")){
            gpsCheck();
            readJSON();
            parseJson();
            addMap();
            addMarkersToMap();
        }else{
            Intent i = new Intent(context, DownloadActivity.class);
            activity.startActivity(i);
        }
    }


    public void gpsCheck() {
        if(GPSUtils.isGpsEnabled(activity)){
            Toast.makeText(context, "GPSUtils is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser(activity);
        }
    }

    private static void showGPSDisabledAlertToUser(final Activity activity){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("GPSUtils is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("GPSUtils Settings",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                //activity.startActivity(callGPSSettingIntent);
                                activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 5);
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

    public void addMarkersToMap(){
        ArrayList<MarkedLocation> markedLocations = MarkedLocations.getMarkedLocations();
        for(int i=0; i< markedLocations.size(); i++){
            double latitude = markedLocations.get(i).getLatitude();
            double longitude = markedLocations.get(i).getLongitude();
            String description = markedLocations.get(i).getDescription();
            String subDescription = markedLocations.get(i).getSubdescription();

            GeoPoint startPoint = new GeoPoint(latitude, longitude);  //white house
            Marker startMarker = new Marker(mapView);
            startMarker.setPosition(startPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setIcon(ContextCompat.getDrawable(activity, R.drawable.ic_placeholder));
            startMarker.setTitle("Tallinn");
            startMarker.setSnippet(description);
            startMarker.setSubDescription(subDescription);
            mapView.getOverlays().add(startMarker);


        }



    }

    public void readJSON(){
        SDCardUtils sdCardUtils = new SDCardUtils();
        sdCardUtils.readLocationData("mapped_locations.json");
    }

    public void parseJson(){
        GPSUtils gpsUtils = new GPSUtils();
        gpsUtils.parseJSONData();
    }
}
