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
import android.widget.Button;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Model model = Model.getInstance();
    User currentUser = model.getCurrentUser();
    Group currentGroup = model.getCurrentGroupInUseByUser();
   // GpsLocation myCurrentLocation = currentUser.getLastGpsLocation();
    GpsLocation myCurrentLocation = new GpsLocation();

    private GoogleMap mapDisplay;
    Circle myRadius;
    MarkerOptions marker;
    MarkerOptions groupMarker;
    private LocationManager locationManager;
    List<Double> setLatList;
    List<Double> setLngList;
    Double[] groupLatArray;
    Double[] groupLngArray;
    double currentUserLat;// = 49.2827;
    double currentUserLng;// = -123.1207;
    double prevLat = 0.00;
    double prevLng = 0.00;
    long secondElapsed = 0;
    boolean atSchool = false;
    boolean cancelTimer = false;
    LatLng markLatLng = new LatLng(0.00, 0.00);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        TextView updateDisplay = (TextView) findViewById(R.id.textViewUpdate);
        TextView updateTime = (TextView) findViewById(R.id.textViewTimeUpdate);
        setUpUpdateBtn();
        setUpLogoutBtn();
        setUpViewGroupBtn();
        setUpParentDashboard();
        setUpEditUserBtn();
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
        locationTimer();
        sendMyLocation();
        getGroupNameOnMap();
        //sendLocationSever();
        getCoordinates();
        Log.d("tag", "Who am i" + model.getCurrentUser());
        Log.d("tag", "WHAT GROUP ARE WE  " + model.getCurrentGroupInUseByUser().getGroupDescription());
        // Log.d("tag","tellmereference"+reference);

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
                    // LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng currentLocation = new LatLng(currentUser.getLastGpsLocation().getLat(), currentUser.getLastGpsLocation().getLng());
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    if (myRadius != null || marker != null) {
                        mapDisplay.clear();
                    }

                    try {
                        //geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        geocoder.getFromLocation(currentUser.getLastGpsLocation().getLat(), currentUser.getLastGpsLocation().getLng(), 0);
                        Toast.makeText(getApplicationContext(), "Our location is Latitude: " + currentUser.getLastGpsLocation().getLat() + "  Longitude: " + currentUser.getLastGpsLocation().getLng() + "  Location uploaded", Toast.LENGTH_SHORT).show();
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        marker = new MarkerOptions().position(currentLocation).title("I'm Here");
                        mapDisplay.addMarker(marker);
                        mapDisplay.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(currentLocation, 17);
                        mapDisplay.animateCamera(defaultDisplay);
                        currentUserLat = location.getLatitude();
                        currentUserLng = location.getLongitude();
                        setLatList = Arrays.asList(currentUserLat);
                        setLngList = Arrays.asList(currentUserLng);
                        sendLocationSever();
                        sendMyLocation();
                        getGroupNameOnMap();
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
                    // LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng currentLocation = new LatLng(currentUser.getLastGpsLocation().getLat(), currentUser.getLastGpsLocation().getLng());
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    if (myRadius != null || marker != null) {
                        mapDisplay.clear();
                    }

                    try {
                        //geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        geocoder.getFromLocation(currentUser.getLastGpsLocation().getLat(), currentUser.getLastGpsLocation().getLng(), 0);
                        Toast.makeText(getApplicationContext(), "Our location is Latitude: " + currentUser.getLastGpsLocation().getLat() + "  Longitude: " + currentUser.getLastGpsLocation().getLng() + "  Location uploaded", Toast.LENGTH_LONG).show();
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        marker = new MarkerOptions().position(currentLocation).title("I'm Here");
                        mapDisplay.addMarker(marker);
                        mapDisplay.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(currentLocation, 17);
                        mapDisplay.animateCamera(defaultDisplay);
                        currentUserLat = location.getLatitude();
                        currentUserLng = location.getLongitude();
                        setLatList = Arrays.asList(currentUserLat);
                        setLngList = Arrays.asList(currentUserLng);
                        sendLocationSever();
                        sendMyLocation();
                        getGroupNameOnMap();
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
            Toast.makeText(this, "Turn on the gps and click update on the map!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapDisplay = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mapDisplay != null) {
            mapDisplay.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.info_box, null);
                    TextView tvName = (TextView) findViewById(R.id.nameView);
                    TextView tvLoc = (TextView) findViewById(R.id.locationView);
                    TextView tvTimer = (TextView) findViewById(R.id.timerView);
                    // tvName.setText()
                    return null;
                }
            });
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
        Button updateButton = (Button) findViewById(R.id.parentDashboardBtn);
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locationUpdate();
            }
        });
    }

    private void setUpParentDashboard() {
        Button parentDashButton = (Button) findViewById(R.id.parentDashboardBtn);
        parentDashButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, ParentDashboard.class);
                startActivity(intent);
            }
        });
    }

    private void setUpEditUserBtn() {
        Button editUserButton = (Button) findViewById(R.id.editUserBtn);
        editUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendGroupCurrentLocation(Group group) {
    }

    private void sendLocationSever() {
        setLatList = Arrays.asList(currentUserLat);
        setLngList = Arrays.asList(currentUserLng);
        currentGroup.setRouteLatArray(setLatList);
        currentGroup.setRouteLngArray(setLngList);
        model.updateGroupDetails(model.getCurrentGroupInUseByUser().getId(), model.getCurrentGroupInUseByUser(), this :: sendGroupCurrentLocation);
    }

    //model.getCurrentGroupInUseByUser().getId()
    private void getCoordinates() {
        model.getGroupDetailsById(model.getCurrentGroupInUseByUser().getId(), this :: groupAttributesCallback);
    }

    private void getGroupNameOnMap() {
        model.getGroups(this :: groupNameCallback);
    }

    private void sendMyLocation() {
        //myCurrentLocation = currentUser.getLastGpsLocation();
        GpsLocation myCurrentLocation  = new GpsLocation();
        myCurrentLocation.setLat(currentUserLat);
        myCurrentLocation.setLng(currentUserLng);
       // String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        //myCurrentLocation.setTimestamp(timeStamp);
        currentUser.setLastGpsLocation(myCurrentLocation);
        model.updateUser(currentUser, this::myLocationCallback);
        Log.d("Albert", "Albert" + myCurrentLocation.getLat() + myCurrentLocation.getLng());
        Log.d("albert", "Alert" + currentUser.getLastGpsLocation().getLat());//works
    }

    private void myLocationCallback(User user) {
    }

    private void groupAttributesCallback(Group group) {
        currentGroup = group;
        model.getCurrentGroupInUseByUser().getGroupDescription();
        groupLatArray = new Double[currentGroup.getRouteLatArray().size()];
        groupLngArray = new Double[currentGroup.getRouteLngArray().size()];
        markLatLng = new LatLng(groupLatArray[groupLatArray.length - 1], groupLngArray[groupLngArray.length - 1]);
        Log.d("tag", "holy" + groupLngArray[groupLngArray.length - 1]);
        if (currentGroup.getRouteLatArray().size() < 1) {
            Log.d("tag", "No groups have route array");
            //markLatLng = new LatLng(49.2829, -123.1411);
        } else {
            markLatLng = new LatLng(groupLatArray[groupLatArray.length - 1], groupLngArray[groupLngArray.length - 1]);
        }
    }

    private void groupNameCallback(List<Group> groups) {
        List<String> groupsDisplay = new ArrayList<>();
        for (Group group : groups) {
            groupsDisplay.add(group.getGroupDescription());
            getCoordinates();
            // Log.d("tag" ,"idc what it is"+ group.getGroupDescription());
        }
        String[] groupDisplayArray = new String[groupsDisplay.size()];
        //Log.d("tag","what size"+groupsDisplay.size());

        int i;
        for (i = 0; i < groupsDisplay.size(); i++) {
            LatLng markLocation = new LatLng(49.1217 , -123.1269);
            groupDisplayArray[i] = groupsDisplay.get(i);
            //getCoordinates();
            mapDisplay.addMarker(groupMarker = new MarkerOptions().position(markLocation).title(groupDisplayArray[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            Log.d("tag", "who are these groups" + groupDisplayArray[i]);
        }

    }


    private void locationTimer() {
        TextView updateTime = (TextView) findViewById(R.id.textViewTimeUpdate);
        atSchoolLocationTimer();
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondElapsed = millisUntilFinished / 1000;
                updateTime.setText(Long.toString(secondElapsed) + " Seconds");
            }

            public void onFinish() {
                prevLat = currentUserLat;
                prevLng = currentUserLng;
                locationUpdate();
                sendLocationSever();
                secondElapsed = 0;
                updateTime.setText(Long.toString(secondElapsed) + " Seconds");
                Log.d("tag", "PreviousLat" + prevLat);
                Log.d("tag", "PreviousLat" + prevLng);
                if (prevLat != currentUserLat && prevLng != currentUserLng) {
                    cancelTimer = true;
                    atSchoolLocationTimer();
                }
                if(!atSchool) {
                    start();
                }
            }
        }.start();
    }

    private void atSchoolLocationTimer() {
        new CountDownTimer(600000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(cancelTimer){
                    cancel();
                }
            }
            public void onFinish() {
                atSchool = true;
                Toast.makeText(getApplicationContext(),"ARRIVED at school,location update will stop",Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}