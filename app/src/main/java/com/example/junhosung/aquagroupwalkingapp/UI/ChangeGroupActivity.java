package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.ArrayList;
import java.util.List;

public class ChangeGroupActivity extends AppCompatActivity {
    Model model = Model.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_group);
        setupBackBtn();
        setupListViewToDisplayGroups();
    }


    private void setupBackBtn() {
    Button btn = (Button) findViewById(R.id.btnBackFromChangeGroup);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }
    private void setupListViewToDisplayGroups() {

        model.getGroups(this::responseForGettingGroups);
    }

    private void responseForGettingGroups(List<Group> groups){
        List<String> groupsDisplay = new ArrayList<>();
        for(Group group:groups){
            groupsDisplay.add(group.getId()+ " , " + group.getGroupDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.display_groups,groupsDisplay);
        ListView groupListView = (ListView) findViewById(R.id.listViewForChangeGroup);
        groupListView.setAdapter(adapter);
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model.setCurrentGroupInUseByUser(groups.get(i));

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });
    }

}
