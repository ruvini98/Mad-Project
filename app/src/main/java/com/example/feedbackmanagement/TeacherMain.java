package com.example.feedbackmanagement;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.annotations.NotNull;public class TeacherMain extends AppCompatActivity {

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

        //submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                try {
                    if (TextUtils.isEmpty(txt1ID.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty ID", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txt1Name.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txt1Email.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Email", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txt1ContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Contact Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txt1Subject.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Subject", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txt1Feedback.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Feedback", Toast.LENGTH_SHORT).show();
                    else {
                        tch.setId(txt1ID.getText().toString().trim());
                        tch.setName(txt1Name.getText().toString().trim());
                        tch.setEmail(txt1Email.getText().toString().trim());
                        tch.setContactNo(Integer.parseInt(txt1ContactNo.getText().toString().trim()));
                        tch.setSubject(txt1Subject.getText().toString().trim());
                        tch.setFeedback(txt1Feedback.getText().toString().trim());
                        dbRef.child("01").setValue(tch);
                        Toast.makeText(getApplicationContext(), "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }

            }});


        //show button

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher/01");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            txt1ID.setText(dataSnapshot.child("id").getValue().toString());
                            txt1Name.setText(dataSnapshot.child("name").getValue().toString());
                            txt1Email.setText(dataSnapshot.child("email").getValue().toString());
                            txt1ContactNo.setText(dataSnapshot.child("contactNo").getValue().toString());
                            txt1Subject.setText(dataSnapshot.child("subject").getValue().toString());
                            txt1Feedback.setText(dataSnapshot.child("feedback").getValue().toString());
                        } else
                            Toast.makeText(getApplicationContext(), "Cannot find Feedback", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                    }
                });


                //update button
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("01")) {
                                    try {
                                        tch.setId(txt1ID.getText().toString().trim());
                                        tch.setName(txt1Name.getText().toString().trim());
                                        tch.setEmail(txt1Email.getText().toString().trim());
                                        tch.setContactNo(Integer.parseInt(txt1ContactNo.getText().toString().trim()));
                                        tch.setSubject(txt1Subject.getText().toString().trim());
                                        tch.setFeedback(txt1Feedback.getText().toString().trim());

                                        dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher").child("01");
                                        dbRef.setValue(tch);
                                        clearControls();
                                        Toast.makeText(getApplicationContext(), "Feedback Updated Successfully", Toast.LENGTH_SHORT).show();

                                    }
                                    catch (NumberFormatException e) {
                                        Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "No Feedback to Update", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });


            }
        });
    }
    private void clearControls() {
        txt1ID.setText("");
        txt1Name.setText("");
        txt1Email.setText("");
        txt1ContactNo.setText("");
        txt1Subject.setText("");
        txt1Feedback.setText("");




        //delete button

        buttonDelete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("01")) {
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher").child("01");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Feedback Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else


                            Toast.makeText(getApplicationContext(), "No Feedback to Delete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }}







