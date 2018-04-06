package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;

import javax.security.auth.Destroyable;

public class CreateNewGroupActivity extends AppCompatActivity {

    Model model = Model.getInstance();
    private final String TAG = "CreateNewGroupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
        wireupBackBtn();
        wireupAddNewGroupBtn();
    }
    private void wireupBackBtn() {
        Button Btn = (Button) findViewById(R.id.btnBackFromCreateGroup);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void addressToCoordinatesConverter(String start,String dest){

    }

    private void wireupAddNewGroupBtn() {
        Button Btn = (Button) findViewById(R.id.btnAddGroup);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = ((TextView) findViewById(R.id.createNewGroupName)).getText().toString();
                String groupMeetingPlace = ((TextView) findViewById(R.id.createNewGroupMeetingPlace)).getText().toString();
                String groupDestination = ((TextView) findViewById(R.id.createNewGroupDestination)).getText().toString();
                if(groupName.equals("") || groupMeetingPlace.equals("") || groupDestination.equals("")){
                    Toast.makeText(CreateNewGroupActivity.this,"One of your field is empty",Toast.LENGTH_SHORT).show();
                } else{
                    Group group = new Group();
                    group.setGroupDescription(groupName);
                    group.setStartingPoint(groupMeetingPlace);
                    group.setDestination(groupDestination);
                    group.setLeader(model.getCurrentUser());
                    Log.i(TAG,group.toString());
                    model.createNewGroup(group,this::responseForCreateNewUser);
                }
            }

            private void responseForCreateNewUser(Group group) {
                Log.i(TAG,group.toString());
                Toast.makeText(CreateNewGroupActivity.this,group.toString(),Toast.LENGTH_LONG).show();
                model.getCurrentUser();
                model.getUserByEmail(model.getCurrentUser().getEmail(),this::responseGetUserByEmail);
                finish();
            }
            private void responseGetUserByEmail(User user){
                if(user!=null){
                    model.setCurrentUser(user);
                }
            }
        });

    }

}
