package com.inge.ticketastic.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inge.ticketastic.database.DatabaseHandler;
import com.inge.ticketastic.utils.EventUtils;
import com.inge.ticketastic.utils.GMailSender;
import com.inge.ticketastic.utils.PreferenceUtils;
import com.inge.ticketastic.R;

public class RegisterActivity extends AppCompatActivity {
    final DatabaseHandler dbh = new DatabaseHandler(this);
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        context = getApplicationContext();

        Button send = findViewById(R.id.sendButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout un = (TextInputLayout) findViewById(R.id.textInputLayout4);
                TextInputLayout pw1 = (TextInputLayout) findViewById(R.id.textInputLayout5);
                TextInputLayout pw2 = (TextInputLayout) findViewById(R.id.confirm_password);
                String username = "";
                String password = "";
                String confirm = "";

                try {
                    username = un.getEditText().getText().toString();
                    password = pw1.getEditText().getText().toString();
                    confirm = pw2.getEditText().getText().toString();
                } catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(), "You should complete all fields before pressing SEND", Toast.LENGTH_SHORT).show();
                }

                if(username.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(confirm)){
                        if (dbh.checkAvailableUsername(username)) {
                            boolean b = dbh.addUser(username, password);
                            if (b) {
                                PreferenceUtils.saveUsername(getApplicationContext(), username);
                                PreferenceUtils.savePassword(getApplicationContext(), password);
                                if (!EventUtils.areEventsLoaded()) {
                                    EventUtils.addEventsToDatabase(getApplicationContext());
                                }
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void[] objects) {
                                        String body = String.format("Welcome to Ticketastic!<br/><br/>Your account was created succesfully.Happy shopping!<br/><br/>Ticketastic team");

                                        try {
                                            GMailSender sender = new GMailSender("ticketasticnoreply@gmail.com", "1234abcd5678");
                                            sender.sendMail("Ticketastic new account",
                                                    Html.fromHtml(body).toString(),
                                                    "ticketasticNoReply@gmail.com",
                                                    PreferenceUtils.getUsername(context));
                                        }   catch (Exception e) {
                                            Log.e("SendMail", e.getMessage(), e);
                                        }
                                        return null;

                                    }
                                }.execute();
                                Toast.makeText(getApplicationContext(), String.format("User %s succesfully created!", username), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "We can't create your user right now. Please try later.", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), String.format("Username %s already taken", username), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Given passwords doesn't match!", Toast.LENGTH_SHORT).show();
                    }
                }
        }});
    }
}
