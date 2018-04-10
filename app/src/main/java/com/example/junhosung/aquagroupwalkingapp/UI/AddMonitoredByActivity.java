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

/**
 *
 * This class asks the User to write the email of the person the User would like to he monitored by.
 *
 */

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
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_add_monitored_by);

        Button btnAddMonitoredBy = (Button) findViewById(R.id.btnAddNewMonitredBy);
        btnAddMonitoredBy.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btnAddMonitoredBy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /**
                 *   if the email is in our list of user emails, then send a call to server to add this person to the
                 *   monitored by list and return to the previous activity.
                 */

                EditText newUser = (EditText) findViewById(R.id.typeEmail2);
                String email = newUser.getText().toString();


                for (int i = 0; i< usersServer.size(); i++) {
                    if (usersServer.get(i).getEmail().equals(email)) {
                        userMatch = usersServer.get(i);
                        model.addNewMonitoredBy(model.getCurrentUser().getId(),userMatch,this::responseWithAddNewMonitoredBy);
                    }
                }

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);

                finish();

            }

            // mandatory response function ... does nothing

            private void responseWithAddNewMonitoredBy(List<User> users) {
                tempList = users;
            }


        });


    }
}
