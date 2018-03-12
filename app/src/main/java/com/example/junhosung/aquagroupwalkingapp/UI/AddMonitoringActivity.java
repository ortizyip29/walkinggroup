package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User2;
import com.example.junhosung.aquagroupwalkingapp.model.UserCollection;

public class AddMonitoringActivity extends AppCompatActivity {

    private Button btnAddMonitoring;
    private Model model = Model.getInstance();
    UserCollection users = model.users;
    User2 mUser2 = model.users.getEmail(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitoree);


        //test cases ...

        User2 randUser21 = new User2("harro5@gmail.com","2");
        User2 randUser22 = new User2("harro6@gmail.com","2");

        // adding users to the UserCollection of the model so that we can check that they exist

        users.addUser(randUser21);
        users.addUser(randUser22);

        // this is here since the for loop inside the onClickListenr gives me trouble about
        // calling users.countUsers() from an inner class ...

        final int counter = users.countUsers();


        btnAddMonitoring = (Button) findViewById(R.id.btnAddMonitoring);
        btnAddMonitoring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Extract data from UI

                EditText newUser = (EditText) findViewById(R.id.typeEmail);
                String email = newUser.getText().toString();
                //Toast.makeText(AddMonitoringActivity.this,email,Toast.LENGTH_SHORT);

                for (int i = 0; i< counter; i++) {

                    // if the username (= email) is in the User2 Collection (later to be replace by model)

                    if (users.getEmail(i).getUsername().equals(email)) {
                        mUser2.addNewMonitorsUsers(users.getEmail(i));

                    }

                    //else {
                        //Toast.makeText(AddMonitoringActivity.this,"mUser2 not found!",Toast.LENGTH_LONG).show();

                    //}

                }

                // Going back to SeeMonitoringActivity


                Log.i("email",email);
                Log.i("0",users.getEmail(0).getUsername());
                Log.i("1",users.getEmail(1).getUsername());
                Log.i("2",users.getEmail(2).getUsername());


                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);

                finish();

            }
        });


    }



}
