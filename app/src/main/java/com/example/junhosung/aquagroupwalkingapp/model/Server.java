package com.example.junhosung.aquagroupwalkingapp.model;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;
import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.nio.file.Path;
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
    private SimpleCallback serverCallbackForAddNewMonitors;
    private SimpleCallback serverCallbackForAddNewMonitoredBy;
    private SimpleCallback serverCallbackForStopMonitoring;
    private SimpleCallback serverCallbackForCreateNewGroup;
    private SimpleCallback serverCallbackForGetGroups;
    private SimpleCallback serverCallbackForGetGroupDetailsById;
    private SimpleCallback serverCallbackForUpdateGroupDetails;
    private SimpleCallback serverCallbackForStopMonitoredBy;
    private SimpleCallback serverCallbackForDeleteMemberOfGroup;
    private SimpleCallback serverCallbackForAddNewUserToGroup;
    private SimpleCallback serverCallbackForGetMembersOfGroup;
    private SimpleCallback serverCallbackForGetUserUnreadMessages;
    private SimpleCallback serverCallbackForGetUserReadMessages;
    private SimpleCallback serverCallbackForNewMsgtoGroup;
    private SimpleCallback serverCallbackForNewMsgToParents;


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

    private void responseAddNewMonitors(List<User> users) {
        serverCallbackForAddNewMonitors.callback(users);
    }

    private void responseAddNewMonitoredBy(List<User> users) {
        serverCallbackForAddNewMonitoredBy.callback(users);
    }

    private void responseStopMonitoring(Void returnedNothing) {
        serverCallbackForStopMonitoring.callback(returnedNothing);
    }
    private void responseCreateNewGroup(Group group) {
        serverCallbackForCreateNewGroup.callback(group);
    }

    private void responseForGetGroups(List<Group> groups) {
        serverCallbackForGetGroups.callback(groups);
    }

    private void responseForGetGroupDetailsById(Group group) {
        serverCallbackForGetGroupDetailsById.callback(group);
    }

    private void responseForUpdateGroupDetails(Group group) {
        serverCallbackForUpdateGroupDetails.callback(group);
    }

    private void responseForStopMonitoredBy(Void returnedNothing) {
        serverCallbackForStopMonitoredBy.callback(returnedNothing);
    }
    private void responseForAddNewUserToGroup(List<User> users) {
        serverCallbackForAddNewUserToGroup.callback(users);
    }

    private void responseForDeleteMemberOfGroup(Void returnedNothing) {
        serverCallbackForDeleteMemberOfGroup.callback(returnedNothing);
    }

    private void responseForGetMembersOfGroup(List<User> users) {
        serverCallbackForGetMembersOfGroup.callback(users);
    }

    private void responseForGetUserUnreadMessages(List<Message> messages) {
        serverCallbackForGetUserUnreadMessages.callback(messages);
    }

    private void responseForGetUserReadMessages(List<Message> messages) {
        serverCallbackForGetUserReadMessages.callback(messages);
    }

    private void responseForNewMsgToGroup(Message msg) {
        serverCallbackForNewMsgtoGroup.callback(msg);
    }

    private void responseForNewMsgToParents(Message msg) {
        serverCallbackForNewMsgtoGroup.callback(msg);
    }







    //call function
    public void loginUser(User user, SimpleCallback<String> callback){
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

    public void addNewMonitors(Long userId, User targetUser, String token, SimpleCallback<List<User>> callback) {
        onReceiveToken(token);
        serverCallbackForAddNewMonitors = callback;
        Call<List<User>> caller = proxy.addNewMonitors(userId,targetUser);
        ProxyBuilder.callProxy(Server.this,caller,this::responseAddNewMonitors);

    }

    public void addNewMonitoredBy(Long userId,User targetUser, String token, SimpleCallback<List<User>> callback) {
        onReceiveToken(token);
        serverCallbackForAddNewMonitoredBy = callback;
        Call<List<User>> caller = proxy.addNewMonitoredBy(userId, targetUser);
        ProxyBuilder.callProxy(Server.this,caller,this::responseAddNewMonitoredBy);

    }

    public void stopMonitoring(Long userId, Long targetId, String token, SimpleCallback<Void> callback) {
        onReceiveToken(token);
        serverCallbackForStopMonitoring = callback;
        Call<Void> caller = proxy.stopMonitors(userId,targetId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseStopMonitoring);
    }

    public void stopMonitoredBy(Long userId, Long targetId, String token, SimpleCallback<Void> callback) {
        onReceiveToken(token);
        serverCallbackForStopMonitoredBy = callback;
        Call<Void> caller = proxy.stopMonitors(userId,targetId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseForStopMonitoredBy);
    }

    public void createNewGroup(Group group,String token,SimpleCallback<Group> callback){
        onReceiveToken(token);
        serverCallbackForCreateNewGroup = callback;
        Call<Group> caller = proxy.createGroup(group);
        ProxyBuilder.callProxy(Server.this,caller,this::responseCreateNewGroup);

    }
    public void getGroups(String token,SimpleCallback<List<Group>> callback){
        onReceiveToken(token);
        serverCallbackForGetGroups = callback;
        Call<List<Group>> caller = proxy.getGroups();
        ProxyBuilder.callProxy(Server.this,caller,this::responseForGetGroups);
    }

    public void getGroupDetailsById(Long groupId,String token, SimpleCallback<Group> callback) {
        onReceiveToken(token);
        serverCallbackForGetGroupDetailsById = callback;
        Call<Group> caller = proxy.getGroupDetails(groupId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseForGetGroupDetailsById);
    }

    public void updateGroupDetails(Long groupId, Group updatedGroup, String token, SimpleCallback<Group> callback) {
        onReceiveToken(token);
        serverCallbackForUpdateGroupDetails = callback;
        Call<Group> caller = proxy.updateGroupDetails(groupId,updatedGroup);
        ProxyBuilder.callProxy(Server.this, caller, this::responseForUpdateGroupDetails);
    }
    public void addNewUser(Long groupId, User user, String token, SimpleCallback<List<User>> callback) {
        onReceiveToken(token);
        serverCallbackForAddNewUserToGroup = callback;
        Call<List<User>> caller = proxy.addNewMemberToGroup(groupId,user);
        ProxyBuilder.callProxy(Server.this, caller, this::responseForAddNewUserToGroup);
    }

    public void deleteMemberOfGroup(Long groupId, Long userId, String token, SimpleCallback<Void> callback) {
        onReceiveToken(token);
        serverCallbackForDeleteMemberOfGroup = callback;
        Call<Void> caller = proxy.deleteMemberOfGroup(groupId, userId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseForDeleteMemberOfGroup);

    }

    public void getMembersOfGroup(Long groupId,String token,SimpleCallback<List<User>> callback) {
        onReceiveToken(token);
        serverCallbackForGetMembersOfGroup = callback;
        Call<List<User>> caller = proxy.getGroupMembers(groupId);
        ProxyBuilder.callProxy(Server.this,caller,this::responseForGetMembersOfGroup);
    }

    public void getUserUnreadMessages(Long userId, String readUnread,String token, SimpleCallback<List<Message>> callback) {
        onReceiveToken(token);
        readUnread = "unread";
        serverCallbackForGetUserUnreadMessages = callback;
        Call<List<Message>> caller = proxy.getUserUnreadMessages(userId,readUnread);
        ProxyBuilder.callProxy(Server.this,caller,this::responseForGetUserUnreadMessages);
    }

    public void getUserReadMessages(Long userId, String readUnread, String token, SimpleCallback<List<Message>> callback) {
        onReceiveToken(token);
        readUnread = "read";
        serverCallbackForGetUserReadMessages = callback;
        Call<List<Message>> caller = proxy.getUserReadMessages(userId,readUnread);
        ProxyBuilder.callProxy(Server.this,caller,this::responseForGetUserReadMessages);
    }

    public void newMsgToGroup(Long groupId, Message msg, String token, SimpleCallback<Message> callback) {
        onReceiveToken(token);
        serverCallbackForNewMsgtoGroup = callback;
        Call<Message> caller = proxy.newMsgToGroup(groupId,msg);
        ProxyBuilder.callProxy(Server.this, caller, this::responseForNewMsgToGroup);
    }

    // sends msg to 'parents' of the User with the input userId

    public void newMsgToParents(Long userId, Message msg, String token, SimpleCallback<Message> callback) {
        onReceiveToken(token);
        serverCallbackForNewMsgtoGroup = callback;
        Call<Message> caller = proxy.sendMsgeToParents(userId,msg);
        ProxyBuilder.callProxy(Server.this, caller, this::responseForNewMsgToParents);
    }

    


}
