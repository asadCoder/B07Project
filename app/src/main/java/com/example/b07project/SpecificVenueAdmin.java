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
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SpecificVenueAdmin extends Fragment {
    ArrayList<Event> venueEvents = new ArrayList<Event>();
    AdapterEventsAdmin adapter;
    String location, admin, vname;
    int startH, startM, endH, endM;
    FloatingActionButton but, backbut;
    TextView venName;
    Venue v;


    public void setLocation(String loc)
    {
        location = loc;
    }
    public void setAdmin(String adm)
    {
        admin = adm;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setStartH(int startH) {
        this.startH = startH;
    }

    public void setStartM(int startM) {
        this.startM = startM;
    }

    public void setEndH(int endH) {
        this.endH = endH;
    }

    public void setEndM(int endM) {
        this.endM = endM;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.activity_specific_venue_admin, container, false);
        venueEvents = new ArrayList<Event>();
        adapter = new AdapterEventsAdmin(getActivity(), venueEvents, admin);
        but = mView.findViewById(R.id.floatingbut2);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEvent.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("venue_events", venueEvents);
                intent.putExtras(bundle);
                intent.putExtra("address", location);
                intent.putExtra("vname", vname);
                intent.putExtra("admin", admin);
                intent.putExtra("vstartH", startH);
                intent.putExtra("vstartM",startM);
                intent.putExtra("vendM",endM);
                intent.putExtra("vendH",endH);

                startActivity(intent);
            }
        });

        backbut = mView.findViewById(R.id.BackBut);
        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminMasterActivity.class));
            }
        });
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Admins/" + admin
                + "/Venues/" + location + "/Events");
        ref1.addValueEventListener(new ValueEventListener() {@Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                System.out.println(snapshot.toString());

                String date = snapshot.child("date").getValue().toString();
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

                Event event = new Event(admin, venueName, eventName, address, startHour,startMin,endHour,endMin,capacity,spotsLeft,location,date);
                if(!venueEvents.contains(event)) venueEvents.add(event);

            }
            Collections.sort(venueEvents);
            adapter.notifyDataSetChanged();
        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycleViewSpecificAdmin);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;
    }
}