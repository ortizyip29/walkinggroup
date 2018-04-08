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
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Timer;


public class SeeMonitoringActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private UserCollectionServer users = model.users;
    User currentUser;
    Button btnAddMonitoring;
    Button btnDeleteMonitoring;
    Button btnEdit;
    String currentUserEmail = model.getCurrentUser().getEmail();
    User receivedUser;
    List<User> monitorsList;
    String[] nameAndEmail;
    String TAG;
    private List<Clicked> isItemClicked = new ArrayList<>();
    public class Clicked{
        public boolean clicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_see_monitoring);
        updateListOfMonitoring();
        setUpAddButton();
        setUpDeleteButton();
        setUpEditButton();

    }

    private void updateListOfMonitoring(){
        model.getMonitorsById(model.getCurrentUser().getId(), this::responseWithUserMonitorsOnActivityResult);
    }

    private void responseWithUserMonitorsOnActivityResult(List<User> users) {
        if(users==null){
            Log.v(TAG,"Users is null----------------------------------------------------");
        } else{
            Log.v(TAG,"Users is not null----------------------------------------------");
        }
        for(User user:users){
            Log.i(TAG,user.toString());
        }
        // Toast.makeText(SeeMonitoringActivity.this,"this is runing!",Toast.LENGTH_LONG).show();

        updateDisplayListAndDeleteList(users);
        populateListView();
    }

    private void updateDisplayListAndDeleteList(List<User> users){
        monitorsList = users;
        isItemClicked = new ArrayList<>();
        nameAndEmail = new String[monitorsList.size()];
        for (int i = 0; i < monitorsList.size();i++) {
            nameAndEmail[i] = "      " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
            isItemClicked.add(new Clicked());
        }
    }


    private void setUpAddButton() {
        btnAddMonitoring = (Button) findViewById(R.id.btnAddMonitoree);
        btnAddMonitoring.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
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
        btnDeleteMonitoring.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
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
                updateListOfMonitoring();
            }
        });
    }

    private void setUpEditButton() {
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counter = 0;
                for(Clicked thisItem: isItemClicked){
                    if(thisItem.clicked){
                        Long userId = monitorsList.get(counter).getId();
                        Intent intent = EditChildActivity.makeIntent(SeeMonitoringActivity.this, userId);
                        //Intent intent = new Intent(SeeMonitoringActivity.this, EditChildActivity.class);
                        //intent.putExtra("test",userId);

                        if (userId != null) {
                            startActivity(intent);
                        }
                    }
                    counter++;
                }
            }

        });

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
                    CountDownTimer timer = new CountDownTimer(3000, 1) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            updateListOfMonitoring();
                        }
                    };
                    timer.start();
                }
        }
    }
}
