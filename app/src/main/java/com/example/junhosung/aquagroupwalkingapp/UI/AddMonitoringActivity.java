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
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;



/**
 *  Activity to add new monitors to your monitoring list.
 */

public class AddMonitoringActivity extends AppCompatActivity {

    private Button btnAddMonitoring;
    private Model model = Model.getInstance();
    List<User> usersServer = model.users.returnUsers();
    String currentUserEmail = model.getCurrentUser().getEmail();
    private final String TAG = "AddMonitoringActivity";
    private User targetUser;
    User currentUser = model.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_add_monitoree);

        btnAddMonitoring = (Button) findViewById(R.id.btnAddMonitoring);
        btnAddMonitoring.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btnAddMonitoring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText newUser = (EditText) findViewById(R.id.typeEmail);
                String email = newUser.getText().toString();

                model.getUserByEmail(email,this::responseWithUserEmail);

            }

            private void responseWithUserEmail(User user) {
              if(user!=null){
                  targetUser = user;
                  model.addNewMonitors(currentUser.getId(),targetUser,this::responseAddNewMonitors);

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
