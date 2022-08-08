package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ViewVenuesUser extends Fragment implements ViewVenuesInterface, RecycleViewInterface, Serializable {

    ArrayList<Venue> venues = new ArrayList<>();
    AdapterVenues adapter;
    String user;

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.activity_view_venues_user, container, false);


        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycleViewVenueUser);

        venues = new ArrayList<Venue>();
        adapter = new AdapterVenues(getActivity(), venues, this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Venues");
        //The following code loops through the database and creates objects from the database
        ref.addValueEventListener(new ValueEventListener() {@Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                String venueName = snapshot.child("venueName").getValue().toString();
                String location = snapshot.child("location").getValue().toString();
                String admin = snapshot.child("admin").getValue().toString();
                //Eventually a sorting alorithm will go here so that the location is priority
                Venue venue = new Venue(admin, venueName, startHour,startMin,endHour,endMin, location,new ArrayList<Event>());
                if(!venues.contains(venue))  venues.add(venue);

            }
            Collections.sort(venues);
            adapter.notifyDataSetChanged();
        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //pass the list of venues from the database to setUpVenues()
//        setUpVenues();

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;
    }



    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(getActivity(), SpecificVenueUser.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("venue_events", venues.get(position).getEvents());
//        intent.putExtras(bundle);
//        startActivity(intent);
        String venueName = venues.get(position).getLocation();
        SpecificVenueUser svu = new SpecificVenueUser();
        svu.setUser(user);
        svu.setVenueName(venueName);
        getParentFragmentManager().beginTransaction().replace(R.id.container, svu).commit();

    }

    @Override
    public void setUpVenues() {


        ArrayList<Event> eventsAll = new ArrayList<Event>() {};
        eventsAll.add(new Event("", "pan am","Soccer","military", 8,  0, 10,  5, 7,7, "court 4","7/7/2022"));
        eventsAll.add(new Event("", "pan am", "Golf", "military",8,  0, 10,  5, 7, 7, "court 13", "9/9/2022"));

        //read venues from from database
        venues.add(new Venue("ahmad", "Pan am", 1, 0,  4, 0, "morningside avneue", eventsAll));
        venues.add(new Venue("ahmad","drake smd", 1, 0,  4, 0, "morningside avneue", eventsAll));
        venues.add(new Venue("ahmad","no name", 1, 0,  4, 0, "morningside avneue", eventsAll));
//        venues.add(new Venue("please word", 1, 0,  4, 0,"morningside avneue", eventsAll));
//        venues.add(new Venue("ronaldo goat ", 1, 0,  4, 0,  "morningside avneue", eventsAll));
    }
}