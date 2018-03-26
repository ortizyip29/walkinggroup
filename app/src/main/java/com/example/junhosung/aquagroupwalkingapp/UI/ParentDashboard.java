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
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class ParentDashboard extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap parentMap;
    Model model = Model.getInstance();
    Group currentGroup = model.getCurrentGroupInUseByUser();
    User currentUser = model.getCurrentUser();
    String[] groupMembers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        setupSendButton();
        setupViewButton();
        MapFragment parentMapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.parentMapFragment));
        parentMapFrag.getMapAsync(this);
        getCurrentMembersInGroup();
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
            responseForGetCurrentMembersInGroup(null);
        } else {
            model.getMembersOfGroup(model.getCurrentGroupInUseByUser().getId(),this::responseForGetCurrentMembersInGroup);
        }
    }
    private void responseForGetCurrentMembersInGroup(List<User> users) {
        List<String> members = new ArrayList<>();
        if (!(users == null)) {
            for (User user : users) {
                members.add(user.getName() + " , " + user.getEmail());
            }
            groupMembers = members.toArray(new String[members.size()]);
            Log.d("", "responseForGetCurrentMembersInGroup:" + groupMembers);
        }
    }
}
