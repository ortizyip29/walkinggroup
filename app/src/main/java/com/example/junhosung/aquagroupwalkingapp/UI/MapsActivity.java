package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.example.junhosung.aquagroupwalkingapp.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends AppCompatActivity{
    private MapView mapDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Button btn = (Button)findViewById(R.id.monitorbtn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MapsActivity.this,MapOptionsActivity.class);
                startActivity(intent);
            }
        });
       mapDisplay = (MapView)findViewById(R.id.mapView);
    }
}
