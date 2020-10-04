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

public class TotalMain extends AppCompatActivity {

     EditText num1,num2,total;
     Button btnCalculate,btnSave,btnShow,btnDelete;

    DatabaseReference dbRef;
    Total tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        Intent intent = getIntent();

        num1 = findViewById(R.id.EtInputStudent);
        num2 = findViewById(R.id.EtInputTeacher);
        total = findViewById(R.id.EtTotal);

        btnCalculate = findViewById(R.id.ButtonCalculate);
        btnSave = findViewById(R.id.ButtonSave);
        btnShow = findViewById(R.id.BtnShow);
        btnDelete = findViewById(R.id.BtnDelete);

        tot = new Total();

        //CALCULATE BUTTON

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(num1.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Enter Student Value", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(num2.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Enter Teacher Value", Toast.LENGTH_SHORT).show();
                else {

                    int number1 = Integer.parseInt(num1.getText().toString());
                    int number2 = Integer.parseInt(num2.getText().toString());
                    int sum = number1 + number2;

                    total.setText(String.valueOf(sum));
                }
            }

        });

                //SAVE BUTTON

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dbRef = FirebaseDatabase.getInstance().getReference().child("Total");

                        if (TextUtils.isEmpty(num1.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Enter Student Value", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(num2.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Enter Teacher Value", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(total.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Click Calculate Button", Toast.LENGTH_SHORT).show();

                        else {
                            tot.setStudent(Integer.parseInt(num1.getText().toString().trim()));
                            tot.setTeacher(Integer.parseInt(num2.getText().toString().trim()));
                            tot.setTotal(Integer.parseInt(total.getText().toString().trim()));
                            dbRef.child("").setValue(tot);
                            Toast.makeText(getApplicationContext(), "Total Feedback Saved Successfully", Toast.LENGTH_SHORT).show();
                            clearControls();
                        }

                    }


                private void clearControls() {
                    num1.setText("");
                    num2.setText("");
                    total.setText("");


                // SHOW BUTTON

                btnShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dbRef = FirebaseDatabase.getInstance().getReference().child("Total");
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChildren()) {
                                    num1.setText(dataSnapshot.child("student").getValue().toString());
                                    num2.setText(dataSnapshot.child("teacher").getValue().toString());
                                    total.setText(dataSnapshot.child("total").getValue().toString());
                                } else
                                    Toast.makeText(getApplicationContext(), "Cannot find Total Feedback", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });


                        //DELETE BUTTON

                            btnDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Total");
                                    delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChild("")) {
                                                dbRef = FirebaseDatabase.getInstance().getReference().child("Total").child("");
                                                dbRef.removeValue();
                                                clearControls();
                                                Toast.makeText(getApplicationContext(), "Feedback Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            } else

                                                Toast.makeText(getApplicationContext(), "No Feedback to Delete", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                            });
                    }}
                    );
                  }
                });
    }}
