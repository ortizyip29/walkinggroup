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
import android.graphics.Color;
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

import java.util.ArrayList;
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

    private List<Clicked> isItemClicked = new ArrayList<>();
    public class Clicked{
        public boolean clicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitoring);

        model.getMonitorsById(model.getCurrentUser().getId(),this::responseWithUserMonitors);
        setUpAddButton();
        setUpDeleteButton();

    }

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
                int counter = 0;
                for(Clicked thisItem: isItemClicked){
                    if(thisItem.clicked){
                        model.stopMonitoring(model.getCurrentUser().getId(),monitorsList.get(counter).getId(),this::voidCallback);
                    }
                    counter++;
                }

            }

            private void voidCallback(Void aVoid) {
                model.getMonitorsById(model.getCurrentUser().getId(),this::responseWithUserMonitorsDeleteButton);
            }

            private void responseWithUserMonitorsDeleteButton(List<User> users) {
                monitorsList = users;
                nameAndEmail = new String[monitorsList.size()];
                for (int i = 0; i < monitorsList.size();i++) {
                    nameAndEmail[i] = "      " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
                }

                populateListView();

            }

        });
    }

    private void responseWithUserMonitors(List<User> users) {
        monitorsList = users;
        nameAndEmail = new String [monitorsList.size()];

        for (int i = 0; i < monitorsList.size();i++) {
            nameAndEmail[i] ="        " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
            isItemClicked.add(new Clicked());
        }

        populateListView();

    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                nameAndEmail);

        ListView list = (ListView) findViewById(R.id.monitorList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                if(!isItemClicked.get(itemNumber).clicked){
                    view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;
                    isItemClicked.set(itemNumber,click); //mark for deletion
                } else{
                    view.setBackgroundColor(Color.WHITE); //change back to normal view
                    Clicked click = new Clicked();
                    click.clicked = false;
                    isItemClicked.set(itemNumber,click);//unmark for deletion
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    model.getMonitorsById(model.getCurrentUser().getId(), this::responseWithUserMonitorsOnActivityResult);
                }

        }

    }

    private void responseWithUserMonitorsOnActivityResult(List<User> users) {
        monitorsList = users;
        nameAndEmail = new String[monitorsList.size()];
        for (int i = 0; i < monitorsList.size();i++) {
            nameAndEmail[i] = "      " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
            isItemClicked.add(new Clicked());
        }

        Toast.makeText(SeeMonitoringActivity.this,"success!",Toast.LENGTH_LONG).show();

        populateListView();

    }


}
