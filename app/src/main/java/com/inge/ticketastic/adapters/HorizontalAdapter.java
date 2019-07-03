package com.inge.ticketastic.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inge.ticketastic.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private Context context;
    private List<String> stringList;
    private List<HorizontalViewHolder> list = new ArrayList<>();

    public List<HorizontalViewHolder> getList() {
        return list;
    }

    public HorizontalAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public HorizontalAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.horizontal_layout, viewGroup, false);

        return new HorizontalAdapter.HorizontalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalAdapter.HorizontalViewHolder horizontalViewHolder, int i) {
        horizontalViewHolder.tv.setText(stringList.get(i));
        list.add(horizontalViewHolder);

        if(horizontalViewHolder.clicked){
            horizontalViewHolder.cv.setBackgroundResource(R.drawable.selector2);
            horizontalViewHolder.tv.setTextColor(Color.parseColor("#ffffff"));

        }
        else{
            horizontalViewHolder.cv.setBackgroundResource(R.drawable.selector);
            horizontalViewHolder.tv.setTextColor(Color.parseColor("#8A2BE2"));

        }

        horizontalViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(HorizontalViewHolder h : list){
                    if(!h.equals(horizontalViewHolder)){
                        h.clicked = false;
                    }
                }

                horizontalViewHolder.clicked = !horizontalViewHolder.clicked;

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }




    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView tv;
        public boolean clicked;

        HorizontalViewHolder(View view) {
            super(view);
            cv = view.findViewById(R.id.horizontal_cv);
            tv = view.findViewById(R.id.horizontal_content);
            cv.setBackgroundResource(R.drawable.selector);
            clicked = false;
        }


    }
}
