package com.example.junhosung.aquagroupwalkingapp.UI;

/**
 * SeeMonitoredBy Activity
 *
 * Allows the mUser2 to see list of people the mUser2 is being monitored by.
 * The mUser2 can then select people to be deleted from list.
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
import com.example.junhosung.aquagroupwalkingapp.model.User2;

public class SeeMonitoredByActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    User2 mUser2 = model.users.getEmail(0);
    Button btnAddMonitoredBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitored_by);

        // test cases - not permanent

        User2 user23 = new User2("harro3@gmail.com","1");
        User2 user24 = new User2("harro4@gmail.com","2");

        mUser2.addNewMonitoredByUsers(user23);
        mUser2.addNewMonitoredByUsers(user24);

        setUpAddButton();
        populateListView();

    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.see_monitored_by,
                mUser2.getMonitoredByUser2s());

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
                    Toast.makeText(SeeMonitoredByActivity.this,"number of monitoredBy: "+String.valueOf(mUser2.countMonitoredBy()),
                            Toast.LENGTH_SHORT).show();
                    populateListView();
                }
        }
    }



}
