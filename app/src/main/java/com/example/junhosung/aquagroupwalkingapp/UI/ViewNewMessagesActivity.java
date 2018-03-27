package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;

public class ViewNewMessagesActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<Message> unreadMessages;
    private String unread = "unread";
    private String[] content;
    private User currentUser = model.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_new_messages);

        model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

    }

    private void responseGetUserUnreadMessages(List<Message> messages) {
        unreadMessages = messages;
        content = new String[unreadMessages.size()];
        for (int i = 0; i < unreadMessages.size(); i ++) {
            content[i] = unreadMessages.get(i).getText();
        }

        populateListView();

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                content);

        ListView list = (ListView) findViewById(R.id.listNewMsg);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                            Message clickedMessage = unreadMessages.get(i);
                                            model.msgMarkAsRead(clickedMessage.getId(),currentUser.getId(),true,this::responseForMsgMarkAsRead);

                                        }

                                        private void responseForMsgMarkAsRead(User user) {
                                            model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);
                                        }


                                        private void responseGetUserUnreadMessages(List<Message> messages) {
                                            unreadMessages = messages;
                                            content = new String[unreadMessages.size()];
                                            for (int i = 0; i < unreadMessages.size(); i ++) {
                                                content[i] = unreadMessages.get(i).getText();
                                            }

                                            populateListView();
                                            Toast.makeText(ViewNewMessagesActivity.this,
                                                    "Message read and moved to old messages",Toast.LENGTH_LONG).show();

                                            }
                                    }

        );


    }




}
