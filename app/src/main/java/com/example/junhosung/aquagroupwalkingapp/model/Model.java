package com.example.junhosung.aquagroupwalkingapp.model;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;

import java.util.List;

/**
 * Created by karti on 2018-03-04.
 */

public class Model extends AppCompatActivity {
    //internal class variables
    final private String TAG ="Model Class";
    private static Model modelInstance;

    //groups or collections
    public UserCollection usersOld;
    public UserCollectionServer users;

    //information about current user
    private User currentUser;
    private boolean isUserLoggedin;
    private String tokenForLogin;
    private List<User> currentGroupInUseByUser; //for david -modify btn activity



    //callback variables
    private SimpleCallback callback;
    private SimpleCallback callbackForUserId;
    private SimpleCallback callbackForVoid;
    private SimpleCallback callbackForGetUserByEmail;
    private SimpleCallback callbackForGetMonitoredById;
    private SimpleCallback callbackForGetMonitorsById;


    //for internal model class
    private Model() {
        usersOld = UserCollection.getInstance();
        users = UserCollectionServer.getInstance();
    }
    public static Model getInstance() {
        if (modelInstance == null) {
            modelInstance = new Model();
        }
        return modelInstance;
    }




    //methods for request from activities NOT related to the server
    public List<User> getUsers() {
        List<User> returnValue = UserCollectionServer.getInstance().returnUsers();
        if (returnValue == null) {
            Log.i(TAG, "returnValue is null");
        } else {
            Log.i(TAG, "returnValue is not null");
        }
        return returnValue;
    }
    public User getCurrentUser() {
        return currentUser;
    }





    //response methods from server
    private void responseLogin(String token) {
        isUserLoggedin = true;
        this.callbackForVoid.callback(null);
        this.tokenForLogin = token;
        //get the collection of user right away
        listUsers();
    }
    private void responseUserList(List<User> userList){
        for(User user: userList){
            UserCollectionServer.getInstance().addUser(user);
        }
    }
    private void responseGetMonitorsById(List<User> userList) {
        callbackForGetMonitorsById.callback(userList);
    }
    private void createNewUserResponse(User user) {
        //this is user we just added, do want to anything with user
    }
    private void responseGetUserById(User returnedUser) {
        callbackForUserId.callback(returnedUser);
    }

    private void responseGetUserByEmail(User user){
        callbackForGetUserByEmail.callback(user);
    }
    private void responseGetMonitoredById(List<User> userList) {
        callbackForGetMonitoredById.callback(userList);
    }



    //calls to server methods
    // Adding in currentUser

    public void logIn(String loginEmail, String password, SimpleCallback<Void> callback) {
        isUserLoggedin = false;
        this.callbackForVoid = callback;
        Server server = new Server();
        currentUser = new User();
        currentUser.setEmail(loginEmail);
        currentUser.setPassword(password);
        server.loginUser(currentUser,this::responseLogin);

    }
    public void listUsers() {
        Server server = new Server();
        if(isUserLoggedin){
            server.getListOfUsers(this.tokenForLogin,this::responseUserList);
        }
    }
    public void createUser(String nameNew, String emailNew, String passwordNew) {
        User user = new User();
        user.setName(nameNew);
        user.setEmail(emailNew);
        user.setPassword(passwordNew);
        Server server = new Server();
        server.createNewUser(user,  this::createNewUserResponse);
    }
    public void getUserById(Long userId ,SimpleCallback<User> callback) {
        Server server = new Server();
        callbackForUserId = callback;
        if(isUserLoggedin) {
            server.getUserById(userId,this.tokenForLogin,this::responseGetUserById);
        }
    }
    public void getUserByEmail(String email, SimpleCallback<User> responseWithUserEmail) {
        Server server = new Server();
        callbackForGetUserByEmail = responseWithUserEmail;
        if(isUserLoggedin){
            server.getUserByEmail(email,this.tokenForLogin,this::responseGetUserByEmail);
        }
    }

     public void getMonitorsById(Long userId,SimpleCallback<List<User>> callback) {
        this.callbackForGetMonitorsById = callback;
        Server server = new Server();
        if(isUserLoggedin) {
            server.getMonitorsUsers(userId, this.tokenForLogin, this::responseGetMonitorsById);
        }
    }
    public void getMonitoredById(Long userId,SimpleCallback<List<User>> callback) {
        this.callbackForGetMonitoredById = callback;
        Server server = new Server();
        if(isUserLoggedin) {
            server.getMonitoredByUsers(userId,this.tokenForLogin,this::responseGetMonitoredById);
        }
    }

}
