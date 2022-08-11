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

public class ViewVenuesUser extends Fragment implements  RecycleViewInterface, Serializable {

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


        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(0);
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;
    }



    @Override
    public void onItemClick(int position) {
        String venueName = venues.get(position).getLocation();
        SpecificVenueUser svu = new SpecificVenueUser();
        svu.setUser(user);
        svu.setVenueName(venueName);
        getParentFragmentManager().beginTransaction().replace(R.id.container, svu).commit();

    }

}