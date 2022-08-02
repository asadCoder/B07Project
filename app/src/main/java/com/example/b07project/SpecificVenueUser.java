package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SpecificVenueUser extends AppCompatActivity {

    ArrayList<Event> venueEvents = new ArrayList<Event>();

    //have to wait for ahmads code for this to work
 /*   AdapterMyEvents adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_venue_user);

        Bundle bundleObject = getIntent().getExtras();
        venueEvents = (ArrayList<Event>) bundleObject.getSerializable("venue_events");
        setContentView(R.layout.activity_specific_venue_user);
        RecyclerView recyclerView = findViewById(R.id.recycleSpecificVenueUser);
        adapter = new AdapterMyEvents(this, venueEvents);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }*/
}