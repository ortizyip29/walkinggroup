package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.List;

public class MessagePanelActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<Message> unreadMessages;
    String unread = "unread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_panel);

        model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

        setupBtnNewMsg();
        setupBtnOldMsg();
        setupBtnSendMsg();

    }

    private void responseGetUserUnreadMessages(List<Message> messages) {
        unreadMessages = messages;
        //Toast.makeText(MessagePanelActivity.this, ""+unreadMessages.size(),Toast.LENGTH_LONG).show();

        TextView txtNumNewMsg = (TextView) findViewById(R.id.txtNumNewMsg);
        txtNumNewMsg.setText("You have " + unreadMessages.size() + " unread messages!"  );

    }

    private void setupBtnNewMsg() {
        Button btnNewMsg = (Button) findViewById(R.id.btnNewMsg);
        btnNewMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagePanelActivity.this,ViewNewMessagesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupBtnOldMsg() {
        Button btnOldMsg = (Button) findViewById(R.id.btnOldMsg);
        btnOldMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagePanelActivity.this,ViewOldMessagesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupBtnSendMsg() {
        Button btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagePanelActivity.this,SendMessageActivity.class);
                startActivity(intent);
            }
        });
    }



}
