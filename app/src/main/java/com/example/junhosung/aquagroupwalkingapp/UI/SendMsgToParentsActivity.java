package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

public class SendMsgToParentsActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg_to_parent);

        currentUser = model.getCurrentUser();

        if (currentUser.getMonitoredByUsers().isEmpty()) {
            Toast.makeText(this,"You don't have any 'parents' ... currently ... :( ",Toast.LENGTH_LONG).show();
            finish();
        }

        else {
            setupBtnSendMsgParent();
        }

    }

    private void setupBtnSendMsgParent() {

        Button btnSendMsgParent = (Button) findViewById(R.id.btnSendMsgParent);
        btnSendMsgParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.txtSendMsgParent);
                String msgBody = text.getText().toString();

                Message msg = new Message();
                msg.setText(msgBody);
                msg.setEmergency(false);

                model.newMsgToParents(currentUser.getId(),msg,this::responseNewMsgToParents);

                Toast.makeText(SendMsgToParentsActivity.this,"message sent",Toast.LENGTH_LONG).show();

                finish();

            }

            private void responseNewMsgToParents(Message msg) {
                //
            }

        });

    }


}
