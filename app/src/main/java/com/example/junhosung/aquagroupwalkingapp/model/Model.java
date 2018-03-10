package com.example.junhosung.aquagroupwalkingapp.model;

import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.R;

/**
 * Created by karti on 2018-03-04.
 */

public class Model {


    // users should be public no?

    public UserCollection users;

    private Model() {
        users = new UserCollection();
            //get from server
    }

    private static Model modelInstance;

    public static Model getInstance() {
        if(modelInstance == null){
            modelInstance = new Model();
        }
        return modelInstance;
    }






    public boolean logIn(String loginEmail, String password) {
        boolean returnFlag = false;
        for (int i = 0; i< users.countUsers(); i++) {
            if (users.getEmail(i).getPassword().equals(password)) {
               // Log.i("Model class: list users", String.valueOf(users.getEmail(i)));
                Log.i("Model class: list users", "working");
                returnFlag = true;
            }
        }
        return returnFlag;
    }
    public boolean addUser(String loginEmail, String password){
        User2 newUser2 = new User2(loginEmail,password);
        users.addUser(newUser2);

        return true; //if successful
    }


}
