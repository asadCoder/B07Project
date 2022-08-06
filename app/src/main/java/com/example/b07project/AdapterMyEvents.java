package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterMyEvents extends RecyclerView.Adapter<AdapterMyEvents.MyViewHolder> implements Serializable {

    Context context;
    ArrayList<Event> events;

    public AdapterMyEvents(Context context, ArrayList<Event> events){
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public AdapterMyEvents.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.eventss, parent,false);
        return new AdapterMyEvents.MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyEvents.MyViewHolder holder, int position) {
        Event event = events.get(position);
        holder.location.setText(event.getLocation());
        holder.sport.setText(event.getEventName());
        holder.time.setText(event.getStartHour()+":00-"+event.getEndHour()+":00");
        holder.capacity.setText("Capacity: "+event.getCapacity());
        holder.sLeft.setText("Spot(s) left: "+event.getCapacity());
        holder.butt.setText("Cancel");
//        if(event.getSpotsLeft() == 0) {
//            holder.butt.setText("Uncapacityailable");
//            holder.butt.setClickable(false);
//        }
//        else if(event.getSpotsLeft() == event.getCapacity()) holder.butt.setText("Book");
//        else holder.butt.setText("Join");

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, sport, time, capacity, sLeft;
        Button butt;
        private AdapterMyEvents adapter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.venue);
            sport = itemView.findViewById(R.id.sport);
            time = itemView.findViewById(R.id.timee);
            capacity = itemView.findViewById(R.id.capacity);
            sLeft = itemView.findViewById(R.id.spots);
            butt = itemView.findViewById(R.id.button);
            butt.setOnClickListener(view -> {
                adapter.events.remove(getAbsoluteAdapterPosition());
                adapter.notifyItemRemoved(getAbsoluteAdapterPosition());
            });

        }

        public MyViewHolder linkAdapter(AdapterMyEvents adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
