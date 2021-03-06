package com.example.junhosung.aquagroupwalkingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.GpsLocation;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.Rewards;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.List;

public class EditAccountActivity extends AppCompatActivity {
    Model model = Model.getInstance();
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

    User current = model.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_edit_account);
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
        updateUI(current);
        setUpCancelbtn();
        setUpDonebtn();

    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

        if(birthM != 0){
            birthMEdit.setText(String.valueOf(birthM), TextView.BufferType.EDITABLE);
        }else{
            birthMEdit.setText("",TextView.BufferType.EDITABLE);
        }
        if(birthY != 0) {
            birthYEdit.setText(String.valueOf(birthY), TextView.BufferType.EDITABLE);
        }else{
            birthYEdit.setText("",TextView.BufferType.EDITABLE);
        }


        nameEdit.setText(name, BufferType.EDITABLE);
        addressEdit.setText(address, BufferType.EDITABLE);
        homePEdit.setText(phone, BufferType.EDITABLE);
        mobileEdit.setText(mobile, BufferType.EDITABLE);
        emailEdit.setText(String.valueOf(email), BufferType.EDITABLE);
        gradeEdit.setText(grade, BufferType.EDITABLE);
        teacherNEdit.setText(teacherN, BufferType.EDITABLE);
        emergencyEdit.setText(emergencyContact, BufferType.EDITABLE);
    }

    private void setUpDonebtn() {
        Button btn = (Button) findViewById(R.id.btnDone);
        btn.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = nameEdit.getText().toString();
                int birthM;
                if(birthMEdit.getText().toString().matches("")){
                    birthM = 0;
                }else{
                    birthM = Integer.parseInt(birthMEdit.getText().toString());
                }
                int birthY;
                if(birthYEdit.getText().toString().matches("")){
                    birthY = 0;
                }else{
                    birthY = Integer.parseInt(birthYEdit.getText().toString());
                }

                String address = addressEdit.getText().toString();
                String homeP = homePEdit.getText().toString();
                String mobile = mobileEdit.getText().toString();
                String email = emailEdit.getText().toString();

                if(isEmailValid(email) != true) {
                    Toast.makeText(EditAccountActivity.this, "Email not valid", Toast.LENGTH_LONG).show();
                    return;
                }

                String grade = gradeEdit.getText().toString();
                String teacherN = teacherNEdit.getText().toString();
                String emergency = emergencyEdit.getText().toString();



                //model.editUser(name, birthY, birthM, address, homeP, mobile, email, grade, teacherN, emergency);


                current.setName(name);
                current.setBirthMonth(birthM);
                current.setBirthYear(birthY);
                current.setAddress(address);
                current.setHomePhone(homeP);
                current.setCellPhone(mobile);
                current.setEmail(email);
                current.setGrade(grade);
                current.setTeacherName(teacherN);
                current.setEmergencyContactInfo(emergency);
                //current.setTotalPointsEarned(900);
                //current.setCurrentPoints(900);
                model.updateUser(current, this::getUserUpdateCallBack);
                finish();

            }
            private void getUserUpdateCallBack(User user){}
        });
    }

    private void setUpCancelbtn() {
        Button btn = (Button) findViewById(R.id.btnCancel);
        btn.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EditAccountActivity.class);
    }
}
