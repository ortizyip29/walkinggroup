package com.example.junhosung.aquagroupwalkingapp;

/**
 * Created by David-Yoga on 2018-03-02.
 * User class that holds the email and password attributes. Will be further updated as the project proceeds
 */

public class User {
    String username;
    private String password;

    public User(String email, String pw) {
        username = email;
        password = pw;
    }


    public String getUsername() {
        return username;
}
    public String getPassword() {
    return password;
    }


    public boolean checkPassword(String userpw){
        if(userpw == password){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUsername(String userin) {
        if (userin == username) {
            return true;
        } else {
            return false;
        }
    }
}
