package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class ViewVenue extends AppCompatActivity {
    Button butt;
    TextView value;
    Button createEvent;
    ArrayList<Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_venue);

        value = findViewById(R.id.textView2);
        String hashCode = getIntent().getStringExtra("hashCode");
        butt = findViewById(R.id.butt);

        events = new ArrayList<Event>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Events");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String date = snapshot.child("date").getValue().toString();
                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String eventName = snapshot.child("eventName").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
                    int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());

                    //Eventually a sorting alorithm will go here so that the location is priority
                    Event event = new Event(eventName, startHour,startMin,endHour,endMin,capacity,date,location,spotsLeft);
                    events.add(event);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value.setText(events.get(0).getEventName());
            }
        });
//        value.setText(hashCode);

        createEvent = findViewById(R.id.createEvent);
        createEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateEvent.class));

            }
        });
    }
}