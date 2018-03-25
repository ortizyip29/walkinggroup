package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupsLeaderOfActivity extends AppCompatActivity {

    Model model = Model.getInstance();
    User currentUser = new User();
//    List<User> listOfUsersThatAreGettingMoniterByCurrentUser = new ArrayList<>();
    List<User> listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly = new ArrayList<>();
    User currentUserSelectedForGroupSelected = new User();
    List<Group> groupsThatUsersCanSeeInformationOn = new ArrayList<>();
    List<Group> groupsThatUsersCanSeeInformationOnWithIdOnly = new ArrayList<>();
    List<User> listOfUsersForSelectedGroup = new ArrayList<>();
    List<User> listOfUserWhoMonitorUserWithIdOnly = new ArrayList<>();
    List<User> listOfUserWhoMonitorUser = new ArrayList<>();
    ProgressBar progressBar;
    List<User> listOfUsersWithIdOnlyForSelectedGroup = new ArrayList<>();
    private final String TAG = "GroupsLeaderOfActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_leader_of);
        progressBar = (ProgressBar) findViewById(R.id.progressBarGroupLeaderOfActivity);

        if(model.getCurrentUser()!=null){
            currentUser = model.getCurrentUser();
        }
        for(Group group:currentUser.getLeadsGroups()) {
            Group newGroup = new Group();
            newGroup.setId(group.getId());
            groupsThatUsersCanSeeInformationOnWithIdOnly.add(newGroup);
            Log.v(TAG,"callded");
        }
        for(Group group:currentUser.getMemberOfGroups()) {
            Group newGroup = new Group();
            newGroup.setId(group.getId());
            groupsThatUsersCanSeeInformationOnWithIdOnly.add(newGroup);
            Log.v(TAG,"callded");
        }
        for(User user:currentUser.getMonitorsUsers())
        {
            User newUser = new User();
            newUser.setId(user.getId());
            listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly.add(newUser);
        }
        if(listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly.isEmpty()){
            removeDuplicates();
        } else {
            getGroupsForUser();
        }
        SetupBackBtn();
    }
    private void getGroupsForUser(){
        while(!listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            model.getUserById(listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly.get(0).getId(),this::responseForGetUserDetails);
            listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly.remove(0);
        }
    }

    private void responseForGetUserDetails(User responseUser) {
        for(Group group:responseUser.getMemberOfGroups()) {
            groupsThatUsersCanSeeInformationOnWithIdOnly.add(group);
        }
        if(!listOfUsersThatAreGettingMoniterByCurrentUserWithIdOnly.isEmpty()){
            getGroupsForUser();
        } else {
            progressBar.setVisibility(View.GONE);
            removeDuplicates();
        }
    }
    private int compareTo1(Group a,Group b) {
        if(a.getId() > b.getId()){
            return 1;
        } else if(a.getId() == b.getId()) {
            return 0;
        }
        else {
            return -1;
        }
    }

    private void removeDuplicates() {
        if(!groupsThatUsersCanSeeInformationOnWithIdOnly.isEmpty()) {
            Collections.sort(groupsThatUsersCanSeeInformationOnWithIdOnly, (a, b) -> compareTo1(a,b) );
            Log.w(TAG,"removeDuplicatesFunctionCalled");
            for(Group group: groupsThatUsersCanSeeInformationOnWithIdOnly){
                Log.w(TAG,"group ID in sorted order" + group.getId());
            }
            int checkUpperIndexForDuplicate = 1;
            int checkLowerIndexForDuplicate = 0;
            boolean duplicateFound = false;
            if(groupsThatUsersCanSeeInformationOnWithIdOnly.size()>=2){
                while(checkUpperIndexForDuplicate<groupsThatUsersCanSeeInformationOnWithIdOnly.size()) {
                    if(groupsThatUsersCanSeeInformationOnWithIdOnly.get(checkLowerIndexForDuplicate).getId()==groupsThatUsersCanSeeInformationOnWithIdOnly.get(checkUpperIndexForDuplicate).getId()){
                        Log.w(TAG,"duplicate Found: " + groupsThatUsersCanSeeInformationOnWithIdOnly.get(checkLowerIndexForDuplicate).getId()
                                + " and " +groupsThatUsersCanSeeInformationOnWithIdOnly.get(checkUpperIndexForDuplicate).getId());
                        groupsThatUsersCanSeeInformationOnWithIdOnly.remove(checkUpperIndexForDuplicate);
                        duplicateFound = true;
                    } else {
                        Log.w(TAG,"duplicate Not  Found: " + groupsThatUsersCanSeeInformationOnWithIdOnly.get(checkLowerIndexForDuplicate).getId()
                                + " and " +groupsThatUsersCanSeeInformationOnWithIdOnly.get(checkUpperIndexForDuplicate).getId());
                        duplicateFound = false;
                    }
                    if(!duplicateFound){
                        checkUpperIndexForDuplicate++;
                        checkLowerIndexForDuplicate++;
                        Log.w(TAG,"updating index upper: " + checkUpperIndexForDuplicate +" and lower " +checkLowerIndexForDuplicate);

                    }
                }
            }
            for(Group group: groupsThatUsersCanSeeInformationOnWithIdOnly){
                Log.w(TAG,"group ID in sorted order after removing duplicates" + group.getId());
            }

        }
        getInformationOnGroups();
        /*        groupsThatUsersCanSeeInformationOnWithIdOnly.sort(Comparator.comparing((Group::getId)));
        Comparator<Double> comp = (Double a, Double b) -> {
            return b.compareTo(a);
        };
      Collections.sort(groupsThatUsersCanSeeInformationOnWithIdOnly, comp);
*//*
        Collections.sort(groupsThatUsersCanSeeInformationOnWithIdOnly, new Comparator<CustomData>() {
            @Override
            public int compare(CustomData lhs, CustomData rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.customInt > rhs.customInt ? -1 : (lhs.customInt < rhs.customInt) ? 1 : 0;
            }
        });
*/

    }

    private void getInformationOnGroups() {
        if(!groupsThatUsersCanSeeInformationOnWithIdOnly.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
            long id = groupsThatUsersCanSeeInformationOnWithIdOnly.get(0).getId();
            Log.w(TAG,"group ID in sorted order before calling" + id);
            groupsThatUsersCanSeeInformationOnWithIdOnly.remove(0);
            model.getGroupDetailsById(id,this::responseGetGroupDetails);
        }

    }

    private void responseGetGroupDetails(Group group) {
        Log.w(TAG,"group ID in sorted order after response " + group.getId());
        if(!groupsThatUsersCanSeeInformationOn.isEmpty()) {
            if(groupsThatUsersCanSeeInformationOn.get(groupsThatUsersCanSeeInformationOn.size()-1).getId()!=group.getId() ) {
                groupsThatUsersCanSeeInformationOn.add(group);
            }
        } else {
            groupsThatUsersCanSeeInformationOn.add(group);
        }
        if(!groupsThatUsersCanSeeInformationOnWithIdOnly.isEmpty()){
            getInformationOnGroups();
        }else{
            progressBar.setVisibility(View.GONE);
            for(Group printDubugGroup: groupsThatUsersCanSeeInformationOn){
                Log.w(TAG,"group ID in sorted order" + printDubugGroup.getId() +  " and name " + printDubugGroup.getGroupDescription());
            }
            SetupSelectGroupSpinner();
        }
    }


    private void SetupSelectGroupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.SelectGroup);

        List<String> groupDetail = new ArrayList<>();
        if(groupsThatUsersCanSeeInformationOn !=null){
            for(Group group: groupsThatUsersCanSeeInformationOn){
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
                if(!groupsThatUsersCanSeeInformationOn.get(i).getMemberUsers().isEmpty()) {
                    for(User user: groupsThatUsersCanSeeInformationOn.get(i).getMemberUsers() ){
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
            progressBar.setVisibility(View.VISIBLE);
            model.getUserById(listOfUsersWithIdOnlyForSelectedGroup.get(0).getId(),this::responseWithUserDetailsAboutSelectedGroup);
            listOfUsersWithIdOnlyForSelectedGroup.remove(0);
        }
    }

    private void responseWithUserDetailsAboutSelectedGroup(User user) {
        if(user!=null){
            if(listOfUsersForSelectedGroup.isEmpty()){
                listOfUsersForSelectedGroup.add(user);
            } else {
                if(listOfUsersForSelectedGroup.get(listOfUsersForSelectedGroup.size()-1).getId()!=user.getId()) {
                    listOfUsersForSelectedGroup.add(user);
                }
            }
        }
        if(! listOfUsersWithIdOnlyForSelectedGroup.isEmpty()) {
            getUsersInformation();
        } else{
            progressBar.setVisibility(View.GONE);
            setupListViewOfUsers();
        }
    }

    private void setupListViewOfUsers() {
        Spinner groupSelectedLeaderOf = (Spinner) findViewById(R.id.selectUser);
        List<String> userDetail = new ArrayList<>();
        for(User user : listOfUsersForSelectedGroup){
            userDetail.add(user.getName() + " , " + user.getEmail());
        }
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.user_leader_groups_users, userDetail);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSelectedLeaderOf.setAdapter(adapter);

        groupSelectedLeaderOf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                EditText text = (EditText) findViewById(R.id.UserSelectedLeaderOf) ;
                User currentUserSelected = listOfUsersForSelectedGroup.get(i);
                currentUserSelectedForGroupSelected = currentUserSelected;
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

                listOfUserWhoMonitorUserWithIdOnly = new ArrayList<>();
                listOfUserWhoMonitorUser = new ArrayList<>();
                if(currentUserSelected!=null){
                    for( User user: currentUserSelected.getMonitoredByUsers() ){
                        User addUser = new User();
                        addUser.setId(user.getId());
                        listOfUserWhoMonitorUserWithIdOnly.add(addUser);
                    }
                }
                if(currentUserSelected!=null){

                    getInformationOnUsersForMonitoredBy();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


    }

    private void getInformationOnUsersForMonitoredBy() {

        if( !listOfUserWhoMonitorUserWithIdOnly.isEmpty() ){
            progressBar.setVisibility(View.VISIBLE);
            model.getUserById(listOfUserWhoMonitorUserWithIdOnly.get(0).getId(),this::responseForGetInformationOnUserForMonitoredByUSer);
            listOfUserWhoMonitorUserWithIdOnly.remove(0);
        }
    }

    private void responseForGetInformationOnUserForMonitoredByUSer(User user){
        if(listOfUserWhoMonitorUser.isEmpty()) {
            listOfUserWhoMonitorUser.add(user);
        } else {
            if(listOfUserWhoMonitorUser.get(listOfUserWhoMonitorUser.size()-1).getId()!=user.getId()) {
                listOfUserWhoMonitorUser.add(user);
            }
        }
        if(!listOfUserWhoMonitorUserWithIdOnly.isEmpty()){
            getInformationOnUsersForMonitoredBy();
        } else {
            progressBar.setVisibility(View.GONE);
            setupSpinnerForMonitoredBy();
        }
    }
    private void setupSpinnerForMonitoredBy() {
        Spinner spinner = (Spinner) findViewById(R.id.userMonitoredBySpinner);

        List<String> usersWhoMonitorSelectedUserDetails = new ArrayList<>();
        if(groupsThatUsersCanSeeInformationOn !=null){
            for(User user:listOfUserWhoMonitorUser){
                usersWhoMonitorSelectedUserDetails.add(user.getName()+ " , " + user.getEmail());
            }
        }

        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, R.layout.user_detail_who_monitor_selected_user, usersWhoMonitorSelectedUserDetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EditText text = (EditText) findViewById(R.id.informationOnSelectedUser) ;
                User currentUserSelected = listOfUserWhoMonitorUser.get(i);

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
