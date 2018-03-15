package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.User2;
import com.example.junhosung.aquagroupwalkingapp.model.UserCollection;

import java.util.List;

public class AddMonitoredByActivity extends AppCompatActivity {

    private Button btnAddMonitoring;
    private Model model = Model.getInstance();
    List<User> usersServer = model.users.returnUsers();
    User receivedUser;
    String currentUserEmail = model.getCurrentUser().getEmail();
    User userMatch;
    List<User> tempList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitored_by);

        // this is here since the for loop inside the onClickListener gives me trouble about
        // calling usersOld.countUsers() from an inner class ...

        Button btnAddMonitoredBy = (Button) findViewById(R.id.btnAddNewMonitredBy);
        btnAddMonitoredBy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText newUser = (EditText) findViewById(R.id.typeEmail2);
                String email = newUser.getText().toString();

                // if the username (=email)is in the UserServer

                for (int i = 0; i< usersServer.size(); i++) {
                    if (usersServer.get(i).getEmail().equals(email)) {
                        userMatch = usersServer.get(i);
                        model.getUserByEmail(currentUserEmail,this::responseWithUserEmail);
                    }

                }

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);

                finish();

            }

            private void responseWithUserEmail(User user) {
                receivedUser = user;
                model.addNewMonitoredBy(receivedUser.getId(),userMatch,this::responseWithAddNewMonitoredBy);

            }

            private void responseWithAddNewMonitoredBy(List<User> users) {
                //tempList = users;
                // well this is a @POST so it doesn't return anything we need to use.

            }


        });


    }
}
