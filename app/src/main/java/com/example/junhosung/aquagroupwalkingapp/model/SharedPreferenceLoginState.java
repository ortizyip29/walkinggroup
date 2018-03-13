package com.example.junhosung.aquagroupwalkingapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by David-Yoga on 2018-03-06.
 */

public class SharedPreferenceLoginState {
    static final String LOGIN_EMAIL = "email";
    static final String LOGIN_PASSWORD = "password";

    static SharedPreferences getSharedPreferences(Context login) {
        return PreferenceManager.getDefaultSharedPreferences(login);

    }

    public static void setEmail(Context login, String email, String password){
        SharedPreferences.Editor edit = getSharedPreferences(login).edit();
        SharedPreferences.Editor edit2 = getSharedPreferences(login).edit();
        edit.putString(LOGIN_EMAIL, email);
        edit2.putString(LOGIN_PASSWORD, password);
        edit.commit();
        edit2.commit();
    }

    public static String getEmail(Context login){
        //Log.i("Save state", LOGIN_EMAIL);
        return getSharedPreferences(login).getString(LOGIN_EMAIL, "");

    }

    public static String getPassword(Context login){
        return getSharedPreferences(login).getString(LOGIN_PASSWORD, "");
    }

    public static void clearSharedPref(Context login){
        SharedPreferences.Editor edit = getSharedPreferences(login).edit();
        SharedPreferences.Editor edit2 = getSharedPreferences(login).edit();
        edit.remove(LOGIN_EMAIL);
        edit2.remove(LOGIN_PASSWORD);
        edit.commit();
        edit2.commit();

    }
}
