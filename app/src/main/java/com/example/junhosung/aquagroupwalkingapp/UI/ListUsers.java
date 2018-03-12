package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class ListUsers extends AppCompatActivity implements  GestureDetector.OnGestureListener{
    GestureDetector gestureDectector;
    private final String TAG ="ListUsers";
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        finish();
        return true;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }
    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        users = Model.getInstance().getUsersOld();
        if(users==null){
            Log.i(TAG, "Model.getInstance().getUsersOld() is null");
        }
        populateUsersList();
        wireSetOnClickListViewListerner();
    }


    private void populateUsersList() {
        ListView monitoringList = (ListView) findViewById(R.id.listUsers);
        List<String> monitoringListString = new ArrayList<>();
        if(users==null){
            Log.i(TAG, "usersOld is null");
        }
        else{
            for(User user : users){
                monitoringListString.add(user.getName() + " , " +user.getEmail());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_users,monitoringListString);
            monitoringList.setAdapter(adapter);
        }
     }
    private void wireSetOnClickListViewListerner() {
        ListView monitoringList = (ListView) findViewById(R.id.listUsers);
        monitoringList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewClicked, int indexOfClick, long idOfLick) {
                TextView textView = (TextView) viewClicked;
                //this id clicked
                 users.get(indexOfClick).getId();
            }
        });
    }
}
