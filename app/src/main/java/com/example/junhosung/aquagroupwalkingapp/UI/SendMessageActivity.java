package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.junhosung.aquagroupwalkingapp.R;

public class SendMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        setupMsgToGroupBtn();
    }

    private void setupMsgToGroupBtn() {
        Button btnMsgGroup = (Button) findViewById(R.id.btnMsgGroup);
        btnMsgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendMessageActivity.this,SendMsgToGroupActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setupMsgToParentsBtn() {
        Button btnMsgGroup = (Button) findViewById(R.id.btnMsgParent);
        btnMsgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendMessageActivity.this,SendMsgToParentsActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setupMsgEmergencyBtn() {
        Button btnMsgGroup = (Button) findViewById(R.id.btnMsgEmergency);
        btnMsgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendMessageActivity.this,SendEmergencyMsgActivity.class);
                startActivity(intent);

            }
        });

    }



}