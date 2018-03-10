package com.example.junhosung.aquagroupwalkingapp.UI;

/**
 * SeeMonitoredBy Activity
 *
 * Allows the user to see list of people the user is being monitored by.
 * The user can then select people to be deleted from list.
 *
 * TODO: When you click on the listView, you should be able to select ppl to delete - coming soon
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

public class SeeMonitoredByActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    User user = model.users.getEmail(0);
    Button btnAddMonitoredBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitored_by);

        // test cases - not permanent

        User user3 = new User("harro3@gmail.com","1");
        User user4 = new User("harro4@gmail.com","2");

        user.addNewMonitoredByUsers(user3);
        user.addNewMonitoredByUsers(user4);

        setUpAddButton();
        populateListView();

    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.see_monitored_by,
                user.getMonitoredByUsers());

        ListView list = (ListView) findViewById(R.id.monitoredByList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do something here
            }
        });

    }

    private void setUpAddButton() {
        btnAddMonitoredBy = (Button) findViewById(R.id.btnAddMonitoredBy);
        btnAddMonitoredBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeeMonitoredByActivity.this,AddMonitoredByActivity.class);
                startActivityForResult(i,2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(SeeMonitoredByActivity.this,"number of monitoredBy: "+String.valueOf(user.countMonitoredBy()),
                            Toast.LENGTH_SHORT).show();
                    populateListView();
                }
        }
    }



}
