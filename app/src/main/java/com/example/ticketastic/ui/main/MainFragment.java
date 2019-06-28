package com.example.ticketastic.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.support.annotation.NonNull;
import android.arch.lifecycle.ViewModelProviders;

import com.example.ticketastic.EventAdapter;
import com.example.ticketastic.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends android.support.v4.app.Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private com.example.ticketastic.ui.main.PageViewModel pageViewModel;
    RecyclerView mRecyclerView;
    EventAdapter eventAdapter;
    private SearchView searchView;



    public static MainFragment newInstance(int index) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        if(index == 1){
            pageViewModel.loadEvents(getContext()); //CU DE ESTOS ES UN SELECT DISTINTO SEGUN LA CATEGORIA
        }
        else if(index == 2){
            pageViewModel.loadMovie(getContext());
        }
        else if(index == 3){
            pageViewModel.loadTheater(getContext());
        }
        else{
            pageViewModel.loadConcert(getContext());
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabbed, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);

        Log.i("Mov",mRecyclerView.toString());

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        eventAdapter = new EventAdapter(getContext(),pageViewModel.getEvents());
        mRecyclerView.setAdapter(eventAdapter);
        return root;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //
        inflater.inflate(R.menu.options_menu,menu);

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                eventAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                eventAdapter.getFilter().filter(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }


}