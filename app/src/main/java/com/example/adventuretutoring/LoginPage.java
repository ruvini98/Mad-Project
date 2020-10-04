package com.example.adventuretutoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    EditText username,password;
    Button login;

    // DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Admin");
    Admin admin;

    private void clearControls() {
        username.setText("");
        password.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username = findViewById(R.id.lusername); //username
        password = findViewById(R.id.lpassword); //password
        login = findViewById(R.id.lbtn); //login button

        TextView frpassword = (TextView) findViewById(R.id.fpassword); //forgot password

        //link to admin panel
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminPanel();

            }
        });

        //validate login to admin panel
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin")){
                    Toast.makeText(LoginPage.this, "Logged in Success!",Toast.LENGTH_SHORT).show();
                    username.setText(null);
                    password.setText(null);
                    Intent i = new Intent(getApplicationContext(),Adminpanel.class);
                    startActivity(i);
                }else {
                    Toast.makeText(LoginPage.this,"No valid User!",Toast.LENGTH_SHORT).show();
                }
                // if (TextUtils.isEmpty(username.getText().toString()))
                // Toast.makeText(getApplicationContext(), "Please enter the User Name", Toast.LENGTH_SHORT).show();
                //   else if (TextUtils.isEmpty(password.getText().toString()))
                // Toast.makeText(getApplicationContext(), "Please Enter the Password", Toast.LENGTH_SHORT).show();
                //else
                // admin.setUsername();
                // }
            }
        });

    }
    public void openAdminPanel() {

        Intent intent = new Intent(LoginPage.this,Adminpanel.class);
        startActivity(intent);
    }
}