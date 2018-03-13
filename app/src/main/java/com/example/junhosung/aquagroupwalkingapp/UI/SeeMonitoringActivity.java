package com.example.junhosung.aquagroupwalkingapp.UI;


/**
 * SeeMonitoreding Activity
 *
 * Allows the mUser2 to see list of people the mUser2 is monitoring
 * The mUser2 can then select the add button to add another mUser2 to monitor
 * The mUser2 can select on the listView, make selection
 *
 * TODO: The button functionalities not built in yet ... coming soon
 */



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.UserCollectionServer;

public class SeeMonitoringActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private UserCollectionServer users = model.users;
    //User2 mUser2 = model.usersOld.getUser(0);
    Button btnAddMonitoring;
    Button btnDeleteMonitoring;
    String currentUserEmail = model.getCurrentUser().getEmail();
    User currentUser;
    User receivedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitoring);

        model.getUserByEmail("bigDaddy5@gmail.com",this::responseWithUserEmail);

        //Toast.makeText(SeeMonitoringActivity.this, currentUserEmail,Toast.LENGTH_LONG).show();

        //findCurrentUserByEmail(currentUserEmail);

        //model.getUserById(Long.valueOf(505),this::responseWithUser);



    }

    private void findCurrentUserByEmail(String email) {
        int counter = users.countUsers();
        for (int i = 0; i < counter; i++ ) {
            if (users.getUser(i).getEmail().equals(email)) {
                currentUser = users.getUser(i);
                Toast.makeText(SeeMonitoringActivity.this,currentUser.getName(),Toast.LENGTH_LONG).show();
            }
        }
    }

    /*private void responseWithUser(User user) {
        this.receivedUser = user;
    }
*/
    private void responseWithUserEmail(User user) {
        receivedUser = user;
        Toast.makeText(SeeMonitoringActivity.this,receivedUser.toString(),Toast.LENGTH_LONG).show();
    }



}
