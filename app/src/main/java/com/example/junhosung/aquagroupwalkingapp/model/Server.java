package com.example.junhosung.aquagroupwalkingapp.model;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback2;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback4;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback5;
import com.example.junhosung.aquagroupwalkingapp.SimpleCallback6;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;
import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Junho Sung on 3/9/2018.
 */
//TO DO: list of monitored by and monitoring
public class Server extends AppCompatActivity {
    private static final String TAG = "Server Class";
    //private static Server instance;
    //   public Server getInstance(){
    //       if(instance == null){
    //            instance = new Server();
    //        }
    //        return instance;
    //   }

    private WGServerProxy proxy;
    private SimpleCallback serverCallback;
    private SimpleCallback2 serverCallbackForUser;
    private SimpleCallback5 serverCallbackForLogin;
    private SimpleCallback6 serverCallbackForList;
    private String token;

    //    private boolean isUserLoggedIn = false;

    public Server(){
 ///       this.isUserLoggedIn = isUserLoggedIn;
        proxy = ProxyBuilder.getProxy("D43B2DCD-D2A8-49EF-AFCC-6B1E309D1B58", null);
        //proxy = ProxyBuilder.getProxy(getString(R.string.apikey), null);
    }

    private void loginResponse(Void returnedNothing) {
        Log.w(TAG, "Server replied for user login: " );
        serverCallbackForLogin.callback(this.token);
    }
    public <T extends Object> void loginUser(User user, SimpleCallback5 callback){
        this.serverCallbackForLogin = callback;
        ProxyBuilder.setOnTokenReceiveCallback( token -> onReceiveToken(token));
        // Make call
        Call<Void> caller = proxy.login(user);
        ProxyBuilder.callProxy(Server.this, caller, returnedNothing -> loginResponse(returnedNothing));
    }

    private void createNewUserResponse(User user) {

        ///delete this line later
        //UserCollection2.getInstance().addUser(user);
        // the calling class should add the user
        Log.w(TAG, "Server replied with user: " + user.toString());
        serverCallbackForUser.callback(user);
    }

    public <T extends Object> void createNewUser(User user, final SimpleCallback2<User> callback){
        serverCallbackForUser = callback;
        Call<User> caller = proxy.createNewUser(user);
        ProxyBuilder.callProxy(Server.this, caller,returnedUser -> createNewUserResponse(returnedUser) );
    }

    private void responseGetListOfUsers(List<User> returnedUsers) {
           for (User user : returnedUsers) {
               Log.w(TAG, "    User: " + user.toString());
          }
          serverCallbackForList.callback(returnedUsers);
      }

    public <T extends Object> void getListOfUsers(String token, SimpleCallback6 callback){
        onReceiveToken(token);
        this.serverCallbackForList = callback;
        Call<List<User>> caller = proxy.getUsers();
        ProxyBuilder.callProxy(Server.this, caller, returnedUsers ->responseGetListOfUsers(returnedUsers) );
    }


    public void getParentGroup(){
    }

    public void getCoordinatesForGroup(){
    }

    public void pushUserGPSCoordinates(String longitude, String latitude) {
    }
    public void getUserGroup(){
    }


    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w(TAG, "   --> NOW HAVE TOKEN: " + token);
        this.token = token;
        proxy = ProxyBuilder.getProxy("D43B2DCD-D2A8-49EF-AFCC-6B1E309D1B58", token);
        //proxy = ProxyBuilder.getProxy(getString(R.string.apikey), token);
    }

}
