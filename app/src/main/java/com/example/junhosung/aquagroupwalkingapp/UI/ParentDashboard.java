package com.example.junhosung.aquagroupwalkingapp.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    MarkerOptions marker;
    Group currentGroup = model.getCurrentGroupInUseByUser();
    User currentUser = model.getCurrentUser();
    String[] groupMembers;
    double currentUserLat = 0.00;
    double currentUserLng = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        setupSendButton();
        setupViewButton();
        MapFragment parentMapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.parentMapFragment));
        parentMapFrag.getMapAsync(this);
        getCurrentMembersInGroup();
        updateChildrenLocation();
        myLocationCallback();
        setChildLocation();
        getUserAttributesAndLocation();
        Log.d("", "responseForGetCurrentMembersInGroup:" + groupMembers);
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
    }
    public void getCurrentMembersInGroup(){
        if(model.getCurrentGroupInUseByUser()==null){
            responseGetUserAttributes(null);
        } else {
            model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseGetUserAttributes);
        }
    }
    private void sendMyLocation(User user){
        //currentUser = user ;
        GpsLocation myCurrentLocation = new GpsLocation();
        myCurrentLocation.setLat(currentUserLat);
        myCurrentLocation.setLng(currentUserLng);
        //setLatList = Arrays.asList(currentUserLat);
        //setLngList = Arrays.asList(currentUserLng);
        //Log.d("check lat", "onLocationChanged"+ setLatList);
        //Log.d("check lng", "onLocationChanged"+ setLngList);
        user.setLastGpsLocation(myCurrentLocation);
    }
    private void responseGetUserAttributes(List<User> users) {
        List<String> members = new ArrayList<>();
        if (!(users == null)) {
            for (User user : users) {
                members.add(user.getName());
                user.getLastGpsLocation();
                LatLng currentLocation = new LatLng(user.getLastGpsLocation().getLat(), user.getLastGpsLocation().getLng());
                marker = new MarkerOptions().position(currentLocation).title(user.getName());
                parentMap.addMarker(marker);
            }
            groupMembers = members.toArray(new String[members.size()]);
            Log.d("", "responseForGetCurrentMembersInGroup:" + groupMembers);
        }
    }
    private void myLocationCallback() {
        model.updateUser(currentUser, this::sendMyLocation);
    }
    private void updateChildrenLocation(){
        model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseSetChildLocation);
    }
    private void responseSetChildLocation(List<User> users){
        for(User user:users){
            myLocationCallback();
        }
    }
    private void setChildLocation(){
        model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseSetChildLocation);
    }
    private void getUserAttributesAndLocation(){
        model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseGetUserAttributes);
    }
}
