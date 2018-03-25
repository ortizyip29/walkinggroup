package com.example.junhosung.aquagroupwalkingapp.UI;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

public class SendMsgToGroupActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private Long urmomGroupId = Long.valueOf(10);
    private User currentUser = model.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg_to_group);

        Intent receive = getIntent();
        Long selectedGroupId = receive.getLongExtra("groupId",0);

        Button btnSendMsgGroup = (Button) findViewById(R.id.btnSendMsgGroup);
        btnSendMsgGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.txtSendMsgGroup);
                String msgBody = text.getText().toString();

                Message msg = new Message();
                msg.setText(msgBody);
                msg.setEmergency(false);

                model.newMsgToGroup(selectedGroupId,msg,this::responseNewMsgToGroup);

                finish();

            }

            private void responseNewMsgToGroup(Message msg) {
                //
            }

        });





    }
}
