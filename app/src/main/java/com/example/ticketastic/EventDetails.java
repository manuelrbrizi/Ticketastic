package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {

    RecyclerView mRecyclerView;
    HorizontalAdapter dayAdapter;
    HorizontalAdapter timeAdapter;
    RecyclerView timeRecyclerView;
    Spinner qSpinner;

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        event = (Event) getIntent().getSerializableExtra("event");
        ImageView iv = findViewById(R.id.event_detailed_image);
        Picasso.get().load(event.getImage()).into(iv);
        TextView tv = findViewById(R.id.event_title);
        tv.setText(event.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = findViewById(R.id.day_recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        dayAdapter = new HorizontalAdapter(this,event.getDate());
        mRecyclerView.setAdapter(dayAdapter);

        LinearLayoutManager timeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        timeRecyclerView = findViewById(R.id.time_recyler_view);
        timeRecyclerView.setLayoutManager(timeLayoutManager);
        timeAdapter = new HorizontalAdapter(this,event.getSchedule());
        timeRecyclerView.setAdapter(timeAdapter);

        qSpinner = findViewById(R.id.quantity_spinner);

        TextView t = findViewById(R.id.descrip_text);
        t.setText(event.getDescription());

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());

                String day = null;
                for(HorizontalAdapter.HorizontalViewHolder hv : dayAdapter.getList()){
                    if(hv.clicked){
                        day = hv.tv.getText().toString();
                        break;
                    }
                }

                String time = null;
                for(HorizontalAdapter.HorizontalViewHolder hv : timeAdapter.getList()){
                    if(hv.clicked){
                        time = hv.tv.getText().toString();
                        break;
                    }
                }


                if(time != null && day != null){
                    int quantity = Integer.parseInt(qSpinner.getSelectedItem().toString());
                    //AGRAGAR QUANTITY A TICKET EN LA BASE DE DATOS
                    Ticket t = new Ticket(event.getName(), event.getImage(), day, time, PreferenceUtils.getUsername(getApplicationContext()), event.getPrice(), quantity);
                    dbh.addTicket(t);
                    Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                    intent.putExtra("ticket", t);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select a day and a time for the event", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
