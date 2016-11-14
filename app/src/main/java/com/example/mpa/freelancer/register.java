package com.example.mpa.freelancer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static android.R.attr.button;

public class register extends AppCompatActivity {

    Button register;
    EditText name;
    EditText password;
    EditText mail;
    String usrname;
    String passwordtxt;
    String mailtxt;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        mail = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(register.this, login.class);
                startActivity(myIntent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usrname = name.getText().toString();
                passwordtxt = password.getText().toString();
                mailtxt = mail.getText().toString();

                if(usrname.equals("") && passwordtxt.equals("")){
                    Toast.makeText(register.this, "Error: Fill in all the form.", Toast.LENGTH_LONG).show();
                }
                else{
                    ParseUser user = new ParseUser();
                    user.setUsername(usrname);
                    user.setPassword(passwordtxt);
                    user.setEmail(mailtxt);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(register.this, "Success: account created!", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(register.this, login.class);
                                startActivity(myIntent);
                            }
                            else{
                                Toast.makeText(register.this, "Error: couldn't create user", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });


    }
}
