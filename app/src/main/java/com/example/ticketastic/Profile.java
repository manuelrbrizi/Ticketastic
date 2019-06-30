package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.ticket_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        ticketAdapter = new TicketAdapter(this, dbh.getTicketsByUser(PreferenceUtils.getUsername(getApplicationContext())));
        recyclerView.setAdapter(ticketAdapter);

//        Button getTickets = findViewById(R.id.getTickets);
//        getTickets.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
//                ArrayList<Ticket> ticketList = dbh.getTicketsByUser(PreferenceUtils.getUsername(getApplicationContext()));
//                StringBuilder str = new StringBuilder();
//
//                for(int i = 0; i < ticketList.size(); i++){
//                    str.append(ticketList.get(i).getEventName());
//                    str.append("\n");
//                }
//
//                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Button logout = findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                PreferenceUtils.saveUsername(getApplicationContext(), null);
//                PreferenceUtils.savePassword(getApplicationContext(), null);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            PreferenceUtils.saveUsername(getApplicationContext(), null);
            PreferenceUtils.savePassword(getApplicationContext(), null);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_logout,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
