package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class SpecificVenueAdmin extends AppCompatActivity implements Serializable {

    ArrayList<Event> venueEvents = new ArrayList<Event>();
    AdapterMyEvents adapter;

    Button Ecreate;
    TextView venName;
    Venue v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_venue_admin);
//        v = (Venue) getIntent().getParcelableExtra("venue");

        Bundle bundleObject = getIntent().getExtras();
        venueEvents = (ArrayList<Event>) bundleObject.getSerializable("venue_events");
        RecyclerView recyclerView = findViewById(R.id.recycleViewSpecificAdmin);
        adapter = new AdapterMyEvents(this, venueEvents);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Ecreate = findViewById(R.id.createE);
        venName = findViewById(R.id.VenName);
        SharedPreferences sharedPref = getSharedPreferences("venue",MODE_PRIVATE);
        String vname = sharedPref.getString("vname", "error");
        venName.setText(vname);
//        Venue ve = new Venue(v.getVenueHashCode(), v.getVenueName(), v.getStartMin())
        Ecreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SpecificVenue.this, CreateEvent.class);
//                intent.putExtra("venue", v);
//                startActivity(intent);
                startActivity(new Intent(getApplicationContext(), CreateEvent.class));

            }
        });


    }
}