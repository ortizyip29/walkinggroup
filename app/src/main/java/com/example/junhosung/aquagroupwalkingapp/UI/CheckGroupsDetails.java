package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.junhosung.aquagroupwalkingapp.R;

public class CheckGroupsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_groups_details);

        setupBtnForGroupsLeaderOf();

    }

    private void setupBtnForGroupsLeaderOf() {
        Button GroupsLeaderOf  = (Button) findViewById(R.id.GroupsLeaderOf);
        GroupsLeaderOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckGroupsDetails.this,GroupsLeaderOfActivity.class);
                startActivity(intent);
            }
        });
    }


}
