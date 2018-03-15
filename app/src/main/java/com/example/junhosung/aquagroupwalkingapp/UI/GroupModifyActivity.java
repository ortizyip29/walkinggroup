package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupModifyActivity extends AppCompatActivity {
    private Model model = Model.getInstance();
    User current = model.getCurrentUser();
    //User receivedUser;
    List<User> monitorsList;
    String[] nameAndEmail;
    List<User> currentGroupUserList = new ArrayList<>();
    private Group currentGroup;
    private int itemClicked;
    private int itemClickedToAdd;
    private boolean isItemClickedForAdd = false;
    ArrayAdapter<String> adapter;
    private final String TAG = "GroupModifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_modify);

        refreshPage();
        setUpAddbtn();
        setUpDelbtn();
        setUpBackbtn();

    }

    private void setUpBackbtn() {
        Button button = (Button) findViewById(R.id.btnBack);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpAddbtn() {
        Button button = (Button) findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener(){

            private void voidCallback(Void aVoid){}
            @Override
            public void onClick(View view) {
                if(isItemClickedForAdd) {
                    User addthisUser =  monitorsList.get(itemClickedToAdd);
                    /*if(addthisUser==null){
                        Log.v(TAG,"------NNNNNNNNNNNNNNNNULLLLLLLLLLLLLLLLL");
                    } else{
                        Log.v(TAG,"-------NNNNNNNNNNNNNNNNULLLLLLLLLLLLLLLLL2");
                    }
                    Log.v(TAG, "NAme of group here --------------------------"+currentGroup.getGroupDescription());
                    List<User> groupUsers = currentGroup.getMemberUsers();

                    groupUsers.add(addthisUser);
                    for(User user:groupUsers){
                        Log.v(TAG,"USER HAS ADDED"+user.toString());
                        //user.toString();
                    }
                    currentGroup.setMemberUsers(groupUsers);
                    if(currentGroup==null){
                        Log.v(TAG,"currentGroup is Null");
                    } else{
                        Log.v(TAG,"currentGroup is not Null");
                    }


                    for(User user:currentGroup.getMemberUsers()){
                        Log.v(TAG,"addded some user  --------------------------------------------------------------------------------");
                       Log.v(TAG,user.toString());

                    }*/
                    model.addUserToGroup(currentGroup.getId(),addthisUser,this::responseAfterAdd2);
                    //model.updateGroupDetails(currentGroup.getId(),currentGroup,this::responseAfterAdd);
                }
            }

            private void responseAfterAdd2(List<User> users) {
                refreshPage();
            }
            private void responseAfterAdd(Group group) {
                refreshPage();
            }
        });
    }

    private void setUpDelbtn(){
        Button button = (Button) findViewById(R.id.btnDel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentGroupUserList.get(itemClicked).getId();
                if(currentGroup!=null){
                         currentGroup.getMemberUsers().remove(itemClicked);
                    model.updateGroupDetails(currentGroup.getId(),currentGroup,this::responseAfterRemove);
                }
            }
            private void responseAfterRemove(Group group) {
                refreshPage();
            }
        });
    }

    public void refreshPage(){
        model.getMonitoredById(current.getId(),this::responseWithUserMonitors);
        model.getGroupDetailsById(model.getCurrentGroupInUseByUser().getId(),this::callbackForGetCurerentGroupDetails);
    }
    private void responseWithUserMonitors(List<User> users) {
        monitorsList = users;
        nameAndEmail = new String [monitorsList.size()];
        for (int i = 0; i < monitorsList.size();i++) {
            nameAndEmail[i] ="        " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
        }

        adapter = new ArrayAdapter<String>(this, R.layout.activity_group_modifymm, nameAndEmail);

        ListView list = (ListView) findViewById(R.id.listViewMM);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long l) {
                isItemClickedForAdd = true;
                itemClickedToAdd = item;
            }
        });
    }

    private void callbackForGetCurerentGroupDetails(Group group) {
        currentGroup = group;
        List<User> currentGroupUserList = group.getMemberUsers();
        List<String> groupUsersDisplayList = new ArrayList<>();
        for(User thisUser: currentGroupUserList){
            groupUsersDisplayList.add(thisUser.getName() +" , "+ thisUser.getEmail());
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.activity_group_mod, groupUsersDisplayList);

        //Configure the list view
        ListView list = (ListView) findViewById(R.id.listViewGroup);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long l) {
                itemClicked = item;
            }
        });

    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, GroupModifyActivity.class);
    }
}
