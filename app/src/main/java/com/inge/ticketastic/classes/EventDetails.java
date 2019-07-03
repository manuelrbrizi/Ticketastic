package com.inge.ticketastic.classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inge.ticketastic.database.DatabaseHandler;
import com.inge.ticketastic.R;
import com.inge.ticketastic.activities.PaymentActivity;
import com.inge.ticketastic.adapters.HorizontalAdapter;
import com.inge.ticketastic.utils.PreferenceUtils;

import java.util.Random;

public class EventDetails extends AppCompatActivity {

    RecyclerView mRecyclerView;
    HorizontalAdapter dayAdapter;
    HorizontalAdapter timeAdapter;
    RecyclerView timeRecyclerView;
    Spinner qSpinner;
    TextView price;

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setTitle("Buy tickets");

        event = (Event) getIntent().getSerializableExtra("event");
        ImageView iv = findViewById(R.id.event_detailed_image);
        iv.setImageResource(event.getImage());
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
        price = findViewById(R.id.event_price);
        String info = "";

        if(event.isPromoted()){
            info = String.format("<b>Price: </b> %d$ <br> <b>THIS EVENT IS 2X1!</b>",event.getPrice());
        }
        else{
            info = String.format("<b>Price: </b> %d$",event.getPrice());
        }

        price.setText(Html.fromHtml(info));
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
                    Random random = new Random();
                    int nextInt = random.nextInt(256*256*256);
                    String code = String.format("%06x", nextInt).toUpperCase();
                    int promoted = (event.isPromoted())? 1:0;
                    Ticket t = new Ticket(code, event.getName(), event.getImage(), day, time, PreferenceUtils.getUsername(getApplicationContext()), event.getPrice(), quantity, promoted);

                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
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
