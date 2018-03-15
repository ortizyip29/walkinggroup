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
import android.graphics.Color;
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
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.User2;

import java.util.ArrayList;
import java.util.List;

public class SeeMonitoredByActivity extends AppCompatActivity {
    private final String TAG = "SeeMonitoredByActivity";
    private Model model = Model.getInstance();
    Button btnAddMonitoredBy;

    List<User> monitoredByList;
    String[] nameAndEmail;

    private List<Clicked> isItemClicked = new ArrayList<>();
    public class Clicked{
        public boolean clicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitored_by);
        List<Boolean> isItemClicked = new ArrayList<>();
        setUpAddButton();
        setupDeleteBtn();
        updateListOfMonitoring();

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

    private void setupDeleteBtn() {
        Button button = (Button) findViewById(R.id.btnEndMonitoredBy);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int counter = 0;
                for(Clicked thisItem: isItemClicked){
                    if(thisItem.clicked){
                        model.stopMonitoring(monitoredByList.get(counter).getId(),model.getCurrentUser().getId(),this::voidCallback);
                    }
                    counter++;
                }
            }
            private void voidCallback(Void aVoid) {
                updateListOfMonitoring();
            }
        });
    }

    public void updateListOfMonitoring(){
        model.getMonitoredById(model.getCurrentUser().getId(), this::responseupdateListOfMonitoring);
    }
    private void responseupdateListOfMonitoring(List<User> users) {
        updateDisplayListAndDeleteList(users);
        populateListView();
    }
    public void updateDisplayListAndDeleteList(List<User> users){
        monitoredByList = users;
        isItemClicked = new ArrayList<>();
        nameAndEmail = new String [monitoredByList.size()];
        for (int i = 0; i < monitoredByList.size();i++) {
            nameAndEmail[i] ="        " + monitoredByList.get(i).getName() + "  :  " + monitoredByList.get(i).getEmail();
            isItemClicked.add(new Clicked());
        }
    }
    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.see_monitored_by,
                nameAndEmail);

        ListView list = (ListView) findViewById(R.id.monitoredByList);
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
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    CountDownTimer timer = new CountDownTimer(3000, 1) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            updateListOfMonitoring();
                        }
                    };
                    timer.start();




                    updateListOfMonitoring();
                }
        }
    }



}
