package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.os.CountDownTimer;
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
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_panel);

        timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {

                model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

                setupBtnNewMsg();
                setupBtnOldMsg();

            }

            @Override
            public void onFinish() {
                model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

                setupBtnNewMsg();
                setupBtnOldMsg();

            }

            private void responseGetUserUnreadMessages(List<Message> messages) {
                unreadMessages = messages;

                TextView txtNumNewMsg = (TextView) findViewById(R.id.txtNumNewMsg);
                txtNumNewMsg.setText("You have " + unreadMessages.size() + " unread messages!"  );

            }


        }.start();


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



}
