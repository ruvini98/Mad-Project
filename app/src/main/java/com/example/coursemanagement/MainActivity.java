package com.example.coursemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnBio;
    ImageButton btnMaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBio = findViewById(R.id.btn_bio);

        btnBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
                MainActivity.this.startActivity(myIntent);
            }
        });



        btnMaths = findViewById(R.id.btn_maths);

        btnMaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, MainActivity3.class);
                MainActivity.this.startActivity(myIntent);
            }
        });



    }




}