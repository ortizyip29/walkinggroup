package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karti on 2018-03-10.
 */

public class UserCollection2 {
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

}
