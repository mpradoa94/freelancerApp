package com.example.mpa.freelancer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class login extends AppCompatActivity {

    Button login;
    Button register;
    EditText email;
    EditText password;
    String mailtxt;
    String passwordtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();

                ParseUser.logInInBackground(mailtxt, passwordtxt,
                        new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null){
                            Intent intent = new Intent(login.this, MenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(login.this, "Logged in", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(login.this, "No such user", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });



    }
}
