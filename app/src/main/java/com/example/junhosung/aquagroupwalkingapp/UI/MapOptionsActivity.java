package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;
import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;

public class MapOptionsActivity extends AppCompatActivity implements  GestureDetector.OnGestureListener{
    GestureDetector gestureDectector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDectector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.w(TAG, "OnDown");
        finish();
        return true;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.w(TAG, "OnShowPress");
    }
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.w(TAG, "Single Tap");
        Intent monitoring = new Intent(MapOptionsActivity.this,ListUsers.class);
        startActivity(monitoring);
        return true;
    }
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.w(TAG, "OnScroll");
        return true;
    }
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.w(TAG, "OnLongPress");
        Intent monitoring = new Intent(MapOptionsActivity.this,ListUsers.class);
        startActivity(monitoring);
    }
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.w(TAG, "OnFling");
        return true;
    }

    private static final String TAG = "MapOptionsActivity";
    private Button seeMonitoringButton;
    private Button seeMonitoredByButton;
    private Button backToMapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_options);

        this.gestureDectector = new GestureDetector(this,this);
    //    populatelistMonintering();
        seeMonitoringButton = (Button) findViewById(R.id.btnMapMonitor);
        seeMonitoringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monitoring = new Intent(MapOptionsActivity.this,SeeMonitoringActivity.class);
                startActivity(monitoring);
            }
        });


        seeMonitoredByButton = (Button) findViewById(R.id.btnMapMonitoredBy);
        seeMonitoredByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monitoredBy = new Intent(MapOptionsActivity.this,SeeMonitoredByActivity.class);
                startActivity(monitoredBy);
            }
        });

      /*  backToMapButton = (Button) findViewById(R.id.btnBackToMap);
        backToMapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
    }

    private void populatelistMonintering() {
        ListView monitoringList = (ListView) findViewById(R.id.listMonitoring);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.<new textView.xml>,<string list>);
//        monitoringList.setAdapter(adapter);

    }


}



