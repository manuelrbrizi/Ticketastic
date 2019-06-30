package com.example.ticketastic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TicketAdapter extends  RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    Context context;
    List<Ticket> ticketList;

    public TicketAdapter(Context context, List<Ticket> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ticket_layout, viewGroup, false);

        return new TicketAdapter.TicketViewHolder(itemView);
    }

    
    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder ticketViewHolder, int i) {
        Picasso.get().load(ticketList.get(i).getImage()).into(ticketViewHolder.thumbnail);
        // CAMBIAR EL 1 POR TICKET.QUANTITY, LO ULTIMO DE LA LINEA DE ABAJO →→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→↓↓↓↓↓↓↓
        String info = String.format(" &nbsp; <b>%s</b> <br> &nbsp; <b>Day:</b> %s <br> &nbsp; <b>Time:</b> %s<br> &nbsp; <b>Quantity:</b> xx<br> &nbsp; <b>Price per ticket:</b> %s$ <br> &nbsp; <b>Total:</b> %d$ ",ticketList.get(i).getEventName(),ticketList.get(i).getEventDate(),ticketList.get(i).getSchedule(),ticketList.get(i).getPrice(),ticketList.get(i).getPrice()*1);
        ticketViewHolder.ticketInfo.setText(Html.fromHtml(info));
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        CardView ticketCV;
        TextView ticketInfo;

        TicketViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.ticket_image);
            ticketCV = view.findViewById(R.id.ticket_cv);
            ticketInfo = view.findViewById(R.id.ticket_info);
        }
    }
}