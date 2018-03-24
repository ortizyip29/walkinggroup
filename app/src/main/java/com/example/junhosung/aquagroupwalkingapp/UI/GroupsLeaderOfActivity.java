package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupsLeaderOfActivity extends AppCompatActivity {

    Model model = Model.getInstance();
    User currentUser = new User();
    List<Group> groupsLeaderOf = new ArrayList<>();
    List<Group> leaderOfGroupsWithIdOnly = new ArrayList<>();
    List<User> listOfUsersForSelectedGroup = new ArrayList<>();

    List<User> listOfUsersWithIdOnlyForSelectedGroup = new ArrayList<>();
    private final String TAG = "GroupsLeaderOfActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_leader_of);

        if(model.getCurrentUser()!=null){
            currentUser = model.getCurrentUser();
        }
        for(Group group:currentUser.getLeadsGroups()) {
            Group newGroup = new Group();
            newGroup.setId(group.getId());
            leaderOfGroupsWithIdOnly.add(newGroup);
            Log.v(TAG,"callded");
        }
        getInformationOnGroups();
        SetupBackBtn();
    }

    private void getInformationOnGroups() {
        if(!leaderOfGroupsWithIdOnly.isEmpty()){
            model.getGroupDetailsById(leaderOfGroupsWithIdOnly.get(0).getId(),this::responseGetGroupDetails);
            leaderOfGroupsWithIdOnly.remove(0);
        }

    }

    private void responseGetGroupDetails(Group group) {
        groupsLeaderOf.add(group);
        if(!leaderOfGroupsWithIdOnly.isEmpty()){
            getInformationOnGroups();
        }else{
            SetupSelectGroupRadioButton();
        }
    }


    private void SetupSelectGroupRadioButton() {
        Spinner spinner = (Spinner) findViewById(R.id.SelectGroup);

        List<String> groupDetail = new ArrayList<>();
        if(groupsLeaderOf!=null){
            for(Group group:groupsLeaderOf){
                groupDetail.add(group.getGroupDescription());
            }
        }

        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.leader_groups, groupDetail);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listOfUsersWithIdOnlyForSelectedGroup = new ArrayList<>();
                listOfUsersForSelectedGroup = new ArrayList<>();
                if(!groupsLeaderOf.get(i).getMemberUsers().isEmpty()) {
                    for(User user:groupsLeaderOf.get(i).getMemberUsers() ){
                        User newUser = new User();
                        newUser.setId(user.getId());
                        listOfUsersWithIdOnlyForSelectedGroup.add(newUser);
                    }
                    getUsersInformation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getUsersInformation(){
        if(!listOfUsersWithIdOnlyForSelectedGroup.isEmpty()){
            model.getUserById(listOfUsersWithIdOnlyForSelectedGroup.get(0).getId(),this::responseWithUserDetailsAboutSelectedGroup);
            listOfUsersWithIdOnlyForSelectedGroup.remove(0);
        }
    }

    private void responseWithUserDetailsAboutSelectedGroup(User user) {
        if(user!=null){
            listOfUsersForSelectedGroup.add(user);
        }
        if(! listOfUsersWithIdOnlyForSelectedGroup.isEmpty()) {
            getUsersInformation();
        } else{
            setupListViewOfUsers();
        }
    }

    private void setupListViewOfUsers() {
        Spinner groupSelectedLeaderOf = (Spinner) findViewById(R.id.selectUser);
        List<String> userDetail = new ArrayList<>();
        for(User user : listOfUsersForSelectedGroup){
            userDetail.add(user.getName());
        }
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.user_leader_groups_users, userDetail);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSelectedLeaderOf.setAdapter(adapter);

        groupSelectedLeaderOf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EditText text = (EditText) findViewById(R.id.UserSelectedLeaderOf) ;
                User currentUserSelected = listOfUsersForSelectedGroup.get(i);
                String displayString = "";
                if(currentUserSelected.getName()!=null){
                    displayString = displayString +"Name: " + currentUserSelected.getName();
                }
                if(currentUserSelected.getEmail()!=null){
                    displayString = displayString +"\nEmail: " + currentUserSelected.getEmail();
                }
                if(currentUserSelected.getCellPhone()!=null){
                    displayString = displayString +"\nCellPhone: " + currentUserSelected.getCellPhone();
                }
                if(currentUserSelected.getHomePhone()!=null){
                    displayString = displayString +"\nHomePhone: " + currentUserSelected.getHomePhone();
                }
                if(currentUserSelected.getGrade()!=null){
                    displayString = displayString +"\nGrade: " + currentUserSelected.getGrade();
                }
                if(currentUserSelected.getTeacherName()!=null){
                    displayString = displayString +"\nTeacherName: " + currentUserSelected.getTeacherName();
                }
                if(currentUserSelected.getEmergencyContactInfo()!=null){
                    displayString = displayString +"\nEmergency Contact: " + currentUserSelected.getEmergencyContactInfo();
                }
                text.setText(displayString);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void SetupBackBtn() {
/*        Button btn =  (Button) findViewById(R.id.btnBackFromGroupsLeaderOf);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
    }


}
