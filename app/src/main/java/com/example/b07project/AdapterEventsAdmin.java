package com.example.b07project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterEventsAdmin extends RecyclerView.Adapter<AdapterEventsAdmin.MyViewHolder> {

    Context context;
    ArrayList<Event> events;
    String admin;


    public AdapterEventsAdmin(Context context, ArrayList<Event> events, String adm) {
        this.context = context;
        this.events = events;
        this.admin = adm;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_events, parent, false);
        return new AdapterEventsAdmin.MyViewHolder(view, admin, events).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Event event = events.get(position);
        holder.location.setText(event.getVenueName() + " @ " + event.getLocation());
        holder.sport.setText(event.getEventName());
//        String start = getTime(event.getStartHour(), event.getStartMin()) ;
//        String end = getTime(event.getEndHour(), event.getEndMin());
//        holder.time.setText(String.valueOf(event.date.getDayOfMonth())+ "/" + String.valueOf(event.date.getMonthValue())
//                + "/" + String.valueOf(event.date.getYear()) + "  " + start + "-" + end);
        holder.time.setText(String.valueOf( event.getDate() + " @ " + event.starttime() + "-" + event.endtime()));
        holder.cap.setText("Capacity: "+event.getCapacity());
        holder.sLeft.setText("Spot(s) left: "+event.getSpotsLeft());
    }

    @Override
    public int getItemCount() { return events.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, sport, time, cap, sLeft;
        Button btn;
        private AdapterEventsAdmin adapter;
        String user;
        ArrayList<Event> events = new ArrayList<Event>();

        public MyViewHolder(@NonNull View itemView, String user, ArrayList<Event> events) {
            super(itemView);
            this.user = user;
            this.events = events;

            location = itemView.findViewById(R.id.venue_up);
            sport = itemView.findViewById(R.id.sport_up);
            time = itemView.findViewById(R.id.timee_up);
            cap = itemView.findViewById(R.id.capacity_up);
            sLeft = itemView.findViewById(R.id.spots_up);
        }

        public MyViewHolder linkAdapter(AdapterEventsAdmin adapter){
            this.adapter = adapter;
            return this;
        }

    }
}
