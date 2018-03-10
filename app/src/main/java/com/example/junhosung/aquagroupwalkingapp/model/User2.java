package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David-Yoga on 2018-03-02.
 * User2 class that holds the email and password attributes. Will be further updated as the project proceeds
 *
 * Will be inserting other attributes
 *
 */

public class User2 {
    String username;
    private String password;

    public User2(String email, String pw) {
        username = email;
        password = pw;
    }

    // This list holds the name and email of people whom the user is monitoring

    public List<User2> mMonitorsUser2s = new ArrayList<>();

    // This list holds the name and email of peop

    public List<User2> mMonitoredByUser2s = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // This method adds a new user to the mMonitorsUser2s List

    public void addNewMonitorsUsers(User2 user2) {

        mMonitorsUser2s.add(user2);
    }

    // This method adds to new user to the mMonitoredByUser2s List

    public void addNewMonitoredByUsers(User2 user2) {

        mMonitoredByUser2s.add(user2);

    }

    public int countMonitoring() {return mMonitorsUser2s.size();}

    public int countMonitoredBy() {return mMonitoredByUser2s.size();}

    public User2 getMonitoree(int index) {
        validateIndexWithException1(index);
        return mMonitorsUser2s.get(index);

    }

    public User2 getMonitoredBy(int index) {
        validateIndexWithException2(index);
        return mMonitoredByUser2s.get(index);

    }

    // retrieves the userName (currently the email address - will have to display both actual name and email in the future
    // similar to the method used in Pot Collection from the second assignment
    // useful for integrating with an ArrayAdapter - Brian

    public String[] getMonitorsUser2s() {
        String[] monitorsUsers = new String[countMonitoring()];
        for (int i = 0; i < countMonitoring(); i++) {
            User2 monitoree = getMonitoree(i);
            monitorsUsers[i] = "real name comes here ... " + monitoree.getUsername();

        }

        return monitorsUsers;
    }

    // same thing here but with the other list

    public String[] getMonitoredByUser2s() {
        String[] monitoredByUsers = new String[countMonitoredBy()];
        for (int i = 0; i < countMonitoredBy(); i++) {
            User2 monitored = getMonitoredBy(i);
            monitoredByUsers[i] = "real name comes here ... " + monitored.getUsername();

        }

        return monitoredByUsers;
    }


    public boolean checkPassword(String userpw) {
        if (userpw == password) {
            return true;
        } else {
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

    // a method to check for out of index search for getMonitoree method - seen in Assignment2

    private void validateIndexWithException1(int index) {
        if (index < 0 || index >= countMonitoring()) {
            throw new IllegalArgumentException();
        }
    }

    // same thing but for getMonitoredBy method

    private void validateIndexWithException2(int index) {
        if (index < 0 || index >= countMonitoredBy()) {
            throw new IllegalArgumentException();
        }
    }
}


