package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpecificVenue extends AppCompatActivity {
    Button Ecreate;
    TextView venName;
    Venue v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_venue);
        v = (Venue) getIntent().getParcelableExtra("venue");
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