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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
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

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Model model = Model.getInstance();
    User currentUser = model.getCurrentUser();
    Group currentGroup = model.getCurrentGroupInUseByUser();
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

    int defaultTheme = R.style.AppTheme;                            //0
    int lowTierTheme = android.R.style.Theme_Light;                 //1
    int darkTheme = android.R.style.ThemeOverlay_Material_Dark;     //2
    int lightTheme = android.R.style.ThemeOverlay_Material_Light;   //3
    int holoTheme = android.R.style.Theme_Holo_NoActionBar;         //4

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.otherUserDetails:
                startActivity(new Intent(MapsActivity.this, GroupsLeaderOfActivity.class));
                return true;
            case R.id.monitors:
                startActivity(new Intent(MapsActivity.this, MapOptionsActivity.class));
                return true;
            case R.id.manageGroups:
                startActivity(new Intent(MapsActivity.this, GroupManagementActivity.class));
                return true;
            case R.id.leaderboard:
                startActivity(new Intent(MapsActivity.this,UserLeaderboardActivity.class));
                return true;
            case R.id.viewMsg:
                startActivity(new Intent(MapsActivity.this,MessagePanelActivity.class));
                return true;
            case R.id.userShop:
                startActivity(new Intent(MapsActivity.this,ShopActivity.class));
                return true;
            case R.id.editUser:
                startActivity(new Intent(MapsActivity.this, EditAccountActivity.class));
                return true;
            case R.id.themeChange:
                startActivity(new Intent(MapsActivity.this,MyStuffActivity.class));
                return true;
            case R.id.editGroup:
                startActivity(new Intent(MapsActivity.this, EditGroupActivity.class));
                return true;
                default:
                return super.onOptionsItemSelected(item);

        }
        //respond to menu item selection
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //model.getCurrentUser().setTheme(4);
        setTheme(model.themeToApply(currentUser));
        //model.getCurrentUser().setColor(4);
        setContentView(R.layout.activity_maps);
        TextView updateDisplay = (TextView) findViewById(R.id.textViewUpdate);
        TextView updateTime = (TextView) findViewById(R.id.textViewTimeUpdate);
        setUpUpdateBtn();
        setUpLogoutBtn();
        //setUpViewGroupBtn();
        setUpParentDashboard();
       /* Button btn = (Button) findViewById(R.id.monitorbtn);
        btn.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MapOptionsActivity.class);
                startActivity(intent);
            }
        });*/

        MapFragment mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag));
        mapFrag.getMapAsync(this);
        walkCompleteChecker();
        locationUpdate();
        locationTimer();
        //sendMyLocation(currentUser);
        sendMyLocation();
        getGroupNameOnMap();
        //sendLocationSever();
        getCoordinates();
        testLocationSender();
        Log.d("tag", "Who am i" + model.getCurrentUser());
        Log.d("tag", "WHAT GROUP ARE WE  " + model.getCurrentGroupInUseByUser().getGroupDescription());

    }
    private void testLocationSender(){
        GpsLocation lastspot = new GpsLocation(49.145,-123.1210,null);
        currentUser = model.getCurrentUser();
        currentUser.setLastGpsLocation(lastspot);
        model.setLastGPSLocation(currentUser.getId(),lastspot,this::testLocationCallback);
    }
    private void testLocationCallback(User user){};
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
                    //LatLng currentLocation = new LatLng(currentUser.getLastGpsLocation().getLat(), currentUser.getLastGpsLocation().getLng());
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    if (myRadius != null || marker != null) {
                        mapDisplay.clear();
                    }

                    try {
                        //
                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 0);
                        //geocoder.getFromLocation(currentUser.getLastGpsLocation().getLat(), currentUser.getLastGpsLocation().getLng(), 0);
                        Toast.makeText(getApplicationContext(), "Our location is Latitude: " +location.getLatitude() + "  Longitude: " + location.getLongitude() + "  Location uploaded", Toast.LENGTH_SHORT).show();
                        mapDisplay.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        marker = new MarkerOptions().position(currentLocation).title("I'm Here");
                        mapDisplay.addMarker(marker);
                        mapDisplay.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(currentLocation, 14);
                        mapDisplay.animateCamera(defaultDisplay);
                        currentUserLat = location.getLatitude();
                        currentUserLng = location.getLongitude();
                        setLatList = Arrays.asList(currentUserLat);
                        setLngList = Arrays.asList(currentUserLng);
                        sendLocationSever();
                        //sendMyLocation(currentUser);
                        sendMyLocation();
                        walkCompleteChecker();
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
                        //sendMyLocation(currentUser);
                        sendMyLocation();
                        walkCompleteChecker();
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
        btn.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceLoginState.clearSharedPref(MapsActivity.this);
                finish();
            }
        });
    }

   /* private void setUpViewGroupBtn() {
        Button groupButton = (Button) findViewById(R.id.viewGroupBtn);
        groupButton.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        groupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, GroupManagementActivity.class);
                startActivity(intent);
            }
        });
    }*/

    private void setUpUpdateBtn() {
        Button updateButton = (Button) findViewById(R.id.parentDashboardBtn);
        updateButton.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        updateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locationUpdate();
            }
        });
    }

    private void setUpParentDashboard() {
        Button parentDashButton = (Button) findViewById(R.id.parentDashboardBtn);
        parentDashButton.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        parentDashButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, ParentDashboard.class);
                startActivity(intent);
            }
        });
    }

    /*private void setUpEditUserBtn() {
        Button editUserButton = (Button) findViewById(R.id.editUserBtn);
        editUserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });
    }*/

    private void sendGroupCurrentLocation(Group group) {
    }

    private void sendLocationSever() {
        currentGroup = model.getCurrentGroupInUseByUser();
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
        GpsLocation lastGpsLocation =  new GpsLocation(49.1208,-123.1210,null);
        Log.d("tag","currentUerLat"+currentUserLat);
        lastGpsLocation.setLat(currentUserLat);
        lastGpsLocation.setLng(currentUserLng);
        //GpsLocation lastGpsLocation =  new GpsLocation(currentUserLat,currentUserLng,null);
        currentUser = model.getCurrentUser();
        currentUser.setLastGpsLocation(lastGpsLocation);
        Log.d("Albert", "Alert" + lastGpsLocation.getLat() +" "+ lastGpsLocation.getLng());
        Log.d("Albert", "Alert" + model.getCurrentUser());
        model.setLastGPSLocation(currentUser.getId(),lastGpsLocation,this::myLocationCallback);
        //model.updateUser(currentUser,this :: myLocationCallback);
    }

    private void myLocationCallback(User currentUser) {
        Log.d("","DOYOUWORK");
    }

    private void groupAttributesCallback(Group group) {
        //currentGroup = group;
       //group = model.getCurrentGroupInUseByUser().getGroupDescription();
      /*  groupLatArray = new Double[group.getRouteLatArray().size()];
        groupLngArray = new Double[group.getRouteLngArray().size()];
        markLatLng = new LatLng(groupLatArray[groupLatArray.length - 1], groupLngArray[groupLngArray.length - 1]);
        Log.d("tag", "holy" + groupLngArray[groupLngArray.length - 1]);
        if (group.getRouteLatArray().size() < 1) {
            Log.d("tag", "No groups have route array");
            //markLatLng = new LatLng(49.2829, -123.1411);
        } else {
            markLatLng = new LatLng(groupLatArray[groupLatArray.length - 1], groupLngArray[groupLngArray.length - 1]);
        }*/
    }

    private void groupNameCallback(List<Group> groups) {
        List<String> groupsDisplay = new ArrayList<>();
        for (Group group : groups) {
            groupsDisplay.add(group.getGroupDescription());
            getCoordinates();
            // Log.d("tag" ,"idc what it is"+ group.getGroupDescription());
        }
        String[] groupDisplayArray = new String[groupsDisplay.size()];
        if(groupsDisplay.size()==0){
            Toast.makeText(getApplicationContext(), "There are no groups in your map range", Toast.LENGTH_SHORT).show();
        }
        //Log.d("tag","what size"+groupsDisplay.size());

        int i;
        for (i = 0; i < groupsDisplay.size(); i++) {
            LatLng markLocation = new LatLng(49.1217 , -123.1207);
            groupDisplayArray[i] = groupsDisplay.get(i);
            //getCoordinates();
            mapDisplay.addMarker(groupMarker = new MarkerOptions().position(markLocation).title(groupDisplayArray[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            //Log.d("tag", "who are these groups" + groupDisplayArray[i]);
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
                currentUser = model.getCurrentUser();
                currentGroup = model.getCurrentGroupInUseByUser();
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
    private void walkCompleteChecker() {
        int currentPoints = currentUser.getCurrentPoints();
        int totalPoints = currentUser.getTotalPointsEarned();
        //GpsLocation userCoord = new GpsLocation(49.145, -123.1210, null);
        GpsLocation userCoord = new GpsLocation(currentUserLat,currentUserLng,null);
        Double[] routeLatArr = {49.1217, 49.12175, 49.145};
        Double[] routeLngArr = {-123.1208, -123.1209, -123.1210};
        double startLat = routeLatArr[0];
        double startLng = routeLngArr[0];
        assert routeLatArr.length == routeLngArr.length;
        double destLat = routeLatArr[routeLatArr.length - 1];
        double destLng = routeLatArr[routeLngArr.length - 1];
        if(userCoord.getLat()==destLat&&userCoord.getLng()==destLng) {
            if (abs(destLat - startLat) < 0.02 || abs(destLng - startLng) < 0.02) {
                Toast.makeText(getApplicationContext(), "Sorry " + model.getCurrentUser().getName() + ", your walk is not long enough to be counted for rewards", Toast.LENGTH_LONG).show();
            } else {
                currentPoints = currentPoints + 5;
                totalPoints = totalPoints + 5;
                currentUser.setCurrentPoints(currentPoints);
                currentUser.setTotalPointsEarned(totalPoints);
                model.updateUser(currentUser, this :: getUserUpdateCallBack);
                Log.d("completewalk", "completewalk");
                Log.d("", "let my check the points" + currentPoints + "  " + totalPoints);
            }
        }
    }
    private void getUserUpdateCallBack(User user){}

}