package com.example.feedbackmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openStudent(View view){
        Intent intent = new Intent(this, StudentMain.class);

        startActivity(intent);
    }

    public void openTeacher(View view) {
        Intent intent = new Intent(this, TeacherMain.class);

        startActivity(intent);
    }

    public void openTotal(View view) {
        Intent intent = new Intent(this, Total.class);

        startActivity(intent);
    }

}