package com.example.ticketastic.ui.main;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.ticketastic.Event;

import java.util.ArrayList;
import java.util.List;

public class PageViewModel extends ViewModel {

    List<Event> mEventList;
    Event mEvent;

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

    public void loadEvents(){
        mEventList = new ArrayList<>();

        mEvent = new Event("Star wars","peli","https://images-na.ssl-images-amazon.com/images/I/81WjGytz7HL._SY445_.jpg");
        mEventList.add(mEvent);
        mEvent = new Event("Toy Story","peli","http://www.boulevardshopping.com.ar/wp-content/uploads/TS4_NeonBg_Trio_1s_v1.0_Mech2a_FS_S.jpg");
        mEventList.add(mEvent);
        mEvent = new Event("Lollapalooza","festival","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVH4jP-8KhF1Qu3vIvK1k_7LxWAHbPJZ22eiu6UCQjtYF2_TQ4");

        mEventList.add(mEvent);
        mEvent = new Event("Champions","deporte","http://www.movistarplus.es/recorte/n/fichaPeque/F3557344");

        mEventList.add(mEvent);
        mEvent = new Event("Tini","concierto","http://roadmusic.alsa.es/wp-content/uploads/2017/02/Tini-GotMeStartedTour.jpg");

        mEventList.add(mEvent);
        mEvent = new Event("Les Luthiers","teatro","https://www.valenciateatros.com/wp-content/uploads/2016/02/Chist-cartel-1.jpg");

        mEventList.add(mEvent);
    }

    void loadCine(){
        mEventList = new ArrayList<>();

        mEvent = new Event("Star wars","peli","https://images-na.ssl-images-amazon.com/images/I/81WjGytz7HL._SY445_.jpg");
        mEventList.add(mEvent);
        mEvent = new Event("Toy Story","peli","http://www.boulevardshopping.com.ar/wp-content/uploads/TS4_NeonBg_Trio_1s_v1.0_Mech2a_FS_S.jpg");
        mEventList.add(mEvent);

    }

    List<Event> getEvents(){
        return mEventList;
    }
}