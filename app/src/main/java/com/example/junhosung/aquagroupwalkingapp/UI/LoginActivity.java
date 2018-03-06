package com.example.junhosung.aquagroupwalkingapp.UI;
/** Login Activity Java Code
 * First page loginEmail sees upon opening the app
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

import com.example.junhosung.aquagroupwalkingapp.R;


import com.example.junhosung.aquagroupwalkingapp.model.Model;



public class LoginActivity extends AppCompatActivity {
    private Model model = Model.getInstance();

    EditText usertemp;          //EditText variable that holds loginEmail input for Email
    EditText passwordtemp;      //EditText variable that holds loginEmail input for Password
    String loginEmail;                //holds the string version of the loginEmail inputted email
    String password;            //holds the string version of the loginEmail inputted password


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
         * The following textwatchers update as loginEmail types email/password.
         * And updates the variables loginEmail and password that store this information
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
                    loginEmail = usertemp.getText().toString();
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
                Toast.makeText(LoginActivity.this, loginEmail, Toast.LENGTH_LONG);
                Log.i("A", loginEmail);
                Log.i("A", password);
                boolean success = model.logIn(loginEmail, password);
                if (success) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
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
                startActivity(intent);
            }
        });
        }



}

