package com.inge.ticketastic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.inge.ticketastic.classes.Event;
import com.inge.ticketastic.classes.EventDetails;
import com.inge.ticketastic.R;
import com.inge.ticketastic.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> implements Filterable {
    private Context context;
    private List<Event> eventList;
    private List<Event> eventListFiltered;

    class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        CardView eventCV;

        EventViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.event_image);
            eventCV = view.findViewById(R.id.event_cv);
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
        holder.thumbnail.setImageResource(eventListFiltered.get(position).getImage());
        holder.eventCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("event", eventListFiltered.get(position));
                context.startActivity(intent);
            }
        });
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
                }
                else if(charString.equals("2x1")){
                    List<Event> filteredList = new ArrayList<>();
                    for (Event row : eventList) {
                        if (row.isPromoted()) {
                            filteredList.add(row);
                        }
                    }

                    eventListFiltered = filteredList;
                }
                else {
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

    public void setList(List<Event> list){
        this.eventList = list;
        this.eventListFiltered = list;
    }
}