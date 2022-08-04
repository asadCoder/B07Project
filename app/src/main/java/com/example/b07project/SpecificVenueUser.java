package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

public class SpecificVenueUser extends AppCompatActivity implements Serializable {

    ArrayList<Event> venueEvents = new ArrayList<Event>();

    //have to wait for ahmads code for this to work
    AdapterUpcomingEvents adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_venue_user);

        Bundle bundleObject = getIntent().getExtras();
        venueEvents = (ArrayList<Event>) bundleObject.getSerializable("venue_events");
        setContentView(R.layout.activity_specific_venue_user);
        RecyclerView recyclerView = findViewById(R.id.recycleSpecificVenueUser);
        adapter = new AdapterUpcomingEvents(this, venueEvents);
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
}