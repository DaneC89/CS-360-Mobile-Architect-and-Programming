package com.example.eventtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    EditText username, password, confirm_password;
    Button create_button;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        create_button = (Button) findViewById(R.id.create_button);
        DB = new DBHelper(this);


        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pw = password.getText().toString();
                String confirm = confirm_password.getText().toString();

                if (user.equals("") || pw.equals("") || confirm.equals(""))
                    Toast.makeText(Registration.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                else{
                    if(pw.equals(confirm)){
                        Boolean checkUser = DB.checkUserName(user);
                        if(checkUser == false){
                            Boolean insert = DB.insertData(user, pw);
                            if(insert == true) {
                                Toast.makeText(Registration.this, "Account successfully created.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainEventTracker.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Registration.this, "Failed to create account.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Registration.this, "Username already exists. Please sign in.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Registration.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }
}