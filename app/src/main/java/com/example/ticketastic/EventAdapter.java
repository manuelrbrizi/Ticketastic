package com.example.ticketastic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> implements Filterable {
    private Context context;
    private List<Event> eventList;
    private List<Event> eventListFiltered;
    //private ContactsAdapterListener listener;

    class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;

        EventViewHolder(View view) {
            super(view);

            thumbnail = view.findViewById(R.id.event_image);


        }
    }


    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.eventListFiltered = eventList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_layout, parent, false);

        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        Picasso.get().load(eventListFiltered.get(position).getUrl()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return eventListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    eventListFiltered = eventList;
                } else {
                    List<Event> filteredList = new ArrayList<>();
                    for (Event row : eventList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    eventListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = eventListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                eventListFiltered = (ArrayList<Event>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}