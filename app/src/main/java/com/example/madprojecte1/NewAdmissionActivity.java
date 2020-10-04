package com.example.madprojecte1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAdmissionActivity extends AppCompatActivity {

    EditText txtID,txtName,txtDate,txtMobile,txtEmail;
    Button butAdd;
    DatabaseReference dbRef;
    Student std;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admission);

        txtID = findViewById(R.id.EtID);
        txtName = findViewById(R.id.EtName);
        txtDate = findViewById(R.id.EtDate);
        txtMobile = findViewById(R.id.EtMobile);
        txtEmail = findViewById(R.id.EtEmail);

        radioGroup = findViewById(R.id.radioGroup);

        butAdd = findViewById(R.id.btnAdd);

        std = new Student();

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Student");

                try {
                    if (TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a Name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtDate.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a birth date",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an Email",Toast.LENGTH_SHORT).show();
                    else {
                        std.setId(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setBirth(txtDate.getText().toString().trim());
                        std.setMob(txtMobile.getText().toString().trim());
                        std.setEmail(txtEmail.getText().toString().trim());

                        //dbRef.push().setValue(std);

                    //   dbRef.child("Std1").setValue(std);
                        dbRef.child(txtID.getText().toString()).setValue(std);
                        Toast.makeText(getApplicationContext(),"Data Saves Successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException nfe){
                    Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Selected " + radioButton.getText(),
                Toast.LENGTH_SHORT).show();

    }

    private void clearControls(){
        txtID.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtMobile.setText("");
        txtEmail.setText("");
    }
}