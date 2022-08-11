package com.example.b07project;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class ViewVenuesAdmin extends Fragment implements ViewVenuesInterface, RecycleViewInterface{
    ArrayList<Venue> venues = new ArrayList<>();;
    DatabaseReference ref;
    AdapterVenues adapter;
    String admin;
    FloatingActionButton but;

    public void setAdmin(String adm)
    {
        admin = adm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.activity_view_venues_admin, container, false);

        RecyclerView recyclerView  = (RecyclerView) mView.findViewById(R.id.recycleViewVenueAdmin);


        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10);

       but = mView.findViewById(R.id.floatingbut1);
       but.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getContext(), CreateVenue.class));
           }
       });
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("save",MODE_PRIVATE);
        boolean isadmin = sharedPref.getBoolean("value",false);
        if (!isadmin){
            startActivity(new Intent(getActivity(), CustomerMain.class));
        }
        
        venues = new ArrayList<Venue>();

        String use = sharedPref.getString("username","f");
        adapter = new AdapterVenues(getActivity(), venues, this);

        ref = FirebaseDatabase.getInstance().getReference().child("Admins/"+use+"/Venues");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String venueName = snapshot.child("venueName").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();

                    Venue obj = new Venue(use, venueName, startHour, startMin, endHour, endMin, location, new ArrayList<Event>());
                    if(!venues.contains(obj)){
                        venues.add(obj);
                    }
                }
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

    @Override
    public void setUpVenues() {


        ArrayList<Event> eventsAll = new ArrayList<Event>() {};
        eventsAll.add(new Event(" ","pan am","Soccer","military", 8,  0, 10,  5, 7,7, "court 4","7/7/2022"));
        eventsAll.add(new Event(" ","pan am", "Golf","military", 8,  0, 10,  5, 7, 7, "court 13", "9/9/2022"));
        venues.add(new Venue("ahmad", "Pan am ", 1, 0,  4, 0, "morningside avneue", eventsAll));
        venues.add(new Venue("admin", "pan am 2", 1, 0,  4, 0, "morningside avneue", eventsAll));
    }

    @Override
    public void onItemClick(int position) {

        Venue v = venues.get(position);
        SpecificVenueAdmin sva = new SpecificVenueAdmin();
        sva.setAdmin(v.getAdmin());
        sva.setLocation(v.getLocation());
        sva.setVname(v.getVenueName());
        sva.setStartM(v.getStartMin());
        sva.setStartH(v.getStartHour());
        sva.setEndM(v.getEndMin());
        sva.setEndH(v.getEndHour());
        getParentFragmentManager().beginTransaction().replace(R.id.container, sva).commit();
    }
}