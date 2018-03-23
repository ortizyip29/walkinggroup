package com.example.junhosung.aquagroupwalkingapp.UI;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

public class SendMsgToGroupActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private Long urmomGroupId = Long.valueOf(11);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg_to_group);

        Button btnSendMsgGroup = (Button) findViewById(R.id.btnSendMsgGroup);
        btnSendMsgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.txtSendMsgGroup);
                String msgBody = text.getText().toString();

                Message msg = new Message(msgBody,false);

                model.newMsgToGroup(urmomGroupId,msg,this::responseNewMsgToGroup);

                finish();

            }

            private void responseNewMsgToGroup(Message msg) {
                //
            }

        });





    }
}
