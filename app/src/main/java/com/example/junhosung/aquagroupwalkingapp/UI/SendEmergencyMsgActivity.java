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

public class SendEmergencyMsgActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private User currentUser;
    private String emergencyMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_emergency_msg);

        emergencyMessage = "This is an emergency message!";
        Message msgEmergency = new Message();
        msgEmergency.setText(emergencyMessage);
        msgEmergency.setEmergency(true);
        currentUser = model.getCurrentUser();


        if (currentUser.getMonitoredByUsers().isEmpty()) {
            Toast.makeText(this,"You don't have any 'parents' ... currently ... :( ",Toast.LENGTH_LONG).show();
            finish();
        }

        else {
            model.newMsgToParents(model.getCurrentUser().getId(),msgEmergency,this::responseToNewMsgToParents);
        }


    }

    private void responseToNewMsgToParents(Message msg) {
        setupBtnSendEmergency();

    }



    private void setupBtnSendEmergency() {


        Button btnSendEmergency = (Button) findViewById(R.id.btnMsgEmergency);
        btnSendEmergency.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText tempText = (EditText) findViewById(R.id.txtMsgEmergency);
                String optionalText = tempText.getText().toString();

                Message msgEmergencyOptional = new Message(optionalText,true);
                model.newMsgToParents(currentUser.getId(),msgEmergencyOptional,this::responseToOptionalMsg);
                finish();

            }

            private void responseToOptionalMsg(Message msg) {
                //
            }

        });


    }


}
