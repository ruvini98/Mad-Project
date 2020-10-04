package com.example.madprojecte1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentOptionsActivity extends AppCompatActivity {

    Button butNewAdmission;
    Button butStudentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_options);

        butNewAdmission = findViewById(R.id.btnNewAdmission);
        butStudentProfile = findViewById(R.id.btnStudentProfile);

        butNewAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentOptionsActivity.this,NewAdmissionActivity.class);
                startActivity(intent);
            }
        });

        butStudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentOptionsActivity.this,CheckID.class);
                startActivity(intent);
            }
        });

    }
}