package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

public class EditChildActivity extends AppCompatActivity {
    Model model = Model.getInstance();
    private static final String USER_ID = "1234";
    private Long CHILD_ID;
    EditText nameEdit;
    EditText birthMEdit;
    EditText birthYEdit;
    EditText addressEdit;
    EditText homePEdit;
    EditText mobileEdit;
    EditText emailEdit;
    EditText gradeEdit;
    EditText teacherNEdit;
    EditText emergencyEdit;

    User child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        extractDataFromIntent();

        nameEdit = (EditText) findViewById(R.id.editName);
        birthMEdit = (EditText) findViewById(R.id.editBirthM);
        birthYEdit = (EditText) findViewById(R.id.editBirthY);
        addressEdit = (EditText) findViewById(R.id.editAddress);
        homePEdit = (EditText) findViewById(R.id.editHome);
        mobileEdit = (EditText) findViewById(R.id.editMobile);
        emailEdit = (EditText) findViewById(R.id.editEmail);
        gradeEdit = (EditText) findViewById(R.id.editGrade);
        teacherNEdit = (EditText) findViewById(R.id.editTeacherName);
        emergencyEdit = (EditText) findViewById(R.id.editEmergency);

        setUpCancelbtn();
        setUpDonebtn();
    }




    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void extractDataFromIntent() {

        Intent intent = getIntent();
        if(intent != null) {
            CHILD_ID = intent.getLongExtra(USER_ID, 1);
            model.getUserById(this.CHILD_ID, this::getUserCallBack);

        }
    }

    private void getUserCallBack(User user){
        child = user;
        updateUI(child);
    }


    private void updateUI(User user) {

        String email = user.getEmail();
        String address = user.getAddress();
        String name = user.getName();
        int birthM = user.getBirthMonth();
        int birthY = user.getBirthYear();
        String phone = user.getHomePhone();
        String mobile = user.getCellPhone();
        String grade = user.getGrade();
        String teacherN = user.getTeacherName();
        String emergencyContact = user.getEmergencyContactInfo();

        nameEdit.setText(name, TextView.BufferType.EDITABLE);
        birthMEdit.setText(String.valueOf(birthM), TextView.BufferType.EDITABLE);
        birthYEdit.setText(String.valueOf(birthY), TextView.BufferType.EDITABLE);
        addressEdit.setText(address, TextView.BufferType.EDITABLE);
        homePEdit.setText(phone, TextView.BufferType.EDITABLE);
        mobileEdit.setText(mobile, TextView.BufferType.EDITABLE);
        emailEdit.setText(String.valueOf(email), TextView.BufferType.EDITABLE);
        gradeEdit.setText(grade, TextView.BufferType.EDITABLE);
        teacherNEdit.setText(teacherN, TextView.BufferType.EDITABLE);
        emergencyEdit.setText(emergencyContact, TextView.BufferType.EDITABLE);

    }

    private void setUpDonebtn() {
        Button btn = (Button) findViewById(R.id.btnDone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = nameEdit.getText().toString();
                int birthM;
                if(birthMEdit.getText().toString().matches("")){
                    //
                }else{
                    birthM = Integer.parseInt(birthMEdit.getText().toString());
                    child.setBirthMonth(birthM);
                }
                int birthY;
                if(birthYEdit.getText().toString().matches("")){
                    //
                }else{
                    birthY = Integer.parseInt(birthYEdit.getText().toString());
                    child.setBirthYear(birthY);
                }

                String address = addressEdit.getText().toString();
                String homeP = homePEdit.getText().toString();
                String mobile = mobileEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String grade = gradeEdit.getText().toString();
                String teacherN = teacherNEdit.getText().toString();
                String emergency = emergencyEdit.getText().toString();

                child.setName(name);
                child.setAddress(address);
                child.setHomePhone(homeP);
                child.setCellPhone(mobile);
                child.setEmail(email);
                child.setGrade(grade);
                child.setTeacherName(teacherN);
                child.setEmergencyContactInfo(emergency);

                model.updateUser(child, this::getUpdatedUserBack);
            }
            private void getUpdatedUserBack(User user){
                finish();
            }
        });
    }

    private void setUpCancelbtn() {
        Button btn = (Button) findViewById(R.id.btnCancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static Intent makeIntent(Context context, Long userId) {
       Intent intent = new Intent(context, EditChildActivity.class);
       intent.putExtra(USER_ID, userId);
       return intent;

    }


}


