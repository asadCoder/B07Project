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

public class AdapterUpcomingEvents extends RecyclerView.Adapter<AdapterUpcomingEvents.MyViewHolder> {

    Context context;
    ArrayList<Event> events;
    ArrayList<Event> myEvents;
    String user;


    public AdapterUpcomingEvents(Context context, ArrayList<Event> events, String user, ArrayList<Event> myEvents) {
        this.context = context;
        this.events = events;
        this.user = user;
        this.myEvents = myEvents;

        }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.eventss, parent, false);
        return new AdapterUpcomingEvents.MyViewHolder(view,user, events).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Event event = events.get(position);
        holder.location.setText(event.getVenueName() + " @ " + event.getLocation());
        holder.sport.setText(event.getEventName());
        holder.time.setText(String.valueOf( event.getDate() + " @ " + event.starttime() + "-" + event.endtime()));
        holder.cap.setText("Capacity: "+event.getCapacity());
        holder.sLeft.setText("Spot(s) left: "+event.getSpotsLeft());
        holder.saver.setText(String.valueOf(position));
        if(myEvents.contains(events.get(position))){
            holder.btn.setText("Joined");
            holder.btn.setClickable(false);
        }

        else if(event.getSpotsLeft() == 0){
            holder.btn.setText("Unavailable");
            holder.btn.setClickable(false);
        }
        else if(event.getSpotsLeft() == event.getCapacity()) holder.btn.setText("Book");
        else holder.btn.setText("Join");

    }

    @Override
    public int getItemCount() { return events.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, sport, time, cap, sLeft, saver;
        Button btn;
        private AdapterUpcomingEvents adapter;
        private AlertDialog.Builder dB;
        private AlertDialog dialog;
        private Button plus, minus, cancel, book;
        TextView numPlayers;
        String user;
        ArrayList<Event> events = new ArrayList<Event>();


        public MyViewHolder(@NonNull View itemView, String user, ArrayList<Event> events) {
            super(itemView);
            this.user = user;
            this.events = events;

            location = itemView.findViewById(R.id.venue);
            sport = itemView.findViewById(R.id.sport);
            time = itemView.findViewById(R.id.timee);
            cap = itemView.findViewById(R.id.capacity);
            sLeft = itemView.findViewById(R.id.spots);
            btn = itemView.findViewById(R.id.button5);
            saver = itemView.findViewById(R.id.saver);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {createDialog();}
            });
        }

        public void createDialog(){
            dB = new AlertDialog.Builder(adapter.context);
            LayoutInflater inflater2 = LayoutInflater.from(adapter.context);
            View view = inflater2.inflate(R.layout.activity_popup, null);

            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            cancel = view.findViewById(R.id.cancell);
            book = view.findViewById(R.id.book);
            numPlayers = view.findViewById(R.id.numplayers);

            dB.setView(view);
            dialog = dB.create();
            dialog.show();



            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int numP = Integer.parseInt(String.valueOf(numPlayers.getText().toString()));
                    String[] s = sLeft.getText().toString().split(": ");
                    int left = Integer.parseInt(String.valueOf(s[s.length-1]));
                    if (numP<left){
                        numPlayers.setText(Integer.toString(numP+1));
                    }
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int numP = Integer.parseInt(String.valueOf(numPlayers.getText().toString()));
                    if(numP>1){
                        numPlayers.setText(Integer.toString(numP-1));
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(saver.getText() != null) {

                        Event e = events.get(Integer.parseInt(saver.getText().toString()));

                        int i = Integer.parseInt(numPlayers.getText().toString());

                        e.spotsLeft = e.spotsLeft - i;
                        sLeft.setText("Spot(s) left: " + String.valueOf(e.spotsLeft));

                        String key = e.toString();


                        FirebaseDatabase database= FirebaseDatabase.getInstance();

                        DatabaseReference ref = database.getReference();

                        ref = database.getReference("Customers/" + user + "/Spots/"+ key);
                        ref.setValue(i);

                        ref = database.getReference("Customers/" + user + "/Events/"+ key);
                        ref.setValue(e);

                        ref = database.getReference("Admins/" + e.getAdmin() + "/Venues/"+
                                e.getAddress()+ "/Events/" + key);
                        ref.setValue(e);

                        ref = database.getReference("Venues/"+e.getAddress()+"/Events/"+key);
                        ref.setValue(e);

                        ref = database.getReference("Events/" + key);
                        ref.setValue(e);

                        dialog.dismiss();
                    }
                }
            });
        }

        public MyViewHolder linkAdapter(AdapterUpcomingEvents adapter){
            this.adapter = adapter;
            return this;
        }

    }
}
