package com.example.junhosung.aquagroupwalkingapp.model;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback4;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;

import java.io.ObjectStreamException;

import retrofit2.Call;

/**
 * Created by karti on 2018-03-04.
 */

public class Model extends AppCompatActivity {
    private SimpleCallback4 callback4;
    private boolean isUserLoggedin;
    // users should be public no?

    public UserCollection users;

    private Model() {
        users = UserCollection.getInstance();
        //get from server
    }

    private static Model modelInstance;

    public static Model getInstance() {
        if (modelInstance == null) {
            modelInstance = new Model();
        }
        return modelInstance;
    }
    private void responseLogin(Void returnNothing) {
        isUserLoggedin = true;
        this.callback4.callback(returnNothing);
    }

    public void logIn(String loginEmail, String password, SimpleCallback4 callback4) {
        isUserLoggedin = false;
        this.callback4 = callback4;
        Server server = new Server();
        User user = new User();
        user.setEmail(loginEmail);
        user.setPassword(password);
        server.loginUser(user,returnNothing->responseLogin(returnNothing));
/*        for (int i = 0; i < users.countUsers(); i++) {
            if (users.getEmail(i).getPassword().equals(password)) {
                // Log.i("Model class: list users", String.valueOf(users.getEmail(i)));
                Log.i("Model class: list users", "working");
                returnFlag = true;
            }
        }*/
    }



    public boolean addUser(String loginEmail, String password) {
        User2 newUser2 = new User2(loginEmail, password);
        users.addUser(newUser2);
        return true; //if successful
    }

    public void createUser(String nameNew, String emailNew, String passwordNew) {
        User user = new User();
        user.setName(nameNew);
        user.setEmail(emailNew);
        user.setPassword(passwordNew);
        Server server = new Server();
        server.createNewUser(user,  (User returnedUser) -> createNewUserResponse(returnedUser) );
    }

/*
    private void responseCreateUser(Void returnUser) {
        UserCollection2.getInstance().addUser(returnUser);
}
*/
    private void createNewUserResponse(User user) {
        UserCollection2.getInstance().addUser(user);
    }

/*
    private void responseCreateUser(User returnUser) {
        UserCollection2.getInstance().addUser(returnUser);
    }
*/

}
