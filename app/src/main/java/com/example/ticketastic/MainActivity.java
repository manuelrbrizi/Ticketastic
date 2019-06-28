package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHandler dbh = new DatabaseHandler(this);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.textinput);
                String username = et.getText().toString();
                Log.i("USERNAME", username);

                boolean b = dbh.addData(username, "fedeesgay");

                if(b){
                    Log.i("BOOLEAN", "true");
                }
                else{
                    Log.i("BOOLEAN", "false");
                }
            }
        });

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
