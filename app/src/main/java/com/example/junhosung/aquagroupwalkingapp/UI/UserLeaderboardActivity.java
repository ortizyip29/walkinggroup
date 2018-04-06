package com.example.junhosung.aquagroupwalkingapp.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserLeaderboardActivity extends AppCompatActivity{
    Model model = Model.getInstance();
    User currentUser = model.getCurrentUser();
    List<String> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leaderboard);
        populateListView(model.getUsers());
        colourSchemeChange();
       // TextView textviewDisplay = (TextView)findViewById(R.id.displayCurrentUser);
        //textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and is ranked Walk Master");
       // textviewDisplay.setTextColor(255);
        setUpRewardBtn();
    }
    private void setUpRewardBtn() {
        Button btn = (Button) findViewById(R.id.rewardBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //go to reward shop
            }
        });
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
    // user get differenet title after every 30 walks

    private TextView colourSchemeChange(){
        int currentPoints = currentUser.getCurrentPoints();
        TextView textviewDisplay = (TextView)findViewById(R.id.displayCurrentUser);
        if(currentPoints<30){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked rookie");

        }
        else if(currentPoints>=30&&currentPoints<60){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked amateur ");
            textviewDisplay.setTextColor(Color.BLUE);
        }
        else if(currentPoints>=60&&currentPoints<90){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked veteran");
            textviewDisplay.setTextColor(Color.RED);
        }
        else if(currentPoints>=90&&currentPoints<120){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked master");
            textviewDisplay.setTextColor(Color.CYAN);
        }
        else{
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked Hall of Famer");
            textviewDisplay.setTextColor(Color.MAGENTA);
            //setContentView(R.layout.activity_user_leaderboard);

        }
    return textviewDisplay;
    }
}
