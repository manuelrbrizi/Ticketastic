package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    final DatabaseHandler dbh = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(PreferenceUtils.getUsername(getApplicationContext()) != null){
            Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
            startActivity(intent);
        }

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout un = (TextInputLayout) findViewById(R.id.textInputLayout2);
                TextInputLayout pw = (TextInputLayout) findViewById(R.id.textInputLayout3);

                String username = "";
                String password = "";

                try {
                    username = un.getEditText().getText().toString();
                    password = pw.getEditText().getText().toString();
                } catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(), "You should complete all fields before pressing LOGIN", Toast.LENGTH_SHORT).show();
                }

                if(dbh.checkUser(username, password)){
                    PreferenceUtils.saveUsername(getApplicationContext(), username);
                    PreferenceUtils.savePassword(getApplicationContext(), password);
                    Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
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
                PreferenceUtils.saveUsername(getApplicationContext(), null);
                PreferenceUtils.savePassword(getApplicationContext(), null);
            }
        });

        Button toastAll = findViewById(R.id.toastAll);
        toastAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, String>> result = dbh.getUsers();
                for(int i = 0; i < result.size(); i++){
                    String str = String.format("Name = %s", result.get(i).get("username"));
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button showUser = findViewById(R.id.showUser);
        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> result = dbh.getUserByUsername("igna");
                Toast.makeText(getApplicationContext(), String.format("Name = %s", result.get("username")), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
