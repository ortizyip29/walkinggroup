package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.List;

public class ChooseWhichGroupMsgActivity extends AppCompatActivity {

    Model model = Model.getInstance();
    List<Group> userIsLeaderOf = model.getCurrentUser().getLeadsGroups();
    Long [] groupIdList;
    TextView selectGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_choose_which_group_msg);

        selectGroup = (TextView) findViewById(R.id.txtChooseGroupMsg);
        selectGroup.setText(R.string.select_group_to_msg);



        if (userIsLeaderOf.isEmpty()) {
            Toast.makeText(this,"you are not leading any groups!",Toast.LENGTH_LONG).show();
            finish();
        }

        else {

            groupIdList = new Long [userIsLeaderOf.size()];
            for (int i = 0; i < userIsLeaderOf.size();i++) {
                groupIdList[i] = userIsLeaderOf.get(i).getId();
            }

            populateListView();

        }

    }

    private void populateListView() {
        //String[] myItems = {"Blue"};

        ArrayAdapter<Long> adapter = new ArrayAdapter<Long>(this,
                R.layout.see_monitoring,
                groupIdList);

        ListView list = (ListView) findViewById(R.id.listChooseGroup);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(ChooseWhichGroupMsgActivity.this,SendMsgToGroupActivity.class);

                                            Group group = userIsLeaderOf.get(i);
                                            Long groupId = group.getId();

                                            intent.putExtra("groupId",groupId);

                                            startActivity(intent);

                                        }
                                    }
        );
    }




}
