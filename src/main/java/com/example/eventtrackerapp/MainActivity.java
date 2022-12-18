//Author: Dane Clark
//Date: 11-25-22
//Class: SNHU CS-360 Mobile Architect & Programming
//Instructor: DR. Sherri Maciosek

//Friendly Reminder is an app that will allow the user
//to add events to a calender that can help them remember
//the important events in their life.



package com.example.eventtrackerapp;


//Code help provided by https://www.youtube.com/watch?v=SMrB97JuIoM
//And https://www.youtube.com/watch?v=knpSbtbPz3o

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    EditText username, password;
    TextView createAccount;
    DBHelper DB;

    private int SMS_permission = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       username = (EditText) findViewById(R.id.username);
       password = (EditText) findViewById(R.id.password);
       createAccount =(TextView) findViewById(R.id.new_login);
       login_button = (Button) findViewById(R.id.login_button);
       DB = new DBHelper(this);

       Button permission_button = findViewById(R.id.permission_button);
       permission_button.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view) {
               if (ContextCompat.checkSelfPermission(MainActivity.this,
                       Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
                   Toast.makeText(MainActivity.this, "You have already granted access.",
                           Toast.LENGTH_SHORT).show();
               }
               else {
                   requestSMS_permission();

               }
           }
       });


        // admin and password
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pw = password.getText().toString();

                if(user.equals("")||pw.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkUserPw = DB.checkUserNamePassword(user, pw);
                    if (checkUserPw == true){
                        Toast.makeText(MainActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainEventTracker.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Invalid login attempt.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

    }
    public void openMainEventTracker(){
        Intent intent = new Intent(this, MainEventTracker.class);
        startActivity(intent);
    }

    private void requestSMS_permission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed to send SMS Text messages about events.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_SMS}, SMS_permission);

                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_SMS}, SMS_permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_permission) {
            if (grantResults.length  > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }
    }
}