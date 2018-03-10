package com.example.junhosung.aquagroupwalkingapp.UI;


/**
 * SeeMonitoreding Activity
 *
 * Allows the user to see list of people the user is monitoring
 * The user can then select the add button to add another user to monitor
 * The user can select on the listView, make selection
 *
 * TODO: The button functionalities not built in yet ... coming soon
 */



import android.app.Activity;
import android.content.Intent;
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
import com.example.junhosung.aquagroupwalkingapp.model.User;

public class SeeMonitoringActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    User user = model.users.getEmail(0);
    Button btnAddMonitoring;
    Button btnDeleteMonitoring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitoring);

        User user2 = new User("harro@gmail.com","1");
        User user3 = new User("harro1@gmail.com","2");

        user.addNewMonitorsUsers(user2);
        user.addNewMonitorsUsers(user3);

        populateListView();
        setUpAddButton();


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(SeeMonitoringActivity.this,"number of monitoring: "+String.valueOf(user.countMonitoring()),Toast.LENGTH_SHORT).show();
                    populateListView();
                }


        }
    }


    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                user.getMonitorsUsers());

        ListView list = (ListView) findViewById(R.id.monitorList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
                Intent i = new Intent(SeeMonitoringActivity.this,DeleteMonitoringActivity.class);
                startActivityForResult(i,2);
            }
        });

    }


}
