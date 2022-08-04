package com.example.b07project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Scroll extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<Event>();
    AdapterMyEvents adapterMY;
    AdapterUpcomingEvents adapterUP;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();

        String indicator = intent.getStringExtra("ind");
        if(indicator.equals("up")) {
            setUpEvents();
            AdapterUpcomingEvents adapter =new AdapterUpcomingEvents(this, events);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            setUpEvents();
            AdapterMyEvents adapter = new AdapterMyEvents(this, events);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

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
    private void setUpEvents(){
        //read events from database into events
        LocalDate date1 = LocalDate.of(2022, 8, 2);
        LocalDate date2 = LocalDate.of(2022, 8, 3);
        LocalDate date3 = LocalDate.of(2022, 8, 4);
        LocalDate date4 = LocalDate.of(2022, 8, 5);
        events.add(new Event("dropIn", "Emirates Stadium", "soccer", 5, 7, 0, 0, 22, 22, date2));
        events.add(new Event("dropIn", "bbb", "basketball",  3, 0,5, 0,10, 10, date1 ));
        events.add(new Event("dropIn","ddd", "basketball", 3, 30,5, 0,10, 0, date2));
        events.add(new Event("dropIn","eee", "basketball", 3, 0,5, 0,10, 10, date2));
        events.add(new Event("dropIn","fff", "tennis", 10, 0,12, 0,2, 2, date3));
        events.add(new Event("dropIn","ggg", "soccer", 5, 0,7, 0,22, 2, date3));
        events.add(new Event("dropIn","hhh", "tennis",  10, 0,12, 0,2, 2, date4));
        events.add(new Event("dropIn","iii", "soccer",  5, 0,7, 0,22, 22, date4));
        events.add(new Event("dropIn","jjj", "basketball", 13, 0,15, 0,10, 10, date4));
        events.add(new Event("dropIn","kkk", "basketball",  1, 0,2, 0,10, 0, date4));
        events.add(new Event("dropIn","PANAM", "soccer",  5, 0,7, 0,22, 22, date1));
        events.add(new Event("dropIn","JALAA", "soccer", 5, 0,7, 0,22, 2, date2));
        events.add(new Event("dropIn","ISKAN", "soccer",  12, 0,15, 0,22, 22, date3));
        events.add(new Event("dropIn","Camp Nou", "soccer", 5, 0,7, 0,22, 22, date4));
        Collections.sort(events);
    }
}