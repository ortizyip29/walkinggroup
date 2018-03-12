package com.example.junhosung.aquagroupwalkingapp.model;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by karti on 2018-03-10.
 */

public class UserCollection2 {
    private final String TAG = "UserCollection2";
    private UserCollection2() {

    }
    private static UserCollection2 instance;
    public static UserCollection2 getInstance(){
        if(instance == null){
            instance = new UserCollection2();
        }
        return instance;
    }

    private List<User> mUsers = new ArrayList<>();

/*    public List<String> getUsersList(){
        List<String> usersList= new ArrayList<>();
        for(User user:mUsers){
            usersList.add(user.getName() + " , " + user.getEmail());
        }
        return usersList;
    }*/

    public void addUser(User user){
        mUsers.add(user);
    }

    public int countUsers() {
        return mUsers.size();
    }

    //maybe this should be renamed getUser ...

    public User getEmail(int index) {
        return mUsers.get(index);

    }
    public List<User> returnUsers(){
        List<User> newList = new ArrayList<>(mUsers);
        if(mUsers == null){
            Log.i(TAG,"List is null");
        } else{
            Log.i(TAG,"List is not null");
        }
        if(newList == null){
            Log.i(TAG,"newList is null");
        } else{
            Log.i(TAG,"newList is not null");
        }
        return newList;
    }


}
