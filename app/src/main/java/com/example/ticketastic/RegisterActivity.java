package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    final DatabaseHandler dbh = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button send = findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout un = (TextInputLayout) findViewById(R.id.textInputLayout4);
                TextInputLayout pw = (TextInputLayout) findViewById(R.id.textInputLayout5);
                String username = "";
                String password = "";

                try {
                    username = un.getEditText().getText().toString();
                    password = pw.getEditText().getText().toString();
                } catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(), "You should complete all fields before pressing SEND", Toast.LENGTH_SHORT).show();
                }

                if(dbh.checkAvailableUsername(username)){
                    boolean b = dbh.addUser(username, password);
                    if(b){
                        PreferenceUtils.saveUsername(getApplicationContext(), username);
                        PreferenceUtils.savePassword(getApplicationContext(), password);
                        if(!EventUtils.areEventsLoaded()){
                            EventUtils.addEventsToDatabase(getApplicationContext());
                        }
                        Toast.makeText(getApplicationContext(), String.format("User %s succesfully created!", username), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "We can't create your user right now. Please try later.", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(getApplicationContext(), String.format("Username %s already taken", username), Toast.LENGTH_SHORT).show();
                }
        }});
    }
}
