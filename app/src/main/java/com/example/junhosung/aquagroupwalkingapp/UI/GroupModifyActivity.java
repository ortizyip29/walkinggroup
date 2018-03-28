package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Context;
import android.content.Intent;
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


    long currentUserID; //= model.getCurrentGroupInUseByUser().getId();
    private List<User> listOfUsersDeleteable;
    private List<User> monitorsList;
    private String[] nameAndEmail;
    private Group currentGroup;
    private int itemClickedForDelete;
    private int itemClickedToAdd;
    private boolean isItemClickedForAdd = false;
    private final String TAG = "GroupModifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_modify);
        if(model.getCurrentGroupInUseByUser()!=null){
            currentUserID = model.getCurrentGroupInUseByUser().getId();
        }

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
                    model.addUserToGroup(currentGroup.getId(),addthisUser,this::responseAfterAdd);
                }
            }

            private void responseAfterAdd(List<User> users) {
                refreshPage();
            }
        });
    }

    private void setUpDelbtn(){
        Button button = (Button) findViewById(R.id.btnDel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listOfUsersDeleteable!=null){
                long userIdOfUserToDeleteFromGroup=listOfUsersDeleteable.get(itemClickedForDelete).getId();
                    model.deleteMemberOfGroup(currentGroup.getId(),userIdOfUserToDeleteFromGroup,this::responseAfterRemove);
                }
            }
            private void responseAfterRemove(Void voidObject) {
                refreshPage();
            }
        });
    }

    public void refreshPage(){
        Log.w(TAG,"model.getCurrentUSer"+model.getCurrentUser().toString());

        if(model.getCurrentGroupInUseByUser()!=null){

            model.getMonitorsById(model.getCurrentUser().getId(),this::responseWithUserMonitors); //list of monitoring
            //      model.getMonitoredById(current.getId(),this::responseWithUserMonitors);
            model.getGroupDetailsById(model.getCurrentGroupInUseByUser().getId(),this::callbackForGetCurrentGroup);//list of delete
        }
    }

    private void callbackForGetCurrentGroup(Group group) {
        currentGroup = group;

        model.getMembersOfGroup(currentUserID,this::callbackForGetCurrentGroupDetails);
    }

    private void responseWithUserMonitors(List<User> users) {
        monitorsList = users;
        nameAndEmail = new String[monitorsList.size()];
        for (int i = 0; i < monitorsList.size();i++) {
            nameAndEmail[i] ="        " + monitorsList.get(i).getName() + "  :  " + monitorsList.get(i).getEmail();
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.add_users_to_current_group_list, nameAndEmail);

        ListView list = (ListView) findViewById(R.id.listViewMemberThatCanBeAddedToCurrentGroup);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long l) {
                isItemClickedForAdd = true;
                itemClickedToAdd = item;
            }
        });
    }

    private void callbackForGetCurrentGroupDetails(List<User> group) {
        listOfUsersDeleteable = group;
        List<String> groupUsersDisplayList = new ArrayList<>();
        for(User thisUser: group){
            groupUsersDisplayList.add(thisUser.getName() +" , "+ thisUser.getEmail());
            Log.v(TAG,"something is here----------------------------------");
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.delete_users_from_group_list, groupUsersDisplayList);
        ListView list = (ListView) findViewById(R.id.listViewMemberOfCurrentGroup);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item, long l) {
                itemClickedForDelete = item;
            }
        });

    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, GroupModifyActivity.class);
    }
}
