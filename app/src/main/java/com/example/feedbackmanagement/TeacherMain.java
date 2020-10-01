package com.example.feedbackmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class TeacherMain extends AppCompatActivity {

    EditText txt1ID, txt1Name, txt1Email, txt1ContactNo, txt1Subject, txt1Feedback;
    Button buttonSubmit,buttonShow,buttonUpdate,buttonDelete;
    DatabaseReference dbRef;
    Teacher tch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachermain);

        Intent intent = getIntent();

        txt1ID = findViewById(R.id.InputID);
        txt1Name = findViewById(R.id.InputName);
        txt1Email = findViewById(R.id.InputEmail);
        txt1ContactNo = findViewById(R.id.InputConNo);
        txt1Subject = findViewById(R.id.InputSubject);
        txt1Feedback = findViewById(R.id.InputFeedback);

        buttonSubmit = findViewById(R.id.ButtonSubmit);
        buttonShow = findViewById(R.id.ButtonShow);
        buttonUpdate = findViewById(R.id.ButtonUpdate);
        buttonDelete = findViewById(R.id.ButtonDelete);


        tch = new Teacher();



