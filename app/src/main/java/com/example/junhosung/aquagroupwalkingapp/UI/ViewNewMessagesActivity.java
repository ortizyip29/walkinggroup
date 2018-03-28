package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.GetListOfUserFromListOfID;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class ViewNewMessagesActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<Message> unreadMessages;
    private String unread = "unread";
    private String[] text;
    private User currentUser = model.getCurrentUser();
    private List<User> listOfUserIdOnly = new ArrayList<>();
    private List<User> listOfUser = new ArrayList<>();
    private String[] fromUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_new_messages);

        model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

    }

    private void responseGetUserUnreadMessages(List<Message> messages) {
        unreadMessages = messages;
        text = new String[unreadMessages.size()];
        for (int i = 0; i < unreadMessages.size(); i ++) {
            //fromUserId = unreadMessages.get(i).getFromUser().getId();
            text[i] = unreadMessages.get(i).getText();
            listOfUserIdOnly.add(unreadMessages.get(i).getFromUser());

        }

        //Toast.makeText(ViewNewMessagesActivity.this,listOfUserIdOnly.size()+"",Toast.LENGTH_LONG).show();

        GetListOfUserFromListOfID getListOfUserFromListOfID = new GetListOfUserFromListOfID(listOfUserIdOnly,this::responseToGetListOfUserFull,
                true, true);

    }

    private void responseToGetListOfUserFull(List<User> fullUsers) {
        listOfUser = fullUsers;

        //Toast.makeText(ViewNewMessagesActivity.this,""+ listOfUser.size(),Toast.LENGTH_LONG).show();

        fromUserName = new  String[listOfUser.size()];

        for (int i = 0; i < listOfUser.size(); i++) {
            fromUserName[i] = "From " + listOfUser.get(i).getEmail() + " : " + text[i];
        }

        populateListView();

    }



    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                fromUserName);

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
                                            text = new String[unreadMessages.size()];

                                            listOfUserIdOnly = new ArrayList<>();

                                            for (int i = 0; i < unreadMessages.size(); i ++) {
                                                text[i] = unreadMessages.get(i).getText();
                                                listOfUserIdOnly.add(unreadMessages.get(i).getFromUser());

                                            }

                                            //Toast.makeText(ViewNewMessagesActivity.this,listOfUserIdOnly.size()+"",Toast.LENGTH_LONG).show();

                                            GetListOfUserFromListOfID getListOfUserFromListOfID = new GetListOfUserFromListOfID(listOfUserIdOnly,
                                                    this::responseToGetListOfUserFull,
                                                    true, true);

                                        }

                                        private void responseToGetListOfUserFull(List<User> fullUsers) {
                                            listOfUser = fullUsers;

                                             //Toast.makeText(ViewNewMessagesActivity.this,""+ listOfUser.size(),Toast.LENGTH_LONG).show();

                                            fromUserName = new  String[listOfUser.size()];

                                            Toast.makeText(ViewNewMessagesActivity.this,listOfUser.size()+"",Toast.LENGTH_LONG).show();
                                            Toast.makeText(ViewNewMessagesActivity.this,text.length+"",Toast.LENGTH_LONG).show();

                                             for (int i = 0; i < listOfUser.size(); i++) {
                                                    fromUserName[i] = "From " + listOfUser.get(i).getEmail() + " : " + text[i];
                                                }

                                            populateListView();

                                            Toast.makeText(ViewNewMessagesActivity.this,"Moved to read messages!",Toast.LENGTH_LONG).show();

                                        }

                                    }

        );


    }




}
