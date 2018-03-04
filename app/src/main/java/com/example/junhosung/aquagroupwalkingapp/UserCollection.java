package com.example.junhosung.aquagroupwalkingapp;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by David-Yoga on 2018-03-02.
 * User Collection class which is similar to Assignment 3 potcollection class
 */

public class UserCollection {

    private static List<User> Users = new ArrayList<>();

    public static void addUser(User user){
        Users.add(user);
    }

    public static int countUsers() {
        return Users.size();
    }

    public static User getEmail(int index) {
        return Users.get(index);

    }

}
