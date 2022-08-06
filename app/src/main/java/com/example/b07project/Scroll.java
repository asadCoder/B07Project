package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Scroll extends AppCompatActivity {

    ArrayList<Event> events = new ArrayList<Event>();
    AdapterMyEvents adapterMY;
    AdapterUpcomingEvents adapterUP;
    DB_ReadEvents reader;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();

        String indicator = intent.getStringExtra("ind");
        if(indicator.equals("up")) {

            setUpEventsU(this.events);
            System.out.println("MOMENT OF TRUTH    " + this.events.size() );
            AdapterUpcomingEvents adapter =new AdapterUpcomingEvents(this, events);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
//            setUpEventsU();
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
    public void setUpEventsU(ArrayList<Event> es){
        //read events from database into events

//        events.add(new Event("soccer", 5, 7, 0, 0, 22, 22, "Emirates Stadium", "1/25/2022"));
//        events.add(new Event("basketball",  3, 0,5, 0,10, 10, "Camp Nou" , "1/23/2022"));
//        events.add(new Event("basketball", 3, 30,5, 0,10, 0, "BMO Field", "1/24/2022"));
//        events.add(new Event("basketball", 3, 0,5, 0,10, 10, "Scrum Master", "1/27/2022"));
//        events.add(new Event("tennis", 10, 0,12, 0,2, 2, "Etihad Stadium", "1/25/2022"));
//        events.add(new Event("soccer", 5, 0,7, 0,22, 2, "Anfield", "1/25/2021"));
//        events.add(new Event("tennis",  10, 0,12, 0,2, 2, "Canada Field", "1/25/2023"));
//        events.add(new Event("soccer",  5, 0,7, 0,22, 22, "UTSC Valley", "2/1/2022"));
//        events.add(new Event( "basketball", 13, 0,15, 0,10, 10, "Syria stadium", "1/25/2022"));
//        events.add(new Event("basketball",  1, 0,2, 0,10, 0, "Arena A", "1/26/2022"));
//        events.add(new Event("soccer",  5, 0,7, 0,22, 22, "Pan Am", "1/26/2022"));
//        events.add(new Event("soccer", 5, 0,7, 0,22, 2, "Arena B", "1/24/2022"));
//        events.add(new Event("soccer",  12, 0,15, 0,22, 22, "Arena C", "11/25/2022"));
//        events.add(new Event( "soccer", 5, 0,7, 0,22, 22, "Arena D", "11/25/2022"));
//        Collections.sort(events);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Events");
        //The following code loops through the database and creates objects from the database
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    System.out.println("hi");
                    String date = snapshot.child("date").getValue().toString();
                    System.out.println(date);
                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String eventName = snapshot.child("eventName").getValue().toString();
                    System.out.println(eventName);
                    String location = snapshot.child("location").getValue().toString();
                    System.out.println(location);
                    int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
                    int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());

                    //Eventually a sorting alorithm will go here so that the location is priority
                    Event event = new Event(eventName, startHour,startMin,endHour,endMin,capacity,location,date);
                    System.out.println("I'm HEREEEE" + event.toString());
                    es.add(event);
                    System.out.println(" " + es.size());



                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        System.out.println("EVENT 1:  " + es.get(0).toString());

    }
}