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
import com.google.firebase.database.annotations.NotNull;

public class StudentMain extends AppCompatActivity {

    EditText txtID, txtName, txtEmail, txtContactNo, txtSubject, txtFeedback;
    Button butSubmit, butShow, butUpdate, butDelete;
    DatabaseReference dbRef;
    Student std;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentmain);

        Intent intent = getIntent();

        txtID = findViewById(R.id.EtInputID);
        txtName = findViewById(R.id.EtInputName);
        txtEmail = findViewById(R.id.EtInputEmail);
        txtContactNo = findViewById(R.id.EtInputConNo);
        txtSubject = findViewById(R.id.EtInputSubject);
        txtFeedback = findViewById(R.id.EtInputFeedback);

        butSubmit = findViewById(R.id.ButSubmit);
        butShow = findViewById(R.id.ButShow);
        butUpdate = findViewById(R.id.ButUpdate);
        butDelete = findViewById(R.id.ButDelete);

        std = new Student();

        //submit button
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Student");
                try {
                    if (TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty ID", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Email", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Contact Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtSubject.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Subject", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtFeedback.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Feedback", Toast.LENGTH_SHORT).show();
                    else {
                        std.setId(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setEmail(txtEmail.getText().toString().trim());
                        std.setContactNo(Integer.parseInt(txtContactNo.getText().toString().trim()));
                        std.setSubject(txtSubject.getText().toString().trim());
                        std.setFeedback(txtFeedback.getText().toString().trim());
                        dbRef.child("01").setValue(std);
                        Toast.makeText(getApplicationContext(), "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }

            }});


        //show button

        butShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbRef = FirebaseDatabase.getInstance().getReference().child("Student/01");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            txtID.setText(dataSnapshot.child("id").getValue().toString());
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtEmail.setText(dataSnapshot.child("email").getValue().toString());
                            txtContactNo.setText(dataSnapshot.child("contactNo").getValue().toString());
                            txtSubject.setText(dataSnapshot.child("subject").getValue().toString());
                            txtFeedback.setText(dataSnapshot.child("feedback").getValue().toString());
                        } else
                            Toast.makeText(getApplicationContext(), "Cannot find Feedback", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                    }
                });


                //update button
                butUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
                        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("01")) {
                                    try {
                                        std.setId(txtID.getText().toString().trim());
                                        std.setName(txtName.getText().toString().trim());
                                        std.setEmail(txtEmail.getText().toString().trim());
                                        std.setContactNo(Integer.parseInt(txtContactNo.getText().toString().trim()));
                                        std.setSubject(txtSubject.getText().toString().trim());
                                        std.setFeedback(txtFeedback.getText().toString().trim());

                                        dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("01");
                                        dbRef.setValue(std);
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
        txtID.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtContactNo.setText("");
        txtSubject.setText("");
        txtFeedback.setText("");




        //delete button

        butDelete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("01")) {
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("01");
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




















