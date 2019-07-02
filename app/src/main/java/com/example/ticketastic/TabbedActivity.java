package com.example.ticketastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ticketastic.ui.main.MainFragment;
import com.example.ticketastic.ui.main.SectionsPagerAdapter;

public class TabbedActivity extends AppCompatActivity {

    SectionsPagerAdapter sectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        //ACA SETTEABA EL FAB
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.profile){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }
        return false;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            MainFragment mf =  (MainFragment) sectionsPagerAdapter.getItem(1);
            if(mf.searchView != null)
                mf.searchView.setQuery("hola",true);

            //Toast.makeText(this,"voice", Toast.LENGTH_LONG).show();

        }

    }
}