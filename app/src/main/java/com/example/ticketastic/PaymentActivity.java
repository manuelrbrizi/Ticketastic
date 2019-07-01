package com.example.ticketastic;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    Ticket t;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = getApplicationContext();
        getSupportActionBar().setTitle("Payment details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        t = (Ticket) getIntent().getSerializableExtra("ticket");

        Button b = findViewById(R.id.payment);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbh = new DatabaseHandler(v.getContext());
                dbh.addTicket(t);

                   new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void[] objects) {
                            String body = String.format("Thank you for your purchase! Your code is %s", t.getCode());

                            try {
                            GMailSender sender = new GMailSender("ticketasticnoreply@gmail.com", "1234abcd5678");
                            sender.sendMail("Purchase information",
                                    body,
                                    "ticketasticNoReply@gmail.com",
                                    PreferenceUtils.getUsername(context));
                            }   catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                            return null;

                        }
                    }.execute();


//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(PaymentActivity.this,ConfirmationActivity.class);
                intent.putExtra("ticket",t);
                startActivity(intent);
                finish();
            }
        });
    }
}
