package com.example.b07project;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AdminAllEventsFragment extends Fragment {
    ArrayList<Event> venueEvents = new ArrayList<Event>();
    AdapterEventsAdmin adapter;
//    String location, admin, vname;
//    int startH, startM, endH, endM;
//    FloatingActionButton but;
//    TextView venName;
//    Venue v;

//    public void setLocation(String loc)
//    {
//        location = loc;
//    }
//    public void setAdmin(String adm)
//    {
//        admin = adm;
//    }
//
//    public void setVname(String vname) {
//        this.vname = vname;
//    }
//
//    public void setStartH(int startH) {
//        this.startH = startH;
//    }
//
//    public void setStartM(int startM) {
//        this.startM = startM;
//    }
//
//    public void setEndH(int endH) {
//        this.endH = endH;
//    }
//
//    public void setEndM(int endM) {
//        this.endM = endM;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_admin_all_events ,container, false);
        venueEvents = new ArrayList<Event>();
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("save",MODE_PRIVATE);
        String admin = sharedPref.getString("username","f");
        adapter = new AdapterEventsAdmin(getActivity(), venueEvents, admin);

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Admins/" + admin
                + "/Venues");
        //The following code loops through the database and creates objects from the database
        ref1.addValueEventListener(new ValueEventListener() {@Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                String vloc = snapshot.getKey();

                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Admins/" + admin
                        + "/Venues/" + vloc + "/Events");
//                    System.out.println(date);
                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
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
                            String date = snapshot.child("date").getValue().toString();

                            //Eventually a sorting alorithm will go here so that the location is priority
                            Event event = new Event(admin, venueName, eventName, address, startHour, startMin, endHour, endMin, capacity, spotsLeft, location, date);
                            if (!venueEvents.contains(event)) venueEvents.add(event);

                        }
                        Collections.sort(venueEvents);
                        adapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycleAllEventsAdmin);
        //FIX HERE
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;
    }
}