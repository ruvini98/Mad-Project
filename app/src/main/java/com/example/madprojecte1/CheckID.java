package com.example.madprojecte1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CheckID extends AppCompatActivity {

    private static final String TAG = "CheckID";
    Button btnAssign;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_i_d);

        btnAssign= findViewById(R.id.btnCheck);

        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckID.this,StudentProfileActivity.class);
                EditText editText = (EditText) findViewById(R.id.editTextEnterID);
                String id = editText.getText().toString();
                intent.putExtra(userID,id);
                startActivity(intent);
            }
        });
    }
}