package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.junhosung.aquagroupwalkingapp.R;

public class MapOptionsActivity extends AppCompatActivity {

    private Button seeMonitoringButton;
    private Button seeMonitoredByButton;
    private Button backToMapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_options);

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

        backToMapButton = (Button) findViewById(R.id.btnBackToMap);
        backToMapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}



