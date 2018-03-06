package com.example.junhosung.aquagroupwalkingapp.UI;

/**
 * SeeMonitoredBy Activity
 *
 * Allows the user to see list of people the user is being monitored by.
 * The user can then select people to be deleted from list.
 *
 * TODO:
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.junhosung.aquagroupwalkingapp.R;

public class SeeMonitoredByActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitored_by);
    }
}
