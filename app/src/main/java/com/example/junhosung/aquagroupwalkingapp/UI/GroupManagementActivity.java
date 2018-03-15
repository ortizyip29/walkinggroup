package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.junhosung.aquagroupwalkingapp.UI.RegisterActivity.makeIntent;

public class GroupManagementActivity extends AppCompatActivity {
    Model model = Model.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);
        TextView textViewGroups = (TextView)findViewById(R.id.textViewGroupName);
        TextView textViewMemberNames= (TextView)findViewById(R.id.textViewMember);
        setupChangeGroupButton();
        setupModifyGroupButton();
        setupAddGroupButton();
        populateListView();
        backToMapsButton();
    }



    private void setupChangeGroupButton(){
        Button changeGroupButton = (Button)findViewById(R.id.changeGroups);
        changeGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupManagementActivity.this,ChangeGroupActivity.class);
                startActivityForResult(intent,1);

            }
        });
    }
    private void setupModifyGroupButton(){
        Button modifyGroupButton = (Button)findViewById(R.id.modGroups);
        modifyGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // refreshPage();
                Intent intent = GroupModifyActivity.makeIntent(GroupManagementActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupAddGroupButton(){
        Button addGroupButton = (Button)findViewById(R.id.createGroupBtn);
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupManagementActivity.this,CreateNewGroupActivity.class );
                startActivity(intent);

            }
        });

    }

    private void populateListView() {
        model.getGroups(this::responseForGetGroups);
    }

    private void responseForGetGroups(List<Group> groups) {
        refreshPage();

    }

    private void refreshPage() {
        List<String> members = new ArrayList<>();
        for(User user:model.getCurrentGroupInUseByUser().getMemberUsers()){
            members.add(user.getName()+" , " +user.getEmail());
        }
        ((TextView) findViewById(R.id.textViewGroupName)).setText(model.getCurrentGroupInUseByUser().getGroupDescription());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.members,members);
        ListView memberListView = (ListView) findViewById(R.id.membersList);
        memberListView.setAdapter(adapter);
        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    private void backToMapsButton(){
        Button  addbackToMapsButton= (Button)findViewById(R.id.backToMapBtn);
        addbackToMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    refreshPage();
                }
        }
    }

}
