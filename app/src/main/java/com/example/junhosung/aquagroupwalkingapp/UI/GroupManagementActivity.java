package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junhosung.aquagroupwalkingapp.R;

import org.w3c.dom.Text;

public class GroupManagementActivity extends AppCompatActivity {

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
        Button changeGroupButton = (Button)findViewById(R.id.createGroupBtn);
        changeGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
    private void setupModifyGroupButton(){
        Button modifyGroupButton = (Button)findViewById(R.id.modGroups);
        modifyGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    // remove tempMembers later
    private void populateListView() {
        String[] tempMembers = {"joe","big daddy","small daddy","yipper","teddy","Nana"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.members,tempMembers);
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
}
