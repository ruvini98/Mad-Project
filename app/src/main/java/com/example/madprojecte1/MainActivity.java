package com.example.madprojecte1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button butStudents;
    Button butAttendance;
    Button butFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butStudents = findViewById(R.id.btnStudents);
        butAttendance = findViewById(R.id.btnAttendance);
        butFees = findViewById(R.id.btnFees);

        butStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,StudentOptionsActivity.class);
                startActivity(intent);
            }
        });

        butAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AttendanceActivity2.class);
                startActivity(intent);
            }
        });

        butFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CourseFeesActivity.class);
                startActivity(intent);
            }
        });

    }



}