package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ViewVenuesUser extends Fragment implements ViewVenuesInterface, RecycleViewInterface, Serializable {

    ArrayList<Venue> venues = new ArrayList<>();
    AdapterVenues adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.activity_view_venues_user, container, false);


        RecyclerView recyclerView  = (RecyclerView)mView.findViewById(R.id.recycleViewVenueUser);

        //pass the list of venues from the database to setUpVenues()
        setUpVenues();

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        adapter = new AdapterVenues(getActivity(), venues, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mView;


    }

    @Override
    public void setUpVenues() {
        ArrayList<String> sportsAll = new ArrayList<String>();
        sportsAll.add("soccer");
        sportsAll.add("football");

        ArrayList<Event> eventsAll = new ArrayList<Event>() {};
        eventsAll.add(new Event("ya hala", "Soccer", 8,  0, 10,  5, 7,7, "11/11/2022", "panam"));
        eventsAll.add(new Event("ya hala","basketball", 8,  0, 10,  5, 7,5, "12/12/2022", "panam"));

        //read venues from from database
        venues.add(new Venue("Pan am", 1, 0,  4, 0, "morningside avneue", eventsAll));
        venues.add(new Venue("drake smd", 1, 0,  4, 0, "morningside avneue", eventsAll));
        venues.add(new Venue("no name", 1, 0,  4, 0, "morningside avneue", eventsAll));
        venues.add(new Venue("please word", 1, 0,  4, 0,"morningside avneue", eventsAll));
        venues.add(new Venue("ronaldo goat ", 1, 0,  4, 0,  "morningside avneue", eventsAll));

    }

   @Override
    public void onItemClick(int position) {
       Intent intent = new Intent(getActivity(), SpecificVenueUser.class);
       Bundle bundle = new Bundle();
       bundle.putSerializable("venue_events", venues.get(position).getEvents());
       intent.putExtras(bundle);
       startActivity(intent);
    }
}