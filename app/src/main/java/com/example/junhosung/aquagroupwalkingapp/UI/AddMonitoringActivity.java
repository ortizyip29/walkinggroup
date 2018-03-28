package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;

public class AddMonitoringActivity extends AppCompatActivity {

    private Button btnAddMonitoring;
    private Model model = Model.getInstance();
    List<User> usersServer = model.users.returnUsers();
    String[] newList;
    List<User> tempList;
    User receivedUser;
    String currentUserEmail = model.getCurrentUser().getEmail();
    User userMatch;
    private final String TAG = "AddMonitoringActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitoree);

        //model.addNewMonitors(Long.valueOf(107),Long.valueOf(196),this::responseAddNewMonitors);

        btnAddMonitoring = (Button) findViewById(R.id.btnAddMonitoring);
        btnAddMonitoring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText newUser = (EditText) findViewById(R.id.typeEmail);
                String email = newUser.getText().toString();

                model.getUserByEmail(email,this::responseWithUserEmail);

            }

            private void responseWithUserEmail(User user) {
              if(user!=null){

                  model.addNewMonitors(model.getCurrentUser().getId(),user,this::responseAddNewMonitors);
              }

              else {
                  Toast.makeText(AddMonitoringActivity.this,"This email does not match our records ...",
                          Toast.LENGTH_LONG).show();
                  finish();
              }

            }

            private void responseAddNewMonitors(List<User> users) {

                Toast.makeText(AddMonitoringActivity.this,"Success",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}
