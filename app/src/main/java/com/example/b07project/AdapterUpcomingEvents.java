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

import java.util.ArrayList;

public class AdapterUpcomingEvents extends RecyclerView.Adapter<AdapterUpcomingEvents.MyViewHolder> {

    Context context;
    ArrayList<Event> events;

    public AdapterUpcomingEvents(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

//    public String getTime(int hour, int min){
//        String hourS;
//        String minS;
//        String MM;
//        if (hour == 0) {
//            hourS = "12";
//            MM = "AM";
//        }
//        else if (0 < hour && hour < 12) {
//            hourS = String.valueOf(hour);
//            MM = "AM";
//        }
//        else {
//            hourS = String.valueOf(hour -12);
//            MM = "PM";
//        }
//
//        if(min <10) minS = "0" + String.valueOf(min);
//        else minS = String.valueOf(min);
//
//        return ( hourS + ":" + minS + MM);
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_up, parent, false);
        return new AdapterUpcomingEvents.MyViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Event event = events.get(position);
        holder.location.setText(event.getLocation());
        holder.sport.setText(event.getEventName());

        //change it back to ahmads events
        holder.time.setText(String.valueOf( event.getDate() + "  " + event.getStartHour() + "-" + event.getEndHour()));
        holder.cap.setText("Capacity: "+event.getCapacity());
        holder.sLeft.setText("Spot(s) left: "+event.getCapacity());
        if(event.getCapacity() == 0){
            holder.btn.setText("Unavailable");
            holder.btn.setClickable(false);
        }
        else if(event.getCapacity() == event.getCapacity()) holder.btn.setText("Book");
        else holder.btn.setText("Join");

    }

    @Override
    public int getItemCount() { return events.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, sport, time, cap, sLeft;
        Button btn;
        private AdapterUpcomingEvents adapter;
        private AlertDialog.Builder dB;
        private AlertDialog dialog;
        private Button plus, minus, cancel, book;
        TextView numPlayers;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.venue_up);
            sport = itemView.findViewById(R.id.sport_up);
            time = itemView.findViewById(R.id.timee_up);
            cap = itemView.findViewById(R.id.capacity_up);
            sLeft = itemView.findViewById(R.id.spots_up);
            btn = itemView.findViewById(R.id.button_up);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {createDialog();}
//            });
        }

//        public void createDialog(){
//            dB = new AlertDialog.Builder(adapter.context);
//            LayoutInflater inflater2 = LayoutInflater.from(adapter.context);
//            View view = inflater2.inflate(R.layout.activity_popup, null);
//
//            plus = view.findViewById(R.id.plus);
//            minus = view.findViewById(R.id.minus);
//            cancel = view.findViewById(R.id.cancell);
//            book = view.findViewById(R.id.book);
//            numPlayers = view.findViewById(R.id.numplayers);
//
//            dB.setView(view);
//            dialog = dB.create();
//            dialog.show();
//
//
//
//            plus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int numP = Integer.parseInt(String.valueOf(numPlayers.getText().toString()));
//                    String[] s = sLeft.getText().toString().split(": ");
//                    int left = Integer.parseInt(String.valueOf(s[s.length-1]));
//                    if (numP<left){
//                        numPlayers.setText(Integer.toString(numP+1));
//                    }
//                }
//            });

//            minus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int numP = Integer.parseInt(String.valueOf(numPlayers.getText().toString()));
//                    if(numP>1){
//                        numPlayers.setText(Integer.toString(numP-1));
//                    }
//                }
//            });
//
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//
//            book.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//        }

        public MyViewHolder linkAdapter(AdapterUpcomingEvents adapter){
            this.adapter = adapter;
            return this;
        }

    }
}
