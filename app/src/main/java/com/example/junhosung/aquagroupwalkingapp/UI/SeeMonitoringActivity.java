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



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

public class SeeMonitoringActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_monitoring);


        user = model.users.getEmail(0);

        User user2 = new User("harro@gmail.com","1");
        User user3 = new User("harro1@gmail.com","2");

        user.addNewMonitorsUsers(user2);
        user.addNewMonitorsUsers(user3);

        populateListView();




    }

    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                user.getMonitorsUsers());

        ListView list = (ListView) findViewById(R.id.monitorList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do something here
            }
        });

    }


}
