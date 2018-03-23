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

public class SendMsgToParentsActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private Long urmomGroupId = Long.valueOf(11);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg_to_parent);

        Button btnSendMsgGroup = (Button) findViewById(R.id.btnSendMsgParent);
        btnSendMsgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText) findViewById(R.id.txtSendMsgParent);
                String msgBody = text.getText().toString();

                Message msg = new Message();
                msg.setText(msgBody);
                msg.setEmergency(false);

                model.newMsgToParents(urmomGroupId,msg,this::responseNewMsgToParents);

                finish();

            }

            private void responseNewMsgToParents(Message msg) {
                //
            }



        });




    }
}
