package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Scroll extends Fragment {

    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Event> myEvents = new ArrayList<Event>();
    AdapterUpcomingEvents adapterUP;
    DB_ReadEvents reader;
    String user;

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_scroll, container, false);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

        //  events =  setUpEventsU();
        events = new ArrayList<Event>();
        myEvents = new ArrayList<Event>();

        adapterUP = new AdapterUpcomingEvents(getActivity(), events, user, myEvents);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Events");
        //The following code loops through the database and creates objects from the database
        ref1.addValueEventListener(new ValueEventListener() {@Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                System.out.println(snapshot.toString());

                    String date = snapshot.child("date").getValue().toString();
//                    System.out.println(date);
                int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                String eventName = snapshot.child("eventName").getValue().toString();
                String venueName = snapshot.child("venueName").getValue().toString();
                String address = snapshot.child("address").getValue().toString();
                String admin = snapshot.child("admin").getValue().toString();
                String location = snapshot.child("location").getValue().toString();
                int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
                int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());

                //Eventually a sorting alorithm will go here so that the location is priority
                Event event = new Event(admin, venueName, eventName, address, startHour,startMin,endHour,endMin,capacity,spotsLeft,location,date);
                if(!events.contains(event)) adapterUP.events.add(event);

            }
            Collections.sort(events);
            adapterUP.notifyDataSetChanged();
        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Customers/" + user + "/Events");
        //The following code loops through the database and creates objects from the database
        ref2.addValueEventListener(new ValueEventListener() {@Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                String date = snapshot.child("date").getValue().toString();
//                    System.out.println(date);
                int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                String eventName = snapshot.child("eventName").getValue().toString();
                String venueName = snapshot.child("venueName").getValue().toString();
                String address = snapshot.child("address").getValue().toString();
                String admin = snapshot.child("admin").getValue().toString();

                String location = snapshot.child("location").getValue().toString();
                int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
                int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());

                //Eventually a sorting alorithm will go here so that the location is priority
                Event event = new Event(admin, venueName, eventName, address, startHour,startMin,endHour,endMin,capacity,spotsLeft,location,date);
                if(!myEvents.contains(event)) myEvents.add(event);
                for(Event e : myEvents){
                    if(e.changes(event)) {
//                        adapterUP.myEvents.remove(e);
                        adapterUP.myEvents.add(event);
//                        adapterUP.notifyDataSetChanged();
                    };
                }

            }
            Collections.sort(events);
            adapterUP.notifyDataSetChanged();
        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        recyclerView.setAdapter(adapterUP);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;

    }

}