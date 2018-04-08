package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserLeaderboardActivity extends AppCompatActivity{
    Model model = Model.getInstance();
    List<String> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_user_leaderboard);
        populateListView(model.getUsers());
    }
    private void populateListView(List<User> users){
        //String[] userList = {"piggy bank","yipper"};
        List<String> userList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_users,userList);
        for(User user:users){
            userList.add(user.getName()+"         :         " +user.getTotalPointsEarned()+"  Total Points Earned");
        }
        ListView memberListView = (ListView) findViewById(R.id.leaderboardLV);
        memberListView.setAdapter(adapter);
        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });


    }
}
