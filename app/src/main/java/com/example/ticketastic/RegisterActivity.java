package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

                String username = un.getEditText().getText().toString();
                String password = pw.getEditText().getText().toString();

                if(dbh.checkAvailableUsername(username)){
                    if(dbh.addData(username, password)){
                        Log.i("BOOL1", "true");
                    }
                    else{
                        Log.i("BOOL1", "false");
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
