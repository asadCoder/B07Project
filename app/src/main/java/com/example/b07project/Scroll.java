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
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Scroll extends Fragment {


    AdapterUpcomingEvents adapterUP;
    DB_ReadEvents reader;
    ArrayList<Event> events = new ArrayList<Event>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapterUP = new AdapterUpcomingEvents(getActivity(), events);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events");
        //  The following code loops through the database and creates objects from the database
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    // System.out.println("hi");
                    String date = snapshot.child("date").getValue().toString();
                    //    System.out.println(date);
                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String venueName = snapshot.child("venueName").getValue().toString();
                    String eventName = snapshot.child("eventName").getValue().toString();
                    //    System.out.println(eventName);
                    String location = snapshot.child("location").getValue().toString();
                    //    System.out.println(location);
                    int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
                    int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());
//                    int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());

                    //Eventually a sorting alorithm will go here so that the location is priority
                    Event event = new Event(venueName, eventName, startHour,startMin,endHour,endMin,capacity,spotsLeft,date, location);
                    if(!events.contains(event))
                    {
                        System.out.println("I'm HEREEEE" + event.toString());
                        events.add(event);
                        System.out.println(" " + events.size());
                    }
                }
                adapterUP.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        View mView = inflater.inflate(R.layout.activity_scroll, container, false);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

      //  events =  setUpEventsU();



        System.out.println("SIZE: " + events.size());


        recyclerView.setAdapter(adapterUP);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;

//        Intent intent = getIntent();

//        String indicator = intent.getStringExtra("ind");
//        if(indicator.equals("up")) {
//
//            setUpEventsU(this.events);
//            AdapterUpcomingEvents adapter =new AdapterUpcomingEvents(this, events);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        }
//        else{
////           setUpEventsU();
//            AdapterMyEvents adapter = new AdapterMyEvents(this, events);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        }

    }

    public void setUpEventsU()
    {
   // public ArrayList<Event> setUpEventsU(){
//
//        ArrayList<Event> newEvents = new ArrayList<>();
//
//        //read events from database into events
//        newEvents.add(new Event("soccer", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("basketball", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("football", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("rugby", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("badminton ", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("table tennis", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("polo", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//        newEvents.add(new Event("horse racing", 9, 0, 11, 0, 22, "1/8/2022", "victoria park"));
//
//        return newEvents;


        //Collections.sort(events);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events");
//        The following code loops through the database and creates objects from the database
//        ref.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                   // System.out.println("hi");
//                    String date = snapshot.child("date").getValue().toString();
                //    System.out.println(date);
//                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
//                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
//                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
//                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
//                    String eventName = snapshot.child("eventName").getValue().toString();
                //    System.out.println(eventName);
//                    String location = snapshot.child("location").getValue().toString();
                //    System.out.println(location);
//                    int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
//                    int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());
//
//                    //Eventually a sorting alorithm will go here so that the location is priority
//                    Event event = new Event(eventName, startHour,startMin,endHour,endMin,capacity,location,date);
//                    if(!events.contains(event))
//                    {
//                        System.out.println("I'm HEREEEE" + event.toString());
//                        events.add(event);
//                        System.out.println(" " + events.size());
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
        //System.out.println("EVENT 1:  " + es.get(0).toString());

    }
}