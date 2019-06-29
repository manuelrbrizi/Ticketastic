package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ConfirmationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Ticket t = (Ticket) getIntent().getSerializableExtra("ticket");
        TextView tv = findViewById(R.id.ticket_data);
        int q = 3;

        String name = "<b>Event name: </b>" + t.getEventName() + "\n";
        String date = "<b>Date: </b>" + t.getEventDate() + "\n";
        String time = "<b>Time: </b>" + t.getSchedule() + "\n";
        String quantity = "<b>Quantity: </b>" + String.valueOf(q) + "\n";
        String price = "<b>Total cost: 3 x </b>" + "$" + String.valueOf(t.getPrice()) + " = " +
                       String.valueOf(q * t.getPrice()) + "\n";


        tv.setText(Html.fromHtml(name + date + time + quantity + price));

        ImageView iv = findViewById(R.id.event_image);
        Picasso.get().load(t.getImage()).into(iv);

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
