package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SpecificVenueAdmin extends AppCompatActivity {
    ArrayList<Event> venueEvents = new ArrayList<Event>();
    AdapterUpcomingEvents adapter;
    Button Ecreate;
    TextView venName;
    Venue v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_venue_admin);
//        v = (Venue) getIntent().getParcelableExtra("venue");
        Bundle bundleObject = getIntent().getExtras();
//        venueEvents = (ArrayList<Event>) bundleObject.getSerializable("venue_events");
        venueEvents = new ArrayList<Event>();
        adapter = new AdapterUpcomingEvents(this, venueEvents, "", venueEvents);


        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Venues")
                .child(getIntent().getStringExtra("address")).child("Events");
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
                if(!venueEvents.contains(event)) venueEvents.add(event);

            }
            Collections.sort(venueEvents);
            adapter.notifyDataSetChanged();
        }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycleViewSpecificAdmin);
        //FIX HERE
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Ecreate = findViewById(R.id.createE);
        venName = findViewById(R.id.VenName);
        SharedPreferences sharedPref = getSharedPreferences("venue",MODE_PRIVATE);
        String vname = getIntent().getStringExtra("vname");
        venName.setText(vname);
//        Venue ve = new Venue(v.getVenueHashCode(), v.getVenueName(), v.getStartMin())
        Ecreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SpecificVenue.this, CreateEvent.class);
//                intent.putExtra("venue", v);
//                startActivity(intent);
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("venue_events", venueEvents);
                intent.putExtras(bundle);
                intent.putExtra("address", getIntent().getStringExtra("address"));
                intent.putExtra("vname", getIntent().getStringExtra("vname"));
                intent.putExtra("admin", getIntent().getStringExtra("admin"));
                intent.putExtra("vstartH", getIntent().getIntExtra("vstartH", 0));
                intent.putExtra("vstartM", getIntent().getIntExtra("vstartM", 0));

                intent.putExtra("vendH", getIntent().getIntExtra("vendH",0));

                intent.putExtra("vendM", getIntent().getIntExtra("vendM",0));


                startActivity(intent);

            }
        });


    }
}