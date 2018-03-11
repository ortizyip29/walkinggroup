package com.example.junhosung.aquagroupwalkingapp.model;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback4;

import java.util.Iterator;
import java.util.List;

/**
 * Created by karti on 2018-03-04.
 */

public class Model extends AppCompatActivity {
    private SimpleCallback callback;
    private SimpleCallback4 callbackForVoid;
    private boolean isUserLoggedin;
    // users should be public no?

    public UserCollection users;
    private String tokenForLogin;


    private Model() {
        users = UserCollection.getInstance();
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

    public void logIn(String loginEmail, String password, SimpleCallback4 callback4) {
        isUserLoggedin = false;
        this.callbackForVoid = callback4;
        Server server = new Server();
        User user = new User();
        user.setEmail(loginEmail);
        user.setPassword(password);
        server.loginUser(user,(String token)->responseLogin(token));

    }

    private void createNewUserResponse(User user) {
        //UserCollection2.getInstance().addUser(user);
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
            UserCollection2.getInstance().addUser(user);
        }
    }

   /* public List<String> getUsers(){
        return  UserCollection2.getInstance().getUsersList();
    }
*/
    public List<User> getUsers(){
        return UserCollection2.getInstance().returnUsers();
    }


}
