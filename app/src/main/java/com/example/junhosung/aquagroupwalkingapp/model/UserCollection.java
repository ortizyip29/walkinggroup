package com.example.junhosung.aquagroupwalkingapp.model;

import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by David-Yoga on 2018-03-02.
 * User Collection class which is similar to Assignment 3 potcollection class
 */

public class UserCollection {

    private  List<User> Users = new ArrayList<>();



    public void addUser(User user){
        Users.add(user);
    }

    public int countUsers() {
        return Users.size();
    }

    //maybe this should be renamed getUser ...

    public  User getEmail(int index) {
        return Users.get(index);

    }


}
