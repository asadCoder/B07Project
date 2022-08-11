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

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterMyEvents extends RecyclerView.Adapter<AdapterMyEvents.MyViewHolder> implements Serializable {

    Context context;
    ArrayList<Event> events;
    String user;

    public AdapterMyEvents(Context context, ArrayList<Event> events, String user){
        this.context = context;
        this.events = events;
        this.user = user;
    }

    @NonNull
    @Override
    public AdapterMyEvents.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_up, parent,false);
        return new AdapterMyEvents.MyViewHolder(view, user).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyEvents.MyViewHolder holder, int position) {
        Event event = events.get(position);
        holder.location.setText(event.getVenueName() + "  " + event.getLocation());
        holder.sport.setText(event.getEventName());
        holder.time.setText(event.getDate() + "  " + event.starttime() + "-" + event.endtime());
        holder.cap.setText("Capacity: "+event.getCapacity());
        holder.sLeft.setText("Spot(s) left: "+event.getSpotsLeft());
        holder.saverMy.setText(event.getAdmin()+"~~~"+event.getVenueName()+"~~~"+event.getEventName()
                +"~~~"+event.getAddress()+"~~~"+event.getStartHour()+"~~~"+event.getStartMin()+"~~~"
                +event.getEndHour()+"~~~"+event.getEndMin()+"~~~"+event.getCapacity()+"~~~"+
                event.getSpotsLeft()+"~~~"+event.getLocation()+"~~~"+event.getDate());

        holder.btn.setText("X");
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView location, sport, time, cap, sLeft, saverMy;
        Button btn;
        ArrayList<Event> events = new ArrayList<Event>();
        String user;


        private AdapterMyEvents adapter;

        public MyViewHolder(@NonNull View itemView, String user) {
            super(itemView);
            this.events = events;
            this.user = user;


            location = itemView.findViewById(R.id.venue1);
            sport = itemView.findViewById(R.id.sport1);
            time = itemView.findViewById(R.id.timee1);
            cap = itemView.findViewById(R.id.capacity1);
            sLeft = itemView.findViewById(R.id.spots1);
            btn = itemView.findViewById(R.id.button6);
            saverMy = itemView.findViewById(R.id.saver1);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String[] s = saverMy.getText().toString().trim().split("~~~");
                    Event[] e = {new Event(s[0], s[1], s[2], s[3], Integer.parseInt(s[4]),
                            Integer.parseInt(s[5]), Integer.parseInt(s[6]), Integer.parseInt(s[7]),
                            Integer.parseInt(s[8]), Integer.parseInt(s[9]), s[10], s[11])};



                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("Customers/" + user + "/Events");

                    String key = e[0].toString();


                   ref = database.getReference("Customers/" + user + "/Spots/" + key);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            e[0].setSpotsLeft( e[0].getSpotsLeft() +
                                    Integer.valueOf(snapshot.getValue().toString()));

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref1 = database.getReference();

                            ref1= database.getReference("Customers/" + user + "/Spots/" + key);
                            ref1.removeValue();

                            ref1 = database.getReference("Customers/" + user + "/Events/" + key);
                            ref1.removeValue();

                            ref1 = database.getReference("Admins/" + e[0].getAdmin()+ "/Venues/" +
                                    e[0].getAddress()+"/Events/" + key);
                            ref1.setValue(e[0]);



                            ref1 = database.getReference("Admins/" + e[0].getAdmin()+ "/Venues/" +
                                    e[0].getAddress()+"/Events/" + key);
                            ref1.setValue(e[0]);

                            ref1 = database.getReference("Venues/"+ e[0].getAddress() + "/Events/"+key);
                            ref1.setValue(e[0]);

                            ref1 = database.getReference("Events/" + key);
                            ref1.setValue(e[0]);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    adapter.events.remove(getAdapterPosition());
                    adapter.notifyItemRemoved(getAdapterPosition());}
            });
        }


        public MyViewHolder linkAdapter(AdapterMyEvents adapter){
            this.adapter = adapter;
            return this;
        }
    }
}
