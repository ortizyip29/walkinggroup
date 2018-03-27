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
import android.widget.TextView;

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

public class ParentDashboard extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap parentMap;
    Model model = Model.getInstance();
    MarkerOptions childMarker;
    MarkerOptions monitoredMarker;
    Group currentGroup = model.getCurrentGroupInUseByUser();
    User currentUser = model.getCurrentUser();
    String[] groupMembers;
    double currentUserLat = 0.00;
    double currentUserLng = 0.00;
    long secondElapsed = 0;
    long minuteElapsed =0;
    boolean reachDestination = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        setupSendButton();
        setupViewButton();
        MapFragment parentMapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.parentMapFragment));
        parentMapFrag.getMapAsync(this);
        updateListOfMonitoring();
        getCurrentMembersInGroup();
        getUserAttributesAndLocation();
        updateChildrenLocation();
        sendMyLocation();
        setChildLocation();

        Log.d("", "responseForGetCurrentMembersInGroup:" + currentGroup.getMemberUsers());
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
        parentMap.moveCamera(CameraUpdateFactory.newLatLng((new LatLng(49.28,-123.1210))));
        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom((new LatLng(49.28,-123.1210)), 15);
        parentMap.animateCamera(defaultDisplay);
    }
    public void getCurrentMembersInGroup(){
        if(model.getCurrentGroupInUseByUser()==null){
            responseGetUserAttributes(null);
        } else {
            model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseGetUserAttributes);
        }
        Log.d("tag","my honey is here ");// user.getName());
    }
    private void sendMyLocation(){
        //currentUser = user ;
        GpsLocation myCurrentLocation = new GpsLocation();
        //myCurrentLocation.setLat(currentUserLat);
        //myCurrentLocation.setLng(currentUserLng);
        myCurrentLocation.getLat();
        myCurrentLocation.getLng();
        //setLatList = Arrays.asList(currentUserLat);
        //setLngList = Arrays.asList(currentUserLng);
        //Log.d("check lat", "onLocationChanged"+ setLatList);
        //Log.d("check lng", "onLocationChanged"+ setLngList);
        currentUser.setLastGpsLocation(myCurrentLocation);
        model.updateUser(currentUser, this::myLocationCallback);
        Log.d("check lat", "onLocationChanged"+ currentUser.getLastGpsLocation().getLat());
        Log.d("check lng", "onLocationChanged"+ currentUser.getLastGpsLocation().getLng());

    }
    private void responseGetUserAttributes(List<User> users) {
        List<String> members = new ArrayList<>();
       // if (!(users == null)) {
            for (User user : users) {
                members.add(user.getName());
                user.getLastGpsLocation();
                //LatLng currentLocation = new LatLng(49.1617, -123.1019);
                LatLng currentLocation = new LatLng(user.getLastGpsLocation().getLat(), user.getLastGpsLocation().getLng());
                Log.d("tag","my child is "+ user.getName());
                Log.d("tag", "child is at"+ user.getLastGpsLocation().getLat());
                //LatLng currentLocation = new LatLng(user.getLastGpsLocation().getLat(), user.getLastGpsLocation().getLng());
                childMarker = new MarkerOptions().position(currentLocation).title("Group Member: "+user.getName());
                parentMap.addMarker(childMarker);
            }
            groupMembers = members.toArray(new String[members.size()]);
            Log.d("", "responseForGetCurrentMembersInGroup:" + groupMembers);
        //}
    }
    private void myLocationCallback(User user) {
    }
    private void responseSetChildLocation(List<User> users){
    }
    private void updateChildrenLocation(){
        for(User user:currentGroup.getMemberUsers()){
            //currentUser = user;
            sendMyLocation();
        }
        model.getMembersOfGroup(Long.valueOf(15),this::responseSetChildLocation);
    }
    //model.getCurrentGroupInUseByUser().getId()
    private void setChildLocation(){
        model.getMembersOfGroup(Long.valueOf(15),this::responseSetChildLocation);
    }
    private void getUserAttributesAndLocation(){
        model.getMembersOfGroup(Long.valueOf(15),this::responseGetUserAttributes);
    }
    private void childLocationTimer() {
        TextView updateTime = (TextView) findViewById(R.id.textViewTimeUpdate);
        new CountDownTimer(600000, 1000) {
            public void onTick(long millisUntilFinished) {
                secondElapsed = millisUntilFinished / 1000;
                minuteElapsed = secondElapsed/60;
                updateTime.setText( Long.toString(minuteElapsed)+"Minute"+ Long.toString(secondElapsed) + " Seconds");
                /*if(reachDestination){
                    cancel();
                }*/
            }
            public void onFinish() {
                updateTime.setText(Long.toString(minuteElapsed)+"Minute" + Long.toString(secondElapsed) + " Seconds");
                updateTime.setText("The child has arrived at his destination with his group");
            }
        }.start();
    }
    private void updateListOfMonitoring(){
        model.getMonitorsById(model.getCurrentUser().getId(), this::responseWithUserMonitorsOnActivityResult);
    }
    private void responseWithUserMonitorsOnActivityResult(List<User> users) {
        if(users==null){
            Log.v("TAG","Users is null----------------------------------------------------");
        } else{
            Log.v("TAG","Users is not null----------------------------------------------");
        }
        for(User user:users){
            Log.i("TAG",user.toString());
            Log.i("tag", "monitoringperson"+ user.getLastGpsLocation().getLat());
            Log.i("tag", "monitoringperson"+ user.getLastGpsLocation().getLng());
            Log.i(     "tag","monitoringperson"+user.getName());
            //LatLng monitoredLocation = new LatLng(user.getLastGpsLocation().getLat(),user.getLastGpsLocation().getLng());
            LatLng monitoredLocation = new LatLng(user.getLastGpsLocation().getLat(),user.getLastGpsLocation().getLng());
            monitoredMarker = new MarkerOptions().position(monitoredLocation).title("Monitoring Member "+user.getName());
            parentMap.addMarker( monitoredMarker);
        }
    }
}
