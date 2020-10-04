package com.example.madprojecte1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileActivity extends AppCompatActivity {

    private static final String TAG ="StudentProfileAActivity";
    String userID;
    TextView birth,email,id,mob,name;

    Button update,delete;
    DatabaseReference dbRef;
    Student myStd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Intent intent = getIntent();
        userID = intent.getStringExtra(CheckID.userID);

        final DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
        name = findViewById(R.id.etName);
        birth = findViewById(R.id.etDob);
        mob = findViewById(R.id.etMob);
        email = findViewById(R.id.etEmail);
        update = findViewById(R.id.buttonUpdate);
        delete = findViewById(R.id.buttonDelete);

        myStd = new Student();

        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){

//                    myStd.setId(dataSnapshot.child(userID).getValue().toString());
                    myStd.setBirth(dataSnapshot.child("birth").getValue().toString());
                    myStd.setMob(dataSnapshot.child("mob").getValue().toString());
                    myStd.setEmail(dataSnapshot.child("email").getValue().toString());
                    myStd.setName(dataSnapshot.child("name").getValue().toString());

                    name.setText(myStd.getName());
                    birth.setText(myStd.getBirth());
                    mob.setText(myStd.getMob());
                    email.setText(myStd.getEmail());
                    //id.setText(myStd.getId());


                }

                else
                    Toast.makeText(getApplicationContext(),"No Source to display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()){
                            try {
                                myStd.setName(name.getText().toString().trim());
                                myStd.setEmail(email.getText().toString().trim());
                                myStd.setMob(mob.getText().toString().trim());
                                myStd.setBirth(birth.getText().toString().trim());
                              //  myStd.setId(id.getText().toString().trim());

                                DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
                                upRef.setValue(myStd);

                                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();

                            }

                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                            }

                        }

                        else {
                            Toast.makeText(getApplicationContext(), "No Source to Update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChildren()){

                            DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student").child(userID);
                            delRef.removeValue();
//                            clearControls();

                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                        }

                        else
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });






    }

    private void clearControls(){

        name.setText("");
        birth.setText("");
        mob.setText("");
        email.setText("");
    }

}