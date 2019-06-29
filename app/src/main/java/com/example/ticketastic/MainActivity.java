package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHandler dbh = new DatabaseHandler(this);

        String dates1 = "Today,Fri 5 Jul,Sat 6 Jul,Sun 7 Jul";
        String dates2 = "Sat 6 Jul,Sun 7 Jul,Sat 13 Jul,Sun 14 Jul";

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
