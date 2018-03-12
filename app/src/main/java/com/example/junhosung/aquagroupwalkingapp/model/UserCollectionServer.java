package com.example.junhosung.aquagroupwalkingapp.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karti on 2018-03-10.
 */

public class UserCollectionServer {
    private final String TAG = "UserCollectionServer";
    private UserCollectionServer() {

    }
    private static UserCollectionServer instance;
    public static UserCollectionServer getInstance(){
        if(instance == null){
            instance = new UserCollectionServer();
        }
        return instance;
    }

    private List<User> mUsers = new ArrayList<>();

/*    public List<String> getUsersList(){
        List<String> usersList= new ArrayList<>();
        for(User user:mUsers){
            usersList.add(user.getName() + " , " + user.getUser());
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

    public User getUser(int index) {
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
