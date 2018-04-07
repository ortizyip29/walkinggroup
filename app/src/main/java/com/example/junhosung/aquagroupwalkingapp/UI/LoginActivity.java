package com.example.junhosung.aquagroupwalkingapp.UI;
/** Login Activity Java Code
 * First page loginEmail sees upon opening the app
 *
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;


import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.SharedPreferenceLoginState;


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
        doNotShowProgress();

            setupRegisterBtn();
            setupLoginbtn();

        if (SharedPreferenceLoginState.getEmail(LoginActivity.this).length() != 0) {
            showProgress();
            loginEmail = SharedPreferenceLoginState.getEmail(LoginActivity.this);
            password = SharedPreferenceLoginState.getPassword(LoginActivity.this);
            model.logIn(loginEmail, password, returnNothing -> responseForLogin(returnNothing));

        }



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
                doNotShowProgress();
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

    private void doNotShowProgress() {
        ProgressBar loginProgressBar =(ProgressBar) findViewById(R.id.progressBarForLoginActivity);
        loginProgressBar.setVisibility(View.GONE);
    }
    private void showProgress(){
        ProgressBar loginProgressBar =(ProgressBar) findViewById(R.id.progressBarForLoginActivity);
        loginProgressBar.setVisibility(View.VISIBLE);
    }

    //Checking if user input is an email
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void responseForLogin(Void returnNothing) {
        doNotShowProgress();
        Toast.makeText(LoginActivity.this, "Server Login successful", Toast.LENGTH_SHORT).show();
        SharedPreferenceLoginState.setEmail(LoginActivity.this, loginEmail, password);
        Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
        startActivity(intent);
    }

    private void setupLoginbtn() {
        Button btn = (Button) findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, loginEmail, Toast.LENGTH_LONG);
                showProgress();
                Log.i("A", loginEmail);
                Log.i("A", password);

                model.logIn(loginEmail, password, returnNothing-> responseForLogin(returnNothing));
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

