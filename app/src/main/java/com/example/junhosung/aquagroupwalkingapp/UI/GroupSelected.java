package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.junhosung.aquagroupwalkingapp.R;

public class GroupSelected extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selected);
        setButtonLauchListUsers();

    }

    private void setButtonLauchListUsers() {
        Button btn = (Button) findViewById(R.id.launchListUsersActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupSelected.this, ListUsers.class);
                startActivity(intent);
            }
        });
    }
}
