package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SpecificVenueUser extends Fragment implements Serializable {

    ArrayList<Event> venueEvents = new ArrayList<Event>();
    ArrayList<Event> myEvents = new ArrayList<Event>();

    //have to wait for ahmads code for this to work
    AdapterUpcomingEvents adapter;
    //FIX HERE
    String user, venueName;

    public void setUser(String user) {
        this.user = user;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.activity_specific_venue_user, container, false);

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycleSpecificVenueUser);

        venueEvents = new ArrayList<Event>();
        myEvents = new ArrayList<Event>();

        adapter = new AdapterUpcomingEvents(getActivity(), venueEvents, user, myEvents);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Venues/" + venueName + "/Events");
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
                if(!venueEvents.contains(event)) adapter.events.add(event);

            }
            Collections.sort(venueEvents);
            adapter.notifyDataSetChanged();
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
                        adapter.myEvents.add(event);
//                        adapterUP.notifyDataSetChanged();
                    };
                }

            }
            Collections.sort(venueEvents);
            adapter.notifyDataSetChanged();
        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;
    }



}