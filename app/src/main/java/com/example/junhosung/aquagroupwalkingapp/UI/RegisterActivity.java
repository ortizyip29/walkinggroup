package com.example.junhosung.aquagroupwalkingapp.UI;
/**
 * Register Activity
 * Allows usersOld to enter an Email and Password, and add it to UserCollection
 * TODO: need to implement Checking previously submitted emails for a duplicate register.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

public class RegisterActivity extends AppCompatActivity {

    Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_register);

        setUpCancelbtn();
        setUpRegisterbtn();


    }
    //Checks if String is a email
    //Takes a charSequence which is a String as input argument to verify
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPasswordValid(String password){
        if (password.isEmpty()){
            return false;
        }else if(!password.equals(password.toLowerCase())){
            if(password.matches(".*\\d+.*") && (password.length() >= 8) && (password.length() <= 22)){
                return true;
            }else{
                Toast.makeText(RegisterActivity.this, "Password must contain at least 1 Capital letter and 1 number/symbol and between 8 to 22 characters long", Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            Toast.makeText(RegisterActivity.this, "Password must contain at least 1 Capital letter and 1 number/symbol", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    //Register Button set up
    //Button will take the email and password entered, and return the data from this activity to the LoginActivity page
    //while also ending this activity simultaneously

    private void setUpRegisterbtn() {
        Button btnRegister = (Button) findViewById(R.id.btnAddNewUser);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = (EditText) findViewById(R.id.textNewEmail);
                EditText password = (EditText) findViewById(R.id.textNewPassword);
                EditText name = (EditText) findViewById(R.id.textNewName);
                String emailNew = username.getText().toString();
                String passwordNew = password.getText().toString();
                String nameNew = name.getText().toString();
                if (emailNew.isEmpty()) {
                    Log.i("Walk", "Empty Username");
                    String message = "Please enter an Email";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }else if(isEmailValid(emailNew)!= true){
                    Log.i("Walk", "email not valid");
                    Toast.makeText(RegisterActivity.this, "Email not valid", Toast.LENGTH_LONG).show();
                } else if (isPasswordValid(passwordNew) == true) {
                    try {
                        Intent intent = new Intent();
                        model.createUser(nameNew, emailNew, passwordNew);
                        finish();
                    } catch (NumberFormatException a) {
                        return;
                    }
                }

            }
        });
    }
    //Cancel button
    //Finishes this activity without any data transferred
    private void setUpCancelbtn() {
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
            return new Intent(context, RegisterActivity.class);
    }
}
