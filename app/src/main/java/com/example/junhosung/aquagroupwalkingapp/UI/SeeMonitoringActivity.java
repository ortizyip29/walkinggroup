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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.SharedPreferenceLoginState;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.UserCollectionServer;

import java.util.List;


public class SeeMonitoringActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private UserCollectionServer users = model.users;
    Button btnAddMonitoring;
    Button btnDeleteMonitoring;
    String currentUserEmail = model.getCurrentUser().getEmail();
    User receivedUser;
    List<User> monitorsList;
    String[] nameAndEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitoring);

        //model.getUserById(Long.valueOf(53),this::responseWithUserId);

        model.getUserByEmail(currentUserEmail,this::responseWithUserEmail);

        setUpAddButton();
    }


    private void responseWithUserEmail(User user) {
        receivedUser = user;
        model.getMonitorsById(receivedUser.getId(),this::responseWithUserMonitors);
    }

    private void responseWithUserMonitors(List<User> users) {
        monitorsList = users;
        nameAndEmail = new String [monitorsList.size()];

        for (int i = 0; i < monitorsList.size();i++) {
            nameAndEmail[i] ="        " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
        }

        populateListView();

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    //Toast.makeText(SeeMonitoringActivity.this,"number of monitoring: "+String.valueOf(mUser2.countMonitoring()),Toast.LENGTH_SHORT).show();
                    //populateListView();
                }
        }
    }



    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                nameAndEmail);

        ListView list = (ListView) findViewById(R.id.monitorList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do something here
            }
        });

    }

    //

    // takes you to the addMonitoringActivity

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


}
