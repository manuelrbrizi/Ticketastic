package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Ticket t = (Ticket) getIntent().getSerializableExtra("ticket");
        TextView tv = findViewById(R.id.ticket_data);
        Log.i("DBG", t.getEventName());
        Log.i("DBG", t.getUsername());
        String str = String.format("Name = %s, User = %s, Price = %d", t.getEventName(), t.getUsername(), t.getPrice());
        tv.setText(str);

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
