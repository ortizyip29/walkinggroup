package com.example.junhosung.aquagroupwalkingapp.model;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.proxy.ProxyBuilder;
import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Junho Sung on 3/9/2018.
 */

public class Server extends AppCompatActivity {
    private WGServerProxy proxy;

    private Server(){
        proxy = ProxyBuilder.getProxy(getString(R.string.apikey), null);
    }
    private static Server instance;
    public Server getInstance(){
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }

    //public void updateUser(User user){
        //Call<User> caller = proxy.createNewUser(user);
        //ProxyBuilder.callProxy(Server.class, caller, returnedUser -> response(returnedUser));

    //}

    private void response(User user) {
      //  Log.w(TAG, "Server replied with user: " + user.toString());
      //  userId = user.getId();
    }
    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
       // Log.w(TAG, "   --> NOW HAVE TOKEN: " + token);
        proxy = ProxyBuilder.getProxy(getString(R.string.apikey), token);
    }

    private void response(Void returnedNothing) {
     //   Log.w(TAG, "Server replied to login request (no content was expected).");
    }

    private void response(List<User> returnedUsers) {
        //Log.w(TAG, "All Users:");
        for (User user : returnedUsers) {
          //  Log.w(TAG, "    User: " + user.toString());
        }
    }
}
