package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;
import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;

import retrofit2.Call;

public class MapOptionsActivity extends AppCompatActivity {

    private Button seeMonitoringButton;
    private Button seeMonitoredByButton;
    private Button backToMapButton;

    private WGServerProxy proxy;


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


        proxy = ProxyBuilder.getProxy(getString(R.string.apikey), null);


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

    private void setupNewUserButton() {
        Button btn = findViewById(R.id.btnBackToMap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new user
                User user = new User();
                user.setEmail("bigDaddy5@gmail.com");
                user.setName("Big Daddy5");
                user.setPassword("bigDaddyPassword5");

                // Make call
                Call<User> caller = proxy.createNewUser();
                ProxyBuilder.callProxy(MapOptionsActivity.this, caller, returnedUser -> response(returnedUser));
            }
        });
    }

    private void response(User user) {
        Log.w(TAG, "Server replied with user: " + user.toString());
        userId = user.getId();
    }
}



