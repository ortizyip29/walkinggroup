package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;

public class SendMessageActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private User currentUser = model.getCurrentUser();
    private List<Group> userIsLeaderOf = currentUser.getLeadsGroups();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        setupMsgToGroupBtn();
        setupMsgToParentsBtn();
        setupMsgEmergencyBtn();
    }

    private void setupMsgToGroupBtn() {
        Button btnMsgGroup = (Button) findViewById(R.id.btnMsgGroup);
        btnMsgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userIsLeaderOf.isEmpty()) {
                    Toast.makeText(SendMessageActivity.this,"you are not leading any groups!",Toast.LENGTH_LONG).show();
                    finish();
                }

                else {
                    Intent intent = new Intent(SendMessageActivity.this,ChooseWhichGroupMsgActivity.class);
                    startActivity(intent);
                }


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
