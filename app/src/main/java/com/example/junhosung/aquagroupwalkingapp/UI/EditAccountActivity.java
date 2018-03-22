package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

public class EditAccountActivity extends AppCompatActivity {
    Model model = Model.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        setUpCancelbtn();
        setUpDonebtn();
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void setUpDonebtn() {
        Button btn = (Button) findViewById(R.id.btnDone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEdit = (EditText) findViewById(R.id.editName);
                EditText birthMEdit = (EditText) findViewById(R.id.editBirthM);
                EditText birthYEdit = (EditText) findViewById(R.id.editBirthY);
                EditText addressEdit = (EditText) findViewById(R.id.editAddress);
                EditText homePEdit = (EditText) findViewById(R.id.editHome);
                EditText mobileEdit = (EditText) findViewById(R.id.editMobile);
                EditText emailEdit = (EditText) findViewById(R.id.editEmail);
                EditText gradeEdit = (EditText) findViewById(R.id.editGrade);
                EditText teacherNEdit = (EditText) findViewById(R.id.editTeacherName);
                EditText eNameEdit = (EditText) findViewById(R.id.editEName);
                EditText eEmailEdit = (EditText) findViewById(R.id.editEEmail);
                EditText ePhoneEdit = (EditText) findViewById(R.id.editEPhone);

                String name = nameEdit.getText().toString();
                int birthM = Integer.parseInt(birthMEdit.getText().toString());
                if(birthMEdit.getText().toString().matches("")){
                    birthM = 0;
                }
                int birthY = Integer.parseInt(birthYEdit.getText().toString());
                if(birthYEdit.getText().toString().matches("")){
                    birthY = 0;
                }

                String address = addressEdit.getText().toString();
                String homeP = homePEdit.getText().toString();
                String mobile = mobileEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String grade = gradeEdit.getText().toString();
                String teacherN = teacherNEdit.getText().toString();
                String eName = eNameEdit.getText().toString();
                String eEmail = eEmailEdit.getText().toString();
                String ePhone = ePhoneEdit.getText().toString();



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
}
