package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.List;

public class ViewOldMessagesActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private List<Message> ReadMessages;
    private String unread = "read";
    private String[] content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_old_messages);

        model.getUserReadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

    }

    private void responseGetUserUnreadMessages(List<Message> messages) {
        ReadMessages = messages;
        content = new String[ReadMessages.size()];
        for (int i = 0; i < ReadMessages.size(); i ++) {
            content[i] = ReadMessages.get(i).getText();
        }

        populateListView();

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                content);

        ListView list = (ListView) findViewById(R.id.listOldMsg);
        list.setAdapter(adapter);

    }




}
