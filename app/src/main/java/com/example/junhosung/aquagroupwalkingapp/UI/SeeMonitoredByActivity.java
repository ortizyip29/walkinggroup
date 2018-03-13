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
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.User2;

import java.util.List;

public class SeeMonitoredByActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    Button btnAddMonitoredBy;
    String currentUserEmail = model.getCurrentUser().getEmail();
    User receivedUser;
    List<User> monitoredByList;
    String[] nameAndEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitored_by);

        model.getUserByEmail(currentUserEmail,this::responseWithUserEmail);

        setUpAddButton();
        //populateListView();

    }

    private void responseWithUserEmail(User user) {
        receivedUser = user;
        Toast.makeText(SeeMonitoredByActivity.this,""+receivedUser.getId(),Toast.LENGTH_LONG).show();
        model.getMonitoredById(receivedUser.getId(),this::responseWithUserMonitoredBy);

    }

    private void responseWithUserMonitoredBy(List<User> users) {
        monitoredByList = users;
        nameAndEmail = new String [monitoredByList.size()];

        for (int i = 0; i < monitoredByList.size();i++) {
            nameAndEmail[i] ="        " + monitoredByList.get(i).getName() + "  :  " + monitoredByList.get(i).getEmail();
        }

        populateListView();

    }


    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.see_monitored_by,
                nameAndEmail);

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
                    populateListView();
                }
        }
    }



}
