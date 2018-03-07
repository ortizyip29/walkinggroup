package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.SharedPreferenceLoginState;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (SharedPreferenceLoginState.getEmail(MapsActivity.this).length() == 0){
            Log.i("Login Activity", SharedPreferenceLoginState.getEmail(MapsActivity.this));
            Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
            startActivity(intent);

        }else{

        }
    }
}
