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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminMain extends AppCompatActivity implements ViewVenuesInterface, RecycleViewInterface, Serializable {
    RecyclerView recyclerView;
    AdapterVenues myadapter;
    Button createV;
    Button viewV;
    Button createEtemp;
    DatabaseReference ref;
    ArrayList<Venue> venues = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        SharedPreferences sharedPref = getSharedPreferences("save",MODE_PRIVATE);
        boolean isadmin = sharedPref.getBoolean("value",false);
        if (!isadmin){
            startActivity(new Intent(getApplicationContext(), CustomerMain.class));
        }
        String use = sharedPref.getString("username","f");
        createV = findViewById(R.id.CreateVenue);
        createV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateVenue.class);
//                intent.putExtra("address", venues.get(0).getLocation());
                intent.putExtra("username", use);
                startActivity(intent);
                //Link to Create Venues page but set to an arbitary venue for now

            }
        });

        //The following method will be triggered when any venue is clicked
        viewV = findViewById(R.id.ViewVenue);
        viewV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMain.this, ViewVenue.class);
                intent.putExtra("address", venues.get(0).getLocation());
                startActivity(intent);
            }
        });
        myadapter = new AdapterVenues(this, venues, this);

        ref = FirebaseDatabase.getInstance().getReference().child("Admins/"+use+"/Venues");
        //The following code loops through the database and creates objects from the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
//                    String hashCode = snapshot.getKey();
//                    System.out.println(hashCode);
//                    String date = snapshot.child("date").getValue().toString();
                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String venueName = snapshot.child("venueName").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();

                    //Eventually a sorting alorithm will go here so that the location is priority
                    Venue obj = new Venue(use, venueName, startHour, startMin, endHour, endMin, location, new ArrayList<Event>());
                    if(!venues.contains(obj)){
                        venues.add(obj);
                    }
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        recyclerView = findViewById(R.id.Venuelist);
//        myadapter = new Myadapter(this,venues);
        recyclerView.setAdapter(myadapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //uncomment incase create event
//        createEtemp = findViewById(R.id.CreaEvent);
//        createEtemp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), CreateEvent.class));
//            }
//        });

    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

//    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(this, SpecificVenueAdmin.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("venue_events", venues.get(position).getEvents());
//        intent.putExtras(bundle);
//        intent.putExtra("address", venues.get(position).getLocation());
//        intent.putExtra("vname", venues.get(position).getVenueName());
//        intent.putExtra("admin", venues.get(position).getAdmin());
//        intent.putExtra("vstartH", venues.get(position).getStartHour());
//        intent.putExtra("vstartM", venues.get(position).getStartMin());
//        intent.putExtra("vendH", venues.get(position).getEndHour());
//        intent.putExtra("vendM", venues.get(position).getEndMin());
//
//        startActivity(intent);

    }

    @Override
    public void setUpVenues() {

//        ArrayList<String> sportsAll = new ArrayList<String>();
//        sportsAll.add("soccer");
//        sportsAll.add("football");
//
//        ArrayList<Event> eventsAll = new ArrayList<Event>() {};
//        eventsAll.add(new Event("Emirates Stadium", 8,  0, 10,  5, 7, "july", "panam"));
//        eventsAll.add(new Event("Bernabue", 8,  0, 10,  5, 7, "july", "panam"));
//
//        //read venues from from database
//        venues.add(new Venue("Pan am", 1, 0,  4, 0, "morningside avneue", eventsAll));
//        venues.add(new Venue("drake smd", 1, 0,  4, 0, "morningside avneue", eventsAll));
//        venues.add(new Venue("no name", 1, 0,  4, 0, "morningside avneue", eventsAll));
//        venues.add(new Venue("please word", 1, 0,  4, 0,"morningside avneue", eventsAll));
//        venues.add(new Venue("ronaldo goat ", 1, 0,  4, 0,  "morningside avneue", eventsAll));
    }
}