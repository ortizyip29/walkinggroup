package com.example.junhosung.aquagroupwalkingapp.model;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback4;

import java.util.List;

/**
 * Created by karti on 2018-03-04.
 */

public class Model extends AppCompatActivity {
    private SimpleCallback callback;
    private SimpleCallback4 callbackForVoid;
    private boolean isUserLoggedin;
    // usersOld should be public no?
    final private String TAG ="Model Class";
    public UserCollection usersOld;
    public UserCollectionServer users;
    private String tokenForLogin;

    // id of the current logged in user

    private User currentUser;


    private Model() {
        usersOld = UserCollection.getInstance();
        users = UserCollectionServer.getInstance();

        //get from server
    }

    private static Model modelInstance;

    public static Model getInstance() {
        if (modelInstance == null) {
            modelInstance = new Model();
        }
        return modelInstance;
    }

    private void responseLogin(String token) {
        isUserLoggedin = true;
        this.callbackForVoid.callback(null);
        this.tokenForLogin = token;


        listUsers();
    }

    // Adding in currentUser

    public void logIn(String loginEmail, String password, SimpleCallback4 callback4) {
        isUserLoggedin = false;
        this.callbackForVoid = callback4;
        Server server = new Server();
        currentUser = new User();
        currentUser.setEmail(loginEmail);
        currentUser.setPassword(password);
        server.loginUser(currentUser,(String token)->responseLogin(token));

    }

    private void createNewUserResponse(User user) {
        //UserCollectionServer.getInstance().addUser(user);
    }

    public void createUser(String nameNew, String emailNew, String passwordNew) {
        User user = new User();
        user.setName(nameNew);
        user.setEmail(emailNew);
        user.setPassword(passwordNew);
        Server server = new Server();
        server.createNewUser(user,  (User returnedUser) -> createNewUserResponse(returnedUser));
    }

    public void listUsers() {//SimpleCallback callback){
        //this.callback = callback;
        Server server = new Server();
        if(isUserLoggedin){
            server.getListOfUsers(this.tokenForLogin,(List<User> usersList) -> responseUserList(usersList));
        }
    }
    private void responseUserList(List<User> userList){
        for(User user: userList){
            UserCollectionServer.getInstance().addUser(user);
        }
    }

    public void getMonitorsById(Long userId) {
        Server server = new Server();
        if(isUserLoggedin) {
            server.getMonitorsUsers(userId, this.tokenForLogin, (List<User> usersList) -> responseGetMonitorsById(usersList));
        }
    }

    private List<User> responseGetMonitorsById(List<User> userList) {
        return userList;
    }

    private List<User> responseGetMonitoredById(List<User> userList) {
        return userList;
    }

    public void getMonitoredById(Long userId) {
        Server server = new Server();
        if(isUserLoggedin) {
            server.getMonitoredByUsers(userId,this.tokenForLogin,(List<User> usersList) -> responseGetMonitoredById(usersList));
        }
    }

   /* public List<String> getUsersOld(){
        return  UserCollectionServer.getInstance().getUsersList();
    }
*/
    public List<User> getUsersOld() {
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

    private User responseGetUserById(User returnedUser) {
        return returnedUser;
    }

    public void getUserById(Long userId) {
        Server server = new Server();
        if(isUserLoggedin) {
            server.getUserById(userId,this.tokenForLogin,(User returnedUser) -> responseGetUserById(returnedUser));
        }
    }


}
