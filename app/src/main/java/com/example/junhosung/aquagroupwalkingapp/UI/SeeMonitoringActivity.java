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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitoring);

        //User2 user22 = new User2("harro@gmail.com","1");
        //User2 user23 = new User2("harro1@gmail.com","2");

        Toast.makeText(SeeMonitoringActivity.this, currentUserEmail,Toast.LENGTH_LONG).show();

        findCurrentUserByEmail(currentUserEmail);

        //Toast.makeText(SeeMonitoringActivity.this,currentUser.getEmail(),Toast.LENGTH_LONG).show();

        //mUser2.addNewMonitorsUsers(user22);
        //mUser2.addNewMonitorsUsers(user23);

        //populateListView();
        //setUpAddButton();

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

    /**

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(SeeMonitoringActivity.this,"number of monitoring: "+String.valueOf(mUser2.countMonitoring()),Toast.LENGTH_SHORT).show();
                    populateListView();
                }
        }
    }


    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                mUser2.getMonitorsUser2s());

        ListView list = (ListView) findViewById(R.id.monitorList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do something here
            }
        });

    }

    // takes you to the add monitoree section so that you

    private void setUpAddButton() {
        btnAddMonitoring = (Button) findViewById(R.id.btnAddMonitoree);
        btnAddMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeeMonitoringActivity.this,AddMonitoringActivity.class);
                startActivityForResult(i,1);
            }
        });

    }

    private void setUpDeleteButton() {
        btnDeleteMonitoring = (Button) findViewById(R.id.btnDeleteMonitoree);
        btnDeleteMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do smth here
            }
        });
    }

    */


}
