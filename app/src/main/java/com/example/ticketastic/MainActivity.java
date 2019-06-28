package com.example.ticketastic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHandler dbh = new DatabaseHandler(this);

        dbh.addEvent("Star wars", "movie","https://images-na.ssl-images-amazon.com/images/I/81WjGytz7HL._SY445_.jpg","AGO07,AGO08","16:00,23:00");
        dbh.addEvent("Toy Story","movie","http://www.boulevardshopping.com.ar/wp-content/uploads/TS4_NeonBg_Trio_1s_v1.0_Mech2a_FS_S.jpg","AGO07,AGO08,AGO09","16:00,23:00");
        dbh.addEvent("Lollapalooza","festival","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVH4jP-8KhF1Qu3vIvK1k_7LxWAHbPJZ22eiu6UCQjtYF2_TQ4","SEP17,SEP18","19:00,23:00");
        dbh.addEvent("Champions","sport","http://www.movistarplus.es/recorte/n/fichaPeque/F3557344","AGO07,AGO08","16:00,23:00");
        dbh.addEvent("Tini","concert","http://roadmusic.alsa.es/wp-content/uploads/2017/02/Tini-GotMeStartedTour.jpg","AGO07,AGO08","16:00,23:00");
        dbh.addEvent("Les Luthiers","theater","https://www.valenciateatros.com/wp-content/uploads/2016/02/Chist-cartel-1.jpg","AGO07,AGO08","16:00,23:00");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
