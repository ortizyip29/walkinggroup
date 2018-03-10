package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.junhosung.aquagroupwalkingapp.model.SharedPreferenceLoginState;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.example.junhosung.aquagroupwalkingapp.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapDisplay;
    // private SupportMapFragment mapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MapFragment mapFrag = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag));
        mapFrag.getMapAsync(this);
        Button btn = (Button) findViewById(R.id.monitorbtn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, MapOptionsActivity.class);
                startActivity(intent);
            }
        });
        // Button updateButton = (Button)findViewById(R.id.btnUpdate);
        setUpLogoutBtn();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapDisplay = googleMap;
        LatLng defaultLocation = new LatLng(49.2827, -123.1207);
        mapDisplay.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mapDisplay.addMarker(new MarkerOptions().position(defaultLocation).title("I'm here"));
        mapDisplay.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        CameraUpdate defaultDisplay = CameraUpdateFactory.newLatLngZoom(defaultLocation, 16);
        mapDisplay.animateCamera(defaultDisplay);

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
}
