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
    private List<Message> readMessages;
    private String unread = "read";
    private String[] content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_view_old_messages);

        model.getUserReadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

    }

    private void responseGetUserUnreadMessages(List<Message> messages) {
        readMessages = messages;
        content = new String[readMessages.size()];
        for (int i = 0; i < readMessages.size(); i ++) {
            content[i] = readMessages.get(i).getText();
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
