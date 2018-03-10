package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David-Yoga on 2018-03-02.
 * User class that holds the email and password attributes. Will be further updated as the project proceeds
 *
 * Will be inserting other attributes
 *
 */

public class User {
    String username;
    private String password;

    public User(String email, String pw) {
        username = email;
        password = pw;
    }

    // This list holds the name and email of people whom the user is monitoring

    public List<User> monitorsUsers = new ArrayList<>();

    // This list holds the name and email of peop

    public List<User> monitoredByUsers = new ArrayList<>();


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // This method adds a new user to the monitorsUsers List

    public void addNewMonitorsUsers(User user) {

        monitorsUsers.add(user);
    }

    // This method adds to new user to the monitoredByUsers List

    public void addNewMonitoredByUsers(User user) {

        monitoredByUsers.add(user);

    }

    public int countMonitoring() {return monitorsUsers.size();}

    public int countMonitoredBy() {return monitoredByUsers.size();}

    public User getMonitoring(int index) {
        validateIndexWithException1(index);
        return monitorsUsers.get(index);

    }

    public User getMonitoredBy(int index) {
        validateIndexWithException2(index);
        return monitoredByUsers.get(index);

    }

    // retrieves the userName (currently the email address - will have to display both actual name and email in the future
    // similar to the method used in Pot Collection from the second assignment
    // useful for integrating with an ArrayAdapter - Brian

    public String[] getMonitorsUsers() {
        String[] monitorsUsers = new String[countMonitoring()];
        for (int i = 0; i < countMonitoring(); i++) {
            User monitoree = getMonitoring(i);
            monitorsUsers[i] = "real name comes here ... " + monitoree.getUsername();

        }

        return monitorsUsers;
    }

    // same thing here but with the other list

    public String[] getMonitoredByUsers() {
        String[] monitoredByUsers = new String[countMonitoredBy()];
        for (int i = 0; i < countMonitoredBy(); i++) {
            User monitored = getMonitoredBy(i);
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

    // a method to check for out of index search for getMonitoring method - seen in Assignment2

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


