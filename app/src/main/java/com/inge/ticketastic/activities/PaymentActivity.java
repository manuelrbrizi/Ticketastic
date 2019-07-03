package com.inge.ticketastic.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inge.ticketastic.database.DatabaseHandler;
import com.inge.ticketastic.utils.GMailSender;
import com.inge.ticketastic.utils.PreferenceUtils;
import com.inge.ticketastic.R;
import com.inge.ticketastic.classes.Ticket;

import cards.pay.paycardsrecognizer.sdk.Card;
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent;

public class PaymentActivity extends AppCompatActivity {

    Ticket t;
    Context context;
    TextView name;
    TextView number;
    TextView expiration;
    TextView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = getApplicationContext();
        getSupportActionBar().setTitle("Payment details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = new ScanCardIntent.Builder(context).build();
        startActivityForResult(intent, 1);

        name = findViewById(R.id.card_name);
        number = findViewById(R.id.card_number);
        expiration = findViewById(R.id.card_expiration);
        code = findViewById(R.id.card_code);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Card card = data.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD);
                name.setText(card.getCardHolderName());
                number.setText(card.getCardNumber());
                expiration.setText(card.getExpirationDate());
//                String cardData = "Card number: " + card.getCardNumber() + "\n"
//                        + "Card holder: " + card.getCardHolderName() + "\n"
//                        + "Card expiration date: " + card.getExpirationDate();
//                Log.i("Card", "Card info: " + cardData);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("Card", "Scan canceled");
            } else {
                Log.i("Card", "Scan failed");
            }
        }
    }
}
