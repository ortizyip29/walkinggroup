package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserLeaderboardActivity extends AppCompatActivity{
    Model model = Model.getInstance();
    User currentUser = model.getCurrentUser();
    //List<String> userList = new ArrayList<>();
    List<String> displayLeaderboard = new ArrayList<>();
    List<String> userList = new ArrayList<>();
    List<Integer> userPoints = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_user_leaderboard);
        populateListView();
      //  updateLeaderboard();
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
               Intent intent = new Intent(UserLeaderboardActivity.this,ShopActivity.class);
               startActivity(intent);
                //go to reward shop
            }
        });
    }
    private void populateListView(){
        List<User> users;
        users=model.getUsers();
        //String[] displayLeaderboard = {"piggy bank","yipper"};
        ListView memberListView = (ListView) findViewById(R.id.leaderboardLV);
        memberListView.setAdapter(null);

        displayLeaderboard.remove(displayLeaderboard);
        userList.remove(userList);
        userPoints.remove(userPoints);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_users,displayLeaderboard);
        for(User user:users){
            userList.add(user.getName());
            userPoints.add(user.getTotalPointsEarned());
        }
        String[] userListInStr= new String[userList.size()];
        userList.toArray(userListInStr);
        Integer[] userPointsArr = new Integer[userPoints.size()];
        userPoints.toArray(userPointsArr);
        for(int i=1; i<userPointsArr.length; i++) {
            int temp;
            String nametemp;
            if(userPointsArr[i-1] < userPointsArr[i]) {
                nametemp = userListInStr[i-1];
                temp = userPointsArr[i-1];
                userPointsArr[i-1] = userPointsArr[i];
                userListInStr[i-1] = userListInStr[i];
                userPointsArr[i] = temp;
                userListInStr[i] = nametemp;
            }
        }
        for(int i=0;i<userListInStr.length;i++){
            displayLeaderboard.add(i+1+". "+userListInStr[i]+"         :         " +userPointsArr[i]+"  Total Points Earned");
        }

        memberListView.setAdapter(adapter);
        //displayLeaderboard.clear();
        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }
    // user get differenet title after every 30 walks

    private TextView colourSchemeChange(){
        int currentPoints = currentUser.getTotalPointsEarned();
        TextView textviewDisplay = (TextView)findViewById(R.id.displayCurrentUser);
        if(currentPoints<30){
            textviewDisplay.setText("   You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked rookie");

        }
        else if(currentPoints>=30&&currentPoints<60){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked amateur ");
            textviewDisplay.setTextColor(Color.BLUE);
        }
        else if(currentPoints>=60&&currentPoints<90){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked Veteran");
            textviewDisplay.setTextColor(Color.RED);
        }
        else if(currentPoints>=90&&currentPoints<120){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked Master");
            textviewDisplay.setTextColor(Color.CYAN);
        }
        else if(currentPoints>=120&&currentPoints<500){
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked Champion");
            textviewDisplay.setTextColor(Color.MAGENTA);
            //setContentView(R.layout.activity_user_leaderboard);
        }
        else{
            textviewDisplay.setText("You currently have "+model.getCurrentUser().getCurrentPoints()+" points and ranked Hall of Famer");
            Toast.makeText(getApplicationContext(),"Congratuations "+model.getCurrentUser().getName()+"You have reached top title IRON MAN",Toast.LENGTH_LONG).show();
            textviewDisplay.setTextColor(Color.GREEN);
        }
    return textviewDisplay;
    }
    private void updateLeaderboard(){
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                populateListView();
            }
        }.start();
    }
}
