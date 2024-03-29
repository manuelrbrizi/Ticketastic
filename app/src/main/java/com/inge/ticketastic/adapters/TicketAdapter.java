package com.inge.ticketastic.adapters;

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

import com.inge.ticketastic.R;
import com.inge.ticketastic.classes.Ticket;

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
        Ticket t = ticketList.get(i);
        ticketViewHolder.thumbnail.setImageResource(t.getImage());
        String info = "";
        // CAMBIAR EL 1 POR TICKET.QUANTITY, LO ULTIMO DE LA LINEA DE ABAJO →→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→↓↓↓↓↓↓↓
        if(t.isPromoted()){
            info = String.format(" &nbsp; <b>%s</b> <br> &nbsp; <b>Code: </b> %s <br>&nbsp; <b>Day:</b> %s <br> &nbsp; <b>Time:</b> %s<br> &nbsp; <b>Quantity:</b> %d<br> &nbsp; <b>Price per ticket:</b> %d$ <br> &nbsp; <b>Total:</b> %d$ <b>2x1 ACTIVE!</b> ", t.getEventName(), t.getCode(),t.getEventDate(), t.getSchedule(), t.getQuantity(), t.getPrice(), t.getPrice()*(t.getQuantity()%2)+(t.getPrice()*(t.getQuantity()/2)));
        }
        else{
            info = String.format(" &nbsp; <b>%s</b> <br> &nbsp; <b>Code: </b> %s <br>&nbsp; <b>Day:</b> %s <br> &nbsp; <b>Time:</b> %s<br> &nbsp; <b>Quantity:</b> %d<br> &nbsp; <b>Price per ticket:</b> %d$ <br> &nbsp; <b>Total:</b> %d$ ", t.getEventName(), t.getCode(),t.getEventDate(), t.getSchedule(), t.getQuantity(), t.getPrice(), t.getPrice()*t.getQuantity());
        }

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
