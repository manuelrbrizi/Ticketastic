package com.example.ticketastic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Event event = (Event) getIntent().getSerializableExtra("event");
        ImageView iv = findViewById(R.id.event_detailed_image);
        Picasso.get().load(event.getUrl()).into(iv);
        TextView tv = findViewById(R.id.event_title);
        tv.setText(event.getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = findViewById(R.id.day_recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        List<String> l = new ArrayList<>();
        l.add("ABCDE");
        l.add("AABCDE");
        l.add("AABCDE");
        l.add("AABCDE");
        l.add("AABCDE");

        dayAdapter = new HorizontalAdapter(this,l);

        mRecyclerView.setAdapter(dayAdapter);

        LinearLayoutManager timeLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        timeRecyclerView = findViewById(R.id.time_recyler_view);
        timeRecyclerView.setLayoutManager(timeLayoutManager);

        timeAdapter = new HorizontalAdapter(this,l);

        timeRecyclerView.setAdapter(timeAdapter);


    }
}
