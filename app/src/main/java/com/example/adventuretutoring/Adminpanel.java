package com.example.adventuretutoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Adminpanel extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        button = (Button) findViewById(R.id.teacher); //teacher registration button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTeacherProfiles();

            }
        });
    }

    public void openTeacherProfiles() {
        Intent intent = new Intent(this,TeacherProfiles.class);
        startActivity(intent);
    }
}