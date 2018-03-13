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
import com.example.junhosung.aquagroupwalkingapp.model.User2;
import com.example.junhosung.aquagroupwalkingapp.model.UserCollection;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitoree);

        //model.addNewMonitors(Long.valueOf(107),Long.valueOf(196),this::responseAddNewMonitors);

        btnAddMonitoring = (Button) findViewById(R.id.btnAddMonitoring);
        btnAddMonitoring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Extract data from UI

                EditText newUser = (EditText) findViewById(R.id.typeEmail);
                String email = newUser.getText().toString();

                for (int i  = 0; i < usersServer.size();i++ ){
                    if (usersServer.get(i).getEmail().equals(email)) {
                        userMatch = usersServer.get(i);
                        model.getUserByEmail(currentUserEmail,this::responseWithUserEmail);
                    }
                }
                //Toast.makeText(AddMonitoringActivity.this,email,Toast.LENGTH_SHORT);

                // Going back to SeeMonitoringActivity


                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);

                finish();

            }

            private void responseWithUserEmail(User user) {
                receivedUser = user;
                model.addNewMonitors(receivedUser.getId(),userMatch,this::responseAddNewMonitors);
            }

            private void responseAddNewMonitors(List<User> users) {

                // well this doesn't really return anything ... so what's below will probably removed ...
                tempList = users;
                Toast.makeText(AddMonitoringActivity.this,tempList.get(0).getId()+"",Toast.LENGTH_LONG).show();
            }

        });


    }



}
