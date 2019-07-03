package com.inge.ticketastic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inge.ticketastic.R;
import com.inge.ticketastic.classes.Ticket;
import com.inge.ticketastic.adapters.TicketAdapter;
import com.inge.ticketastic.database.DatabaseHandler;

import java.util.ArrayList;

public class ConfirmationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ArrayList<Ticket> ticketArray = new ArrayList<>();
        Ticket t = (Ticket) getIntent().getSerializableExtra("ticket");
        int id = getIntent().getIntExtra("id", 0);
        ticketArray.add(t);
        DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
        dbh.updateEvent(id, t.getQuantity());

        getSupportActionBar().setTitle("Purchase successful");

        TextView tv = findViewById(R.id.ticket_data);
        tv.setText("You can access the event with this code:");

        TextView tv2 = findViewById(R.id.ticket_code);
        tv2.setText(t.getCode());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.confirmation_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        ticketAdapter = new TicketAdapter(this, ticketArray);
        recyclerView.setAdapter(ticketAdapter);

        Button ok = findViewById(R.id.button_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabbedActivity.class);
                startActivity(intent);
            }
        });
    }
}
