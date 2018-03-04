package com.example.junhosung.aquagroupwalkingapp;
/** Login Activity Java Code
 * First page user sees upon opening the app
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.junhosung.aquagroupwalkingapp.UserCollection.countUsers;

public class LoginActivity extends AppCompatActivity {
    private UserCollection users = new UserCollection();        // Instantiates usercollection
    public static final int REQUEST_CODE_NEWUSER = 1234;
    EditText usertemp;          //EditText variable that holds user input for Email
    EditText passwordtemp;      //EditText variable that holds user input for Password
    String user;                //holds the string version of the user inputted email
    String password;            //holds the string version of the user inputted password


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usertemp = (EditText) findViewById(R.id.textEmail);
        passwordtemp = (EditText) findViewById(R.id.textPassword);
        password = passwordtemp.getText().toString();
        setupRegisterBtn();
        setupLoginbtn();

        /**
         * The following textwatchers update as user types email/password.
         * And updates the variables user and password that store this information
         */
        usertemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try{
                    user = usertemp.getText().toString();
                }catch(NumberFormatException a){
                    return;
                }
            }
        });

        passwordtemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    password = passwordtemp.getText().toString();

                } catch (NumberFormatException a) {
                    return;
                }
            }
        });
    }


    //Login button set up
    //loops through UserCollection activity to compare email and passwords to confirm login
    private void setupLoginbtn() {
        Button btn = (Button) findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, user, Toast.LENGTH_LONG);
                Log.i("A", user);
                Log.i("A", password);
                for (int i = 0; i<countUsers(); i++) {
                    Log.i("lel", UserCollection.getEmail(i).getUsername());
                    Log.i("lel", UserCollection.getEmail(i).getPassword());
                    if (UserCollection.getEmail(i).getPassword().equals(password)){

                        Toast.makeText(LoginActivity.this, "Login successful",Toast.LENGTH_SHORT).show();
                    }
                //    Toast.makeText(LoginActivity.this, "We made it here", Toast.LENGTH_LONG).show();
                    }
            }
        });
    }


    //Wiring up Register Button
    //Starts new activity for register
    private void setupRegisterBtn() {
        Button btn = (Button) findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.makeIntent(LoginActivity.this);
                startActivityForResult(intent, REQUEST_CODE_NEWUSER);
            }
        });
        }

    //Retrieves the information from Register Activity
    //Stores the information in the UserCollection class
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_NEWUSER:
                if (resultCode == Activity.RESULT_OK) {
                    //get message
                    String userEmail = data.getStringExtra("newUserEmail");
                    String userPassword = data.getStringExtra("newUserPassword");
                    //store new user details in userCollection

                    UserCollection.addUser(new User(userEmail, userPassword));
                } else {
                    Log.i("Login page", "Activity cancelled");
                }


        }
    }


}

