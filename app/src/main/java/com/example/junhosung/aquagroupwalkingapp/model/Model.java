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
    final private String TAG = "Model Class";
    private static Model modelInstance;

    //groups or collections
    public UserCollection usersOld;
    public UserCollectionServer users;

    //information about current user
    private User currentUser;
    private boolean isUserLoggedin;
    private String tokenForLogin;
    private List<User> currentListOfUsersForGroupInUseByUser; //for david -modify btn activity
    private Group currentGroupInUseByUser;
    private List<Group> usersGroups;


    //callback variables
    private SimpleCallback callback;
    private SimpleCallback callbackForUserId;
    private SimpleCallback callbackForVoid;
    private SimpleCallback callbackForGetUserByEmail;
    private SimpleCallback callbackForGetMonitoredById;
    private SimpleCallback callbackForGetMonitorsById;
    private SimpleCallback callbackForAddNewMonitors;
    private SimpleCallback callbackForAddNewMonitoredBy;
    private SimpleCallback callbackForStopMonitoring;
    private SimpleCallback callbackForCreateNewGroup;
    private SimpleCallback serverCallbackForGetGroups;
    private SimpleCallback callbackForGetGroupDetailsById;
    private SimpleCallback callbackForUpdateGroupDetails;
    private SimpleCallback callbackForStopMonitoredBy;
    private SimpleCallback callbackForDeleteMemberOfGroup;
    private SimpleCallback callbackForAddNewUserToGroup;
    private SimpleCallback callbackForGetMembersOfGroup;


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

    public Group getCurrentGroupInUseByUser() {
        return currentGroupInUseByUser;
    }

    public void setCurrentGroupInUseByUser(Group group) {
        currentGroupInUseByUser = group;
    }

    public void getGroupsOfUserNoCallToServer() {
    }


    //response methods from server
    private void responseLogin(String token) {
        isUserLoggedin = true;
        this.callbackForVoid.callback(null);
        this.tokenForLogin = token;
        //get the collection of user right away
        listUsers();
        getUserByEmail(currentUser.getEmail(), this::callbackTogGetCurrentUser);
    }

    private void callbackTogGetCurrentUser(User user) {
        currentUser = user;
    }

    private void responseUserList(List<User> userList) {
        for (User user : userList) {
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

    private void responseGetUserByEmail(User user) {
        callbackForGetUserByEmail.callback(user);
    }

    private void responseGetMonitoredById(List<User> userList) {
        callbackForGetMonitoredById.callback(userList);
    }

    private void responseAddNewMonitors(List<User> users) {
        callbackForAddNewMonitors.callback(users);
    }

    private void responseAddNewMonitoredBy(List<User> users) {
        callbackForAddNewMonitoredBy.callback(users);
    }

    private void responseStopMonitoring(Void returnedNothing) {
        callbackForStopMonitoring.callback(returnedNothing);
    }

    private void responseForStopMonitoredBy(Void returnedNothing) {
        callbackForStopMonitoredBy.callback(returnedNothing);
    }

    private void responseForCreateNewGroup(Group group) {
        callbackForCreateNewGroup.callback(group);
    }

    private void responseForGetGroups(List<Group> groups) {
        usersGroups = groups;
        if (!groups.isEmpty()) {
            if (currentGroupInUseByUser == null) {
                currentGroupInUseByUser = groups.get(0);
            }
        }
        if (serverCallbackForGetGroups != null) {
            serverCallbackForGetGroups.callback(groups);
        }
    }

    private void responseForGetGroupDetailsById(Group group) {
        callbackForGetGroupDetailsById.callback(group);
    }

    private void responseForUpdateGroupDetails(Group group) {
        callbackForUpdateGroupDetails.callback(group);
    }
    private void responseForAddNewUserToGroup(List<User> users) {
        this.callbackForAddNewUserToGroup.callback(users);
    }

    private void responseForDeleteMemberOfGroup(Void returnedNothing) {
        this.callbackForDeleteMemberOfGroup.callback(returnedNothing);
    }

    private void responseForGetMembersOfGroups(List<User> users) {
        this.callbackForGetMembersOfGroup.callback(users);
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
        server.loginUser(currentUser, this::responseLogin);
    }

    public void listUsers() {
        Server server = new Server();
        if (isUserLoggedin) {
            server.getListOfUsers(this.tokenForLogin, this::responseUserList);
        }
    }

    public void createUser(String nameNew, String emailNew, String passwordNew) {
        User user = new User();
        user.setName(nameNew);
        user.setEmail(emailNew);
        user.setPassword(passwordNew);
        Server server = new Server();
        server.createNewUser(user, this::createNewUserResponse);
    }

    public void getUserById(Long userId, SimpleCallback<User> callback) {
        Server server = new Server();
        callbackForUserId = callback;
        if (isUserLoggedin) {
            server.getUserById(userId, this.tokenForLogin, this::responseGetUserById);
        }
    }

    public void getUserByEmail(String email, SimpleCallback<User> responseWithUserEmail) {
        Server server = new Server();
        callbackForGetUserByEmail = responseWithUserEmail;
        if (isUserLoggedin) {
            server.getUserByEmail(email, this.tokenForLogin, this::responseGetUserByEmail);
        }
    }

    public void getMonitorsById(Long userId, SimpleCallback<List<User>> callback) {
        this.callbackForGetMonitorsById = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.getMonitorsUsers(userId, this.tokenForLogin, this::responseGetMonitorsById);
        }
    }

    public void getMonitoredById(Long userId, SimpleCallback<List<User>> callback) {
        this.callbackForGetMonitoredById = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.getMonitoredByUsers(userId, this.tokenForLogin, this::responseGetMonitoredById);
        }
    }

    public void addNewMonitors(Long userId, User targetUser, SimpleCallback<List<User>> callback) {
        this.callbackForAddNewMonitors = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.addNewMonitors(userId, targetUser, this.tokenForLogin, this::responseAddNewMonitors);
        }
    }

    public void addNewMonitoredBy(Long userId, User targetUser, SimpleCallback<List<User>> callback) {
        this.callbackForAddNewMonitoredBy = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.addNewMonitoredBy(userId, targetUser, this.tokenForLogin, this::responseAddNewMonitoredBy);
        }
    }

    public void stopMonitoring(Long userId, Long targetId, SimpleCallback<Void> callback) {
        this.callbackForStopMonitoring = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.stopMonitoring(userId, targetId, this.tokenForLogin, this::responseStopMonitoring);
        }
    }

    public void stopMonitoredBy(Long userId, Long targetId, SimpleCallback<Void> callback) {
        this.callbackForStopMonitoredBy = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.stopMonitoredBy(userId,targetId,this.tokenForLogin,this::responseForStopMonitoredBy);
        }
    }

    public void createNewGroup(Group group, SimpleCallback<Group> callback) {
        this.callbackForCreateNewGroup = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.createNewGroup(group, this.tokenForLogin, this::responseForCreateNewGroup);
        }
    }

    public void getGroups(SimpleCallback<List<Group>> callback) {
        this.serverCallbackForGetGroups = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.getGroups(this.tokenForLogin, this::responseForGetGroups);
        }

    }

    public void getGroupDetailsById(Long groupId,SimpleCallback<Group> callback) {
        this.callbackForGetGroupDetailsById = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.getGroupDetailsById(groupId,this.tokenForLogin,this::responseForGetGroupDetailsById);

        }
    }

    public void updateGroupDetails(Long groupId, Group updatedGroup,SimpleCallback<Group> callback) {
        this.callbackForUpdateGroupDetails = callback;
        Server server = new Server();
        if (isUserLoggedin) {
            server.updateGroupDetails(groupId,updatedGroup,this.tokenForLogin,this::responseForUpdateGroupDetails);
        }
    }
    public void addUserToGroup(Long groupId,User addUser,SimpleCallback<List<User>> callback){
        this.callbackForAddNewUserToGroup = callback;
        Server server = new Server();
        if (isUserLoggedin){
            server.addNewUser(groupId, addUser, this.tokenForLogin, this::responseForAddNewUserToGroup);
        }
    }

    public void deleteMemberOfGroup(Long groupId, Long userId, SimpleCallback<Void> callback) {
        this.callbackForDeleteMemberOfGroup = callback;
        Server server = new Server();
        if(isUserLoggedin) {
            server.deleteMemberOfGroup(groupId,userId,this.tokenForLogin,this::responseForDeleteMemberOfGroup);
        }
    }

    public void getMembersOfGroup(Long groupId, SimpleCallback<List<User>> callback) {
        this.callbackForGetMembersOfGroup = callback;
        Server server = new Server();
        if(isUserLoggedin) {
            server.getMembersOfGroup(groupId, this.tokenForLogin, this::responseForGetMembersOfGroups);
        }
    }



}
