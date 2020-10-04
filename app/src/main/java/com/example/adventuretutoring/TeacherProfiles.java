package com.example.adventuretutoring;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherProfiles extends AppCompatActivity {

    EditText teachername, dob,password, nic,phone;
    Button btnAdd, btnShow, btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Teachers teachers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profiles);

        teachername = findViewById(R.id.teachername);
        dob = findViewById(R.id.dob);
        password = findViewById(R.id.password);
        nic = findViewById(R.id.nic);
        phone = findViewById(R.id.phone);

        btnAdd = findViewById(R.id.add);
        btnShow = findViewById(R.id.show);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        teachers = new Teachers();

        //add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                try {
                    if (TextUtils.isEmpty(teachername.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter the Name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(dob.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter Date of Birth",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(password.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the password",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(nic.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please Enter NIC Number",Toast.LENGTH_SHORT).show();
                    else{
                        teachers.setTeacherName(teachername.getText().toString().trim());
                        teachers.setDateOfBirth(dob.getText().toString().trim());
                        teachers.setPassword(password.getText().toString().trim());
                        teachers.setNIC(nic.getText().toString().trim());
                        teachers.setConNo(Integer.parseInt(phone.getText().toString().trim()));
                        dbRef.child("01").setValue(teachers);
                       //dbRef.push().setValue(teachers);

                        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_SHORT).show();

                    }

                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Teachers/01");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            teachername.setText(dataSnapshot.child("TeacherName").getValue().toString());
                            dob.setText(dataSnapshot.child("DateOfBirth").getValue().toString());
                            password.setText(dataSnapshot.child("Password").getValue().toString());
                            nic.setText(dataSnapshot.child("NIC").getValue().toString());
                            phone.setText(dataSnapshot.child("conNo").getValue().toString());

                        } else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}

