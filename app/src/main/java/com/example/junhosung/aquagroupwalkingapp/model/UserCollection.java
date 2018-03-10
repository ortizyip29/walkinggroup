package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by David-Yoga on 2018-03-02.
 * User2 Collection class which is similar to Assignment 3 potcollection class
 */

public class UserCollection {

    private  List<User2> mUser2s = new ArrayList<>();



    public void addUser(User2 user2){
        mUser2s.add(user2);
    }

    public int countUsers() {
        return mUser2s.size();
    }

    //maybe this should be renamed getUser ...

    public User2 getEmail(int index) {
        return mUser2s.get(index);

    }


}
