package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class ConfirmationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ArrayList<Ticket> ticketArray = new ArrayList<>();
        //DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
        Ticket t = (Ticket) getIntent().getSerializableExtra("ticket");
        ticketArray.add(t);

        Random random = new Random();
        int nextInt = random.nextInt(256*256*256);

        TextView tv = findViewById(R.id.ticket_data);
        tv.setText("You can access the event with this code:");

        TextView tv2 = findViewById(R.id.ticket_code);
        tv2.setText(String.format("\n %06x \n", nextInt).toUpperCase());

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
