package com.example.junhosung.aquagroupwalkingapp.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.junhosung.aquagroupwalkingapp.model.GpsLocation;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.SharedPreferenceLoginState;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import static com.example.junhosung.aquagroupwalkingapp.model.Model.getInstance;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Model model = Model.getInstance();
    User currentUser;
    private GoogleMap mapDisplay;
    Group currentGroup;
    Circle myRadius;
    MarkerOptions marker;
    MarkerOptions groupMarker;
    private LocationManager locationManager;
    List<String> groupList;
    List<List<Double>> LatList;
    List<List<Double>> LngList;
    List<Double> setLatList;
    List<Double> setLngList;
    long elapsedTime;
    Chronometer Timer = null;
    int minElapsed = 0;
    int secondElapsed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        TextView updateDisplay = (TextView) findViewById(R.id.textViewUpdate);
        TextView updateTime = (TextView) findViewById(R.id.textViewTimeUpdate);
        ((TextView) findViewById(R.id.curGroupInMaps)).setText(currentGroup.getGroupDescription());//("Yipper group");
        updateTime.setText(Integer.toString(minElapsed) + " Minutes: " + Integer.toString(secondElapsed) + " Seconds");
        setUpUpdateBtn();
        setUpLogoutBtn();
        setUpViewGroupBtn();
        Button btn = (Button) findViewById(R.id.monitorbtn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MapOptionsActivity.class);
                startActivity(intent);
            }
        });

        MapFragment mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag));
        mapFrag.getMapAsync(this);
        locationUpdate();
     //   locationTimer();
    }

    // circle now set 500 meter radius from myself
    private void locationUpdate() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // choose between using network provider or gps provider since android chooses between the two
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    if (myRadius != null || marker != null) {
                        mapDisplay.clear();
                    }

                    try {
                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        Toast.makeText(getApplicationContext(), "Our location is Latitude: " + location.getLatitude() + "  Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        marker = new MarkerOptions().position(currentLocation).title("We're here");
                        mapDisplay.addMarker(marker);
                        markGroupsOnMap();
                        // displayTimeSinceLastUpdate();
                        mapDisplay.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(currentLocation, 17);
                        mapDisplay.animateCamera(defaultDisplay);
                        myRadius = mapDisplay.addCircle(new CircleOptions()
                                .center(currentLocation)
                                .radius(100)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.TRANSPARENT));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }

            });
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    if (myRadius != null || marker != null) {
                        mapDisplay.clear();
                    }
                    try {
                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        marker = new MarkerOptions().position(currentLocation).title("I'm here");
                        mapDisplay.addMarker(marker);
                        markGroupsOnMap();
                        mapDisplay.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(currentLocation, 15);
                        mapDisplay.animateCamera(defaultDisplay);
                        myRadius = mapDisplay.addCircle(new CircleOptions()
                                .center(currentLocation)
                                .radius(1000)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.TRANSPARENT));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else {
            Toast.makeText(this, "Turn on the gps and click update on the map!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapDisplay = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mapDisplay.setMyLocationEnabled(true);
    }

    private void setUpLogoutBtn() {
        Button btn = (Button) findViewById(R.id.btnLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceLoginState.clearSharedPref(MapsActivity.this);
                finish();
            }
        });
    }

    private void setUpViewGroupBtn() {
        Button groupButton = (Button) findViewById(R.id.viewGroupBtn);
        groupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpUpdateBtn() {
        Button updateButton = (Button) findViewById(R.id.updateBtn);
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTimeSinceLastUpdate();
                locationUpdate();
            }
        });
    }

    private void markGroupsOnMap() {
        int i;
        Double[] latitudeList = {49.2826, 49.2825, 49.2818, 49.2819};
        Double[] longitudeList = {-123.1206, -123.1209, -123.1219, -123.1221};
        setLatList = Arrays.asList(latitudeList);
        setLngList = Arrays.asList(longitudeList);
        String[] groupList = {"Yipper Group", "group2", "Big Daddy's group","yipper"};
        for (i = 0; i < latitudeList.length; i++) {
            LatLng markLocation = new LatLng(latitudeList[i], longitudeList[i]);
            mapDisplay.addMarker(groupMarker = new MarkerOptions().position(markLocation).title(groupList[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        }
    }

    private void displayTimeSinceLastUpdate() {
        long minutes = ((SystemClock.elapsedRealtime() - Timer.getBase()) / 1000) / 60;
        long seconds = ((SystemClock.elapsedRealtime() - Timer.getBase()) / 1000) % 60;
        elapsedTime = SystemClock.elapsedRealtime();
        Log.d("Check Time", "Since last location update: " + minutes + " : " + seconds);
    }

   private void sendGroupCurrentLocation(Group group) {
        currentGroup = group;
        currentUser = currentGroup.getLeader();
        Double[] curlat = {49.2826, 49.2827};
        Double[] curlng = {-123.1206, 49.2728};
        GpsLocation myCurrentLocation = new GpsLocation();
        myCurrentLocation.setLat(49.2828);
        myCurrentLocation.setLng(-123.1208);
        currentUser.setLastGpsLocation(myCurrentLocation);
        setLatList = Arrays.asList(curlat);
        setLngList = Arrays.asList(curlng);
        currentGroup.setRouteLatArray(setLatList);
        currentGroup.setRouteLngArray(setLngList);
    }

    private void sendLocationSever() {
        model.updateGroupDetails(currentGroup.getId(), currentGroup, this :: sendGroupCurrentLocation);
    }

    private void getCoordinates() {
        model.getGroupDetailsById(currentGroup.getId(), this :: groupAttributesCallback);
    }

    private void groupAttributesCallback(Group group) {
        model.getCurrentGroupInUseByUser().getGroupDescription();
        currentGroup = group;
        currentUser = currentGroup.getLeader();
        currentUser.getLastGpsLocation();
        currentGroup.getRouteLatArray();
        currentGroup.getRouteLngArray();
    }

    private void locationTimer() {
        CountDownTimer timer = new CountDownTimer(30000, 1) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                locationUpdate();
            }
        }.start();
    }
}