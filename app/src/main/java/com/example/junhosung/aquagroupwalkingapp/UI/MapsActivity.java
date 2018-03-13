package com.example.junhosung.aquagroupwalkingapp.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.junhosung.aquagroupwalkingapp.model.SharedPreferenceLoginState;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapDisplay;
    Circle myRadius;
    MarkerOptions marker;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
    }
    private void locationUpdate() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // choose between using network provider or gps provider since android chooses between the two
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    if(myRadius!=null || marker != null){
                        mapDisplay.clear();
                    }
                    try {
                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        Toast.makeText(getApplicationContext(), "Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        marker = new MarkerOptions().position(currentLocation).title("I'm here");
                        mapDisplay.addMarker(marker);
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
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    if(myRadius!=null || marker != null){
                        mapDisplay.clear();
                    }
                    try {
                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        marker = new MarkerOptions().position(currentLocation).title("I'm here");
                        mapDisplay.addMarker(marker);
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
        }
    }


   @Override
    public void onMapReady(GoogleMap googleMap) {
        mapDisplay = googleMap;
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
    private void setUpViewGroupBtn(){
        Button groupButton = (Button)findViewById(R.id.viewGroupBtn);
        groupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setUpUpdateBtn(){
        Button updateButton = (Button)findViewById(R.id.updateBtn);
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locationUpdate();
            }
        });
    }
}
