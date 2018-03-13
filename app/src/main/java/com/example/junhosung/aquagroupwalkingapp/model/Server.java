package com.example.junhosung.aquagroupwalkingapp.model;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;
import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Junho Sung on 3/9/2018.
 */
//TO DO: list of monitored by and monitoring
public class Server extends AppCompatActivity {
    private String token;
    //internal variable
    private static final String TAG = "Server Class";
    private WGServerProxy proxy;

    //Server Class Internal Functions
    public Server(){
        proxy = ProxyBuilder.getProxy("D43B2DCD-D2A8-49EF-AFCC-6B1E309D1B58", null);
    }
    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w(TAG, "   --> NOW HAVE TOKEN: " + token);
        this.token = token;
        proxy = ProxyBuilder.getProxy("D43B2DCD-D2A8-49EF-AFCC-6B1E309D1B58", token);
        //proxy = ProxyBuilder.getProxy(getString(R.string.apikey), token);
    }
    //


    //callbackVariables;
    private SimpleCallback serverCallback;
    private SimpleCallback serverCallbackForLogin;
    private SimpleCallback serverCallbackForCreateNewUser;
    private SimpleCallback serverCallbackForGetListOfUsers;
    private SimpleCallback serverCallbackForGetMonitorsUser;
    private SimpleCallback serverCallbackForGetMonitoredByUsers;
    private SimpleCallback serverCallbackForUserById;
    private SimpleCallback serverCallbackForGetUserByEmail;




    // response functions
    private void loginResponse(Void returnedNothing) {
        Log.w(TAG, "Server replied for user login: " );
        serverCallbackForLogin.callback(this.token);
    }
    private void createNewUserResponse(User user) {
        Log.w(TAG, "Server replied with user: " + user.toString());
        serverCallbackForCreateNewUser.callback(user);
    }
    private void responseGetListOfUsers(List<User> returnedUsers) {
        for (User user : returnedUsers) {
            Log.w(TAG, "    User: " + user.toString());
        }
        serverCallbackForGetListOfUsers.callback(returnedUsers);
    }
    private void responseGetMonitorsUsers(List<User> returnedUsers) {
        for (User user : returnedUsers) {
            Log.w(TAG, "    User: " + user.toString());
        }
        serverCallbackForGetMonitorsUser.callback(returnedUsers);
    }
    private void responseGetMonitoredByUsers(List<User> returnedUsers) {
        for (User user : returnedUsers) {
            Log.w(TAG, "   User: " +user.toString());
        }
        serverCallbackForGetMonitoredByUsers.callback(returnedUsers);
    }
    private void responseGetUserById(User returnedUser) {
        serverCallbackForUserById.callback(returnedUser);
    }
    private void responseGetUserByEmail(User user) {
        serverCallbackForGetUserByEmail.callback(user);
    }







    //call function
    public  void loginUser(User user, SimpleCallback<String> callback){
        this.serverCallbackForLogin = callback;
        ProxyBuilder.setOnTokenReceiveCallback( token -> onReceiveToken(token));
        // Make call
        Call<Void> caller = proxy.login(user);
        ProxyBuilder.callProxy(Server.this, caller, this::loginResponse);
    }
    public void createNewUser(User user, final SimpleCallback<User> callback){
        serverCallbackForCreateNewUser = callback;
        Call<User> caller = proxy.createNewUser(user);
        ProxyBuilder.callProxy(Server.this, caller,this::createNewUserResponse);
    }
    public void getListOfUsers(String token, SimpleCallback<List<User>> callback){
        onReceiveToken(token);
        this.serverCallbackForGetListOfUsers = callback;
        Call<List<User>> caller = proxy.getUsers();
        ProxyBuilder.callProxy(Server.this, caller, this::responseGetListOfUsers);
    }
    public void getMonitorsUsers(Long userId, String token, SimpleCallback<List<User>> callback) {
        onReceiveToken(token);
        this.serverCallbackForGetMonitorsUser = callback;
        Call<List<User>> caller = proxy.getMonitorsById(userId);
        ProxyBuilder.callProxy(Server.this,caller, this::responseGetMonitorsUsers);
    }
    public void getMonitoredByUsers(Long userId, String token, SimpleCallback<List<User>> callback) {
        onReceiveToken(token);
        this.serverCallbackForGetMonitoredByUsers = callback;
        Call<List<User>> caller = proxy.getMonitoredByById(userId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseGetMonitoredByUsers);
    }
    public void getUserById(Long userId, String token, SimpleCallback<User> callback)
    {
        onReceiveToken(token);
        serverCallbackForUserById = callback;
        Call<User> caller = proxy.getUserById(userId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseGetUserById);
    }
    public void getUserByEmail(String email, String token, SimpleCallback<User> callback) {
        onReceiveToken(token);
        serverCallbackForGetUserByEmail = callback;
        Call<User> caller = proxy.getUserByEmail(email);
        ProxyBuilder.callProxy(Server.this,caller,this::responseGetUserByEmail);
    }

}
