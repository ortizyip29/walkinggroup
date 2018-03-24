package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Message;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

public class SendEmergencyMsgActivity extends AppCompatActivity {


    private Model model = Model.getInstance();
    private Long randomUser = Long.valueOf(132);
    private String emergencyMessage = "This is an emergency message!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_emergency_msg);

        Message msgEmergency = new Message();
        msgEmergency.setText(emergencyMessage);
        msgEmergency.setEmergency(true);

        model.newMsgToParents(model.getCurrentUser().getId(),msgEmergency,this::responseToNewMsgToParents);


    }

    private void responseToNewMsgToParents(Message msg) {
        EditText tempText = (EditText) findViewById(R.id.txtMsgEmergency);
        String optionalText = tempText.getText().toString();

        Message msgEmergencyOptional = new Message();
        msgEmergencyOptional.setText(optionalText);
        msgEmergencyOptional.setEmergency(true);

        model.newMsgToParents(model.getCurrentUser().getId(),msgEmergencyOptional,this::responseToOptionalMsg);

    }

    private void responseToOptionalMsg(Message msg) {
        //
    }


}
