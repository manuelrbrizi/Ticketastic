package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    Ticket t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setTitle("Payment details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        t = (Ticket) getIntent().getSerializableExtra("ticket");

        Button b = findViewById(R.id.payment);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbh = new DatabaseHandler(v.getContext());
                dbh.addTicket(t);
                Intent intent = new Intent(PaymentActivity.this,ConfirmationActivity.class);
                intent.putExtra("ticket",t);
                startActivity(intent);
                finish();
            }
        });
    }
}
