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

    static SharedPreferences getSharedPreferences(Context login) {
        return PreferenceManager.getDefaultSharedPreferences(login);

    }

    public static void setEmail(Context login, String email){
        SharedPreferences.Editor edit = getSharedPreferences(login).edit();
        edit.putString(LOGIN_EMAIL, email);
        edit.commit();
    }

    public static String getEmail(Context login){
        Log.i("Save state", LOGIN_EMAIL);
        return getSharedPreferences(login).getString(LOGIN_EMAIL, "");

    }

    public static void clearSharedPref(Context login){
        SharedPreferences.Editor edit = getSharedPreferences(login).edit();
        edit.remove(LOGIN_EMAIL);
        edit.commit();

    }
}
