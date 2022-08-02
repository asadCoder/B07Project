package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public class ViewVenuesAdmin extends AppCompatActivity implements ViewVenuesInterface, RecycleViewInterface{
    ArrayList<Venue> venues = new ArrayList<>();
    AdapterVenues adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_venues_admin);

        RecyclerView recyclerView  = findViewById(R.id.recycleViewVenueAdmin);

        //pass the list of venues of the admin from the database to setUpVenues()
        setUpVenues();

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10);
        //recyclerView.addItemDecoration(itemDecorator);
        adapter = new AdapterVenues(this, venues, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setUpVenues() {
        ArrayList<String> sportsAll = new ArrayList<String>();
        sportsAll.add("soccer");
        sportsAll.add("football");

        HashSet<Event> eventsAll = new HashSet<Event>() {};
        eventsAll.add(new Event("Emirates Stadium", 8,  0, 10,  5, 7, "july", "panam"));
        eventsAll.add(new Event("Bernabue", 8,  0, 10,  5, 7, "july", "panam"));

        //read venues from from database
        venues.add(new Venue("Pan am ", "Pan am", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));
        venues.add(new Venue("pan am 2", "Pan am 2", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));
    }

    @Override
    public void onItemClick(int position) {

    }
}