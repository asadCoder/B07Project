package com.example.b07project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ViewVenuesUser extends  AppCompatActivity implements ViewVenuesInterface, RecycleViewInterface, Serializable {

    ArrayList<Venue> venues = new ArrayList<>();
    AdapterVenues adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_venues_user);

//        bottomNavigationView  = findViewById(R.id.bottom_navigation);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, myEvents).commit();
//
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.venues:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myEvents).commit();
//                        return true;
//                    case R.id.upcoming:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
//                        return true;
//                    case R.id.myevents:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
//                        return true;
//                }
//
//                return false;
//            }
//        });

        RecyclerView recyclerView  = findViewById(R.id.recycleViewVenueUser);

        //pass the list of venues from the database to setUpVenues()
        setUpVenues();

        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10);
        //recyclerView.addItemDecoration(itemDecorator);
        adapter = new AdapterVenues(this, venues, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void clickUp(View view){
        Intent intent = new Intent(getApplicationContext(), Scroll.class);
        intent.putExtra("ind", "up");
        startActivity(intent);
    }

    public void clickMy(View view){
        Intent intent = new Intent(getApplicationContext(), Scroll.class);
        intent.putExtra("ind", "my");
        startActivity(intent);
    }

    public void clickV(View view){
        Intent intent = new Intent(getApplicationContext(), ViewVenuesUser.class);
        startActivity(intent);
    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setUpVenues() {
        ArrayList<String> sportsAll = new ArrayList<String>();
        sportsAll.add("soccer");
        sportsAll.add("football");

        ArrayList<Event> eventsAll = new ArrayList<Event>() {};
        LocalDate date3 = LocalDate.of(2022, 8, 4);
        LocalDate date4 = LocalDate.of(2022, 8, 5);
        eventsAll.add(new Event("dropIn","ISKAN", "soccer",  12, 15, 0, 0,22, 22, date3));
        eventsAll.add(new Event("dropIn","Camp Nou", "soccer", 5, 7, 0, 0,22, 22, date4));

        //read venues from from database
        venues.add(new Venue("somehaschode", "Pan am", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));
        venues.add(new Venue("somehaschode", "drake smd", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));
        venues.add(new Venue("somehaschode", "no name", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));
        venues.add(new Venue("somehaschode", "please word", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));
        venues.add(new Venue("somehaschode", "ronaldo goat ", 1, 0,  4, 0, "august", "morningside avneue", eventsAll));

    }

   @Override
    public void onItemClick(int position) {
       Intent intent = new Intent(ViewVenuesUser.this, SpecificVenueUser.class);
       Bundle bundle = new Bundle();
       bundle.putSerializable("venue_events", venues.get(position).getEvents());
       intent.putExtras(bundle);
       startActivity(intent);
    }
}