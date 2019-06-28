package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    final DatabaseHandler dbh = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout un = (TextInputLayout) findViewById(R.id.textInputLayout2);
                TextInputLayout pw = (TextInputLayout) findViewById(R.id.textInputLayout3);

                String username = un.getEditText().getText().toString();
                String password = pw.getEditText().getText().toString();

                Log.i("USR", username);
                Log.i("PSW", password);

                if(dbh.checkUser(username, password)){
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Check username and password!", Toast.LENGTH_SHORT).show();
                    Log.i("LOGIN FAIL", "wrong username");
                }
            }
        });

        Button signup = findViewById(R.id.signupButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button deleteDB = findViewById(R.id.deleteDB);
        deleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().deleteDatabase("ticketastic.db");
            }
        });
    }
}
