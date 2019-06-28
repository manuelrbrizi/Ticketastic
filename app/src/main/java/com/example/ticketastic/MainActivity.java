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

        String dates1 = "Today,Fri 5 Jul,Sat 6 Jul,Sun 7 Jul";
        String dates2 = "Sat 6 Jul,Sun 7 Jul,Sat 13 Jul,Sun 14 Jul";

        dbh.addEvent("Star wars", "movie","https://images-na.ssl-images-amazon.com/images/I/81WjGytz7HL._SY445_.jpg",dates1,"16:00,19:00,23:00");
        dbh.addEvent("Toy Story","movie","http://www.boulevardshopping.com.ar/wp-content/uploads/TS4_NeonBg_Trio_1s_v1.0_Mech2a_FS_S.jpg",dates1,"16:00,19:00,23:00");
        dbh.addEvent("Lollapalooza","festival","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVH4jP-8KhF1Qu3vIvK1k_7LxWAHbPJZ22eiu6UCQjtYF2_TQ4","Wed 31 Jul","All day");
        dbh.addEvent("Champions","sport","http://www.movistarplus.es/recorte/n/fichaPeque/F3557344",dates2,"16:00,23:00");
        dbh.addEvent("Tini","concert","http://roadmusic.alsa.es/wp-content/uploads/2017/02/Tini-GotMeStartedTour.jpg",dates2,"21:00");
        dbh.addEvent("Les Luthiers","theater","https://www.valenciateatros.com/wp-content/uploads/2016/02/Chist-cartel-1.jpg",dates2,"22:00");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
