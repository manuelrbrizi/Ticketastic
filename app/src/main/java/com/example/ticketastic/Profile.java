package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button getTickets = findViewById(R.id.getTickets);
        getTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
                ArrayList<Ticket> ticketList = dbh.getTicketsByUser(PreferenceUtils.getUsername(getApplicationContext()));
                StringBuilder str = new StringBuilder();

                for(int i = 0; i < ticketList.size(); i++){
                    str.append(ticketList.get(i).getEventName());
                    str.append("\n");
                }

                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                PreferenceUtils.saveUsername(getApplicationContext(), null);
                PreferenceUtils.savePassword(getApplicationContext(), null);
                startActivity(intent);
            }
        });
    }
}
