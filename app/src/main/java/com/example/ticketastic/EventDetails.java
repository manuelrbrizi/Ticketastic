package com.example.ticketastic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {

    RecyclerView mRecyclerView;
    HorizontalAdapter dayAdapter;
    HorizontalAdapter timeAdapter;
    RecyclerView timeRecyclerView;

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

        //HAY QUE VER COMO OBTENER CUAL ESTÁ CLICKEADO



        final Ticket t =
                new Ticket(event.getName(), event.getImage(), "13AGO19", "16:00", PreferenceUtils.getUsername(getApplicationContext()));



        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
                dbh.addTicket(t);

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
                Log.i("ticket",time);
                Log.i("ticket",day);

            }
        });
    }
}
