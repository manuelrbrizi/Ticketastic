package com.inge.ticketastic.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.inge.ticketastic.classes.Profile;
import com.inge.ticketastic.R;
import com.inge.ticketastic.ui.main.MainFragment;
import com.inge.ticketastic.ui.main.SectionsPagerAdapter;

public class TabbedActivity extends AppCompatActivity {

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
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
        int i = 0;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            MainFragment mf = null;
            int pos = tabs.getSelectedTabPosition();
            Log.i("voiceFede",String.format("selected tab %d",pos));

            for(Fragment f : getSupportFragmentManager().getFragments()){
                mf = (MainFragment) f;
                if(mf.pageViewModel.getIndex() == pos+1){
                    break;
                }
            }

            mf.searchView.setQuery(intent.getStringExtra(SearchManager.QUERY),false);



        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.i("voiceFede",String.format("RequestCode %d",requestCode));
    }
}