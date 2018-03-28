package com.example.junhosung.aquagroupwalkingapp.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.GpsLocation;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class ParentDashboard extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap parentMap;
    Model model = Model.getInstance();
    MarkerOptions childMarker;
    MarkerOptions monitoredMarker;
    Group currentGroup = model.getCurrentGroupInUseByUser();
    User currentUser = model.getCurrentUser();
    String[] groupMembers;
    double currentLat = 0.00;
    double currentLng = 0.00;
    double prevLat = 0.00;
    double prevLng = 0.00;
    int minuteSinceUpdate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        setupSendButton();
        setupViewButton();
        MapFragment parentMapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.parentMapFragment));
        parentMapFrag.getMapAsync(this);
        //childLocationTimer();
        updateListOfMonitoring();
        getCurrentMembersInGroup();
        getUserAttributesAndLocation();
        updateChildrenLocation();
        sendMyLocation();
       // setChildLocation();
        refreshMonitoringLocation();
    }

    private void setupViewButton() {
        Button viewButton = (Button) findViewById(R.id.viewMsgBtn);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ParentDashboard.this,MessagePanelActivity.class);
                startActivity(i);
            }
        });
    }

    private void setupSendButton() {
        Button sendButton = (Button) findViewById(R.id.sendMsgBtn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ParentDashboard.this,SendMessageActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        parentMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        parentMap.setMyLocationEnabled(true);
        LatLng displayLatLng = new LatLng(currentUser.getLastGpsLocation().getLat(),currentUser.getLastGpsLocation().getLng());
        displayLatLng = new LatLng(49.2767,-122.918);
        Log.d("tag","wewerehere"+currentUser.getLastGpsLocation().getLat());
        parentMap.moveCamera(CameraUpdateFactory.newLatLng(displayLatLng));
        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(displayLatLng, 12);
        parentMap.animateCamera(defaultDisplay);
    }
    public void getCurrentMembersInGroup(){
        if(model.getCurrentGroupInUseByUser()==null){
            responseGetUserAttributes(null);
        } else {
            model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseGetUserAttributes);
        }
    }
    private void sendMyLocation(){
        GpsLocation myCurrentLocation = new GpsLocation(0,0,null);
        myCurrentLocation.getLat();
        myCurrentLocation.getLng();
        //Log.d("check lat", "onLocationChanged"+ setLatList);
        //Log.d("check lng", "onLocationChanged"+ setLngList);
        currentUser.setLastGpsLocation(myCurrentLocation);
        model.updateUser(currentUser, this::myLocationCallback);
        Log.d("check lat", "onLocationChanged"+ currentUser.getLastGpsLocation().getLat());
        Log.d("check lng", "onLocationChanged"+ currentUser.getLastGpsLocation().getLng());
    }
    private void responseGetUserAttributes(List<User> users) {
        List<String> members = new ArrayList<>();
            for (User user : users) {
                members.add(user.getName());
                //user.getLastGpsLocation();
                //LatLng currentLocation = new LatLng(49.1617, -123.1019);
                LatLng currentLocation = new LatLng(user.getLastGpsLocation().getLat(), user.getLastGpsLocation().getLng());
                currentLat = user.getLastGpsLocation().getLat();
                currentLng = user.getLastGpsLocation().getLng();
                if(currentLat == NULL || currentLng == NULL){
                    Toast.makeText(getApplicationContext(), "No monitoring member in range", Toast.LENGTH_SHORT).show();
                }
                //LatLng currentLocation = new LatLng(user.getLastGpsLocation().getLat(), user.getLastGpsLocation().getLng());
                childMarker = new MarkerOptions().position(currentLocation).title("Group Member: "+user.getName());
                parentMap.addMarker(childMarker);
            }
            groupMembers = members.toArray(new String[members.size()]);
            Log.d("", "responseForGetCurrentMembersInGroup:" + groupMembers);
    }
    private void myLocationCallback(User user) {
    }
    private void responseSetChildLocation(List<User> users){
    }
    private void updateChildrenLocation(){
        for(User user:currentGroup.getMemberUsers()){
            sendMyLocation();
        }
        model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseSetChildLocation);
    }
    //model.getCurrentGroupInUseByUser().getId()
   /* private void setChildLocation(){
        model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseSetChildLocation);
    }*/
    private void getUserAttributesAndLocation(){
        model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseGetUserAttributes);
    }
    private void updateListOfMonitoring(){
        model.getMonitorsById(model.getCurrentUser().getId(), this::responseWithUserMonitorsOnActivityResult);
    }
    private void responseWithUserMonitorsOnActivityResult(List<User> users) {
        for(User user:users){
            Log.d("TAG",user.toString());
            Log.d("tag", "monitoringperson"+ user.getLastGpsLocation().getLat());
            Log.d("tag", "monitoringperson"+ user.getLastGpsLocation().getLng());
            Log.d(     "tag","monitoringperson"+user.getName());
            prevLat = currentLat;
            prevLng = currentLng;
            //LatLng monitoredLocation = new LatLng(49.2827,-123.1208);
            currentLat = user.getLastGpsLocation().getLat();
            currentLng = user.getLastGpsLocation().getLng();
            LatLng monitoredLocation = new LatLng(currentLat, currentLng);
            if(currentLat == NULL || currentLng == NULL){
                Toast.makeText(getApplicationContext(), "No monitoring member or child in range", Toast.LENGTH_SHORT).show();
            }
            else if(currentLng <0.001 && currentLng <0.001){
                Toast.makeText(getApplicationContext(), "No monitoring member or child in range", Toast.LENGTH_SHORT).show();
            }
            monitoredMarker = new MarkerOptions().position(monitoredLocation).title("Location for Monitoring Member: "+user.getName()).snippet("updated "+ minuteSinceUpdate + " minutes ago");
            parentMap.addMarker( monitoredMarker);
        }
    }
    //update location if possible, or update the list
    private void refreshMonitoringLocation() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Monitoring users' location being updated",Toast.LENGTH_SHORT).show();
                parentMap.clear();
                if(prevLat != currentLat || prevLng != currentLng){
                    minuteSinceUpdate =0;
                }
                else {
                    minuteSinceUpdate++;
                }
                updateListOfMonitoring();
                getCurrentMembersInGroup();
                getUserAttributesAndLocation();
                updateChildrenLocation();
                sendMyLocation();
                start();
            }
        }.start();
    }
}
