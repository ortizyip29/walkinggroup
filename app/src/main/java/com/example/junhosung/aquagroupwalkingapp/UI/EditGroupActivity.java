package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;
import com.example.junhosung.aquagroupwalkingapp.model.get_me_data_from_server.GetListOfGroupFromListOfGroupWithIDOnly;

import java.util.ArrayList;
import java.util.List;

public class EditGroupActivity extends AppCompatActivity {

    private static final String TAG = "EditGroupActivity" ;
    private  User currentUser;
    private List<Group> groupsCurrentUserPartOf;
    private Group groupSelected;
    private Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        getCurrentUserInformation();
        setupButtonToMakeLeader();

    }

    private void setupButtonToMakeLeader() {
        Button button = (Button) findViewById(R.id.makeLeaderOfEditGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupSelected!=null) {
                    groupSelected.setLeader(currentUser);
                    Long selectedGroupId = groupSelected.getId();
                    model.updateGroupDetails(selectedGroupId,groupSelected,this::responseToUpdateGroupDetails);

                    //send permission to current user leader


                }
            }

            private void responseToUpdateGroupDetails(Group group) {
                Toast.makeText(EditGroupActivity.this,"success!",Toast.LENGTH_LONG).show();
            }

        });
    }

    private void getCurrentUserInformation() {
        Long currentUserId = Model.getInstance().getCurrentUser().getId();
        Model.getInstance().getUserById(currentUserId,this::responseForGetCurrentUser);

    }

    private void responseForGetCurrentUser(User user) {
        if(user!=null) {
            currentUser = user;
            getGroupsForUser();
        } else {
            Log.e(TAG,"currentUser Returned Null");
        }


    }

    private void getGroupsForUser() {
        List<Group> userGroup = new ArrayList<>();
        if(currentUser!= null && currentUser.getLeadsGroups()!= null) {
            for(Group group:currentUser.getLeadsGroups() ) {
                userGroup.add(group);
            }
        }
        if(currentUser!= null && currentUser.getMemberOfGroups()!= null) {
            for (Group group : currentUser.getMemberOfGroups()) {
                userGroup.add(group);
            }
        }
        new GetListOfGroupFromListOfGroupWithIDOnly(userGroup,this::responseForGetGroups,true);
    }

    private void responseForGetGroups(List<Group> groups) {
        this.groupsCurrentUserPartOf = groups;
        setupSpinnerToEditGroup();

    }

    private void setupSpinnerToEditGroup() {
        Spinner spinner = (Spinner) findViewById(R.id.selectEditGroup);

        List<String> groupDetail = new ArrayList<>();
        if(groupsCurrentUserPartOf !=null){
            for(Group group: groupsCurrentUserPartOf){
                Log.w(TAG,"groupDetail.add(Group.getGroupDescription)" +  group.getGroupDescription());
                if(group.getGroupDescription() != null) {
                    groupDetail.add(group.getGroupDescription());
                }
            }

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.groups_details, groupDetail);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groupSelected = groupsCurrentUserPartOf.get(i) ;
                TextView groupDetailTextView = (TextView) findViewById(R.id.groupDeatails);
                groupDetailTextView.setText(groupDetail.get(i));
                Log.w(TAG, "item Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TextView groupDetailTextView = (TextView) findViewById(R.id.groupDeatails);
                groupDetailTextView.setText("");
            }
        });

    }
}
