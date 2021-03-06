package www.smit.ee.smittestexercise.utils;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.Map;

import www.smit.ee.smittestexercise.R;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

/**
 * RagnSells
 * Created by Andreas on 28.05.2017.
 */

public class MyLocationUtil {

    private Activity activity;
    private Context context;
    private MapView mapView;

    public MyLocationUtil(Activity activity, Context context, MapView mapView) {
        this.activity = activity;
        this.context = context;
        this.mapView = mapView;
    }

    public void setMyLocation() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
        LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {



            @Override
            public void onLocationChanged(Location location) {

                //Set user location
                MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(mapView);
                myLocationoverlay.enableMyLocation();
                mapView.getOverlays().add(myLocationoverlay);

                //Draw user
                mapView.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(GPS_PROVIDER, 0, 0, listener);
        }else{
            Alert.info(activity, "Viga", "Teil pole Asukoha määramiseks õigusi");
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    private BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {

            } else {
                Alert.info(activity, "Viga", "Palun lüitage GPS sisse");
            }
        }
    };

    public void registerGPSReciever(){
        activity.registerReceiver(gpsReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }
}
