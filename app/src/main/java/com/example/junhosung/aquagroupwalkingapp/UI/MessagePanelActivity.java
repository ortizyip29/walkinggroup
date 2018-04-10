package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.List;

public class MessagePanelActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<Message> unreadMessages;
    String unread = "unread";
    private CountDownTimer timer;
    private boolean isOnCreateDone = false;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (isOnCreateDone) {

            model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_message_panel);

        setupBtnNewMsg();
        setupBtnOldMsg();
        setupBtnPermissions();
        setupBtnAllPermissions();
        

        model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

        isOnCreateDone = true;

        timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {

                //

            }

            @Override
            public void onFinish() {
                model.getUserUnreadMessages(model.getCurrentUser().getId(),unread,this::responseGetUserUnreadMessages);

                setupBtnNewMsg();
                setupBtnOldMsg();

                start();

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

        TextView txtNumNewMsg = (TextView) findViewById(R.id.txtNumNewMsg);
        txtNumNewMsg.setText("You have " + unreadMessages.size() + " unread messages!"  );

    }

    private void setupBtnNewMsg() {
        Button btnNewMsg = (Button) findViewById(R.id.btnNewMsg);
        btnNewMsg.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
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
        btnOldMsg.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btnOldMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagePanelActivity.this,ViewOldMessagesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupBtnPermissions() {
        Button btnPermission = (Button) findViewById(R.id.btnPermission);
        btnPermission.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagePanelActivity.this, ViewPendingPermissionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupBtnAllPermissions() {
        Button btnPermission = (Button) findViewById(R.id.btnAllPermission);
        btnPermission.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessagePanelActivity.this, ViewAllPermissionsActivity.class);
                startActivity(intent);
            }
        });
    }

}
