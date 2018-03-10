package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.UserCollection;

public class AddMonitoredByActivity extends AppCompatActivity {

    private Button btnAddMonitoring;
    private Model model = Model.getInstance();
    UserCollection users = model.users;
    User user = model.users.getEmail(0);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitored_by);

        //test cases ...

        User randUser3 = new User("harro7@gmail.com","2");
        User randUser4 = new User("harro8@gmail.com","2");

        // adding users to the UserCollection of the model so that we can check that they exist there

        users.addUser(randUser3);
        users.addUser(randUser4);

        // this is here since the for loop inside the onClickListenr gives me trouble about
        // calling users.countUsers() from an inner class ...

        final int counter = users.countUsers();

        Button btnAddMonitoredBy = (Button) findViewById(R.id.btnAddNewMonitredBy);
        btnAddMonitoredBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText newUser = (EditText) findViewById(R.id.typeEmail2);
                String email = newUser.getText().toString();

                for (int i = 0; i< counter; i++) {

                    // if the username (= email) is in the User Collection (later to be replace by model)

                    if (users.getEmail(i).getUsername().equals(email)) {
                        user.addNewMonitoredByUsers(users.getEmail(i));

                    }


                }

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);

                finish();

            }
        });


    }
}