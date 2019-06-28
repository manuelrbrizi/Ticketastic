package com.example.ticketastic.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.ticketastic.DatabaseHandler;
import com.example.ticketastic.Event;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {
    private List<Event> mEventList;

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    void loadEvents(Context context){
        DatabaseHandler dbh = new DatabaseHandler(context);
        mEventList = dbh.getEvents();
    }

    void loadMovie(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        mEventList = db.getEvents("movie");
    }

    void loadFestival(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        mEventList = db.getEvents("festival");
    }

    void loadTheater(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        mEventList = db.getEvents("theater");
    }

    void loadConcert(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        mEventList = db.getEvents("concert");
    }

    void loadSport(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        mEventList = db.getEvents("sport");
    }

    List<Event> getEvents(){
        return mEventList;
    }
}