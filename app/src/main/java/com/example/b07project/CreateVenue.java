package com.example.b07project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateVenue extends AppCompatActivity {
    Button sTime, eTime;
    int shour, sminute;
    int ehour, eminute;
    Venue venue;
    TextView location;
    TextView vename;
    boolean stime;
    boolean etime;
    Admin a;
    ArrayList<Venue> venues;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button createbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_venue);
        createbut = findViewById(R.id.CV);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Venues");
        venue = new Venue();
        vename = findViewById(R.id.Vename);

        location = findViewById(R.id.Vlocation);
        sTime = findViewById(R.id.startTime);
        eTime = findViewById(R.id.endTime);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Venues");
        venues = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String venueName = snapshot.child("venueName").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();

                    //Eventually a sorting alorithm will go here so that the location is priority
                    Venue obj = new Venue( venueName, startHour, startMin, endHour, endMin, location, null);
                    venues.add(obj);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        createbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String venuename = vename.getText().toString().trim();
                String loc = location.getText().toString().trim();
                Boolean b = false;
                venue.setVenueName(venuename);
                venue.setLocation(loc);
                for(Venue v : venues){
                    if(v.equals(venue)){
                        b=true;
                    }
                }
                if(TextUtils.isEmpty(venuename) || TextUtils.isEmpty(loc) || !stime || !etime){
                    Toast.makeText(CreateVenue.this,"No fields can be empty", Toast.LENGTH_SHORT).show();
                }
                else if(b){
                    Toast.makeText(CreateVenue.this,"Venue already exists", Toast.LENGTH_SHORT).show();
                }
                else if(venue.getStartHour()>venue.getEndHour() || ((venue.getStartHour()==venue.getEndHour()) && venue.getStartMin()>=venue.getEndMin())){
                    Toast.makeText(CreateVenue.this,"Enter valid start and end times", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Add to users set of created events


//                    ArrayList<Venue> ss = new ArrayList<Venue>();
//                    ss.add(venue);
//                    a.setVenues(ss);
                    reference.child(venue.getLocation()).setValue(venue).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateVenue.this, "Venue added", Toast.LENGTH_SHORT).show();
                        }
                    });
                    SharedPreferences sharedPref = getSharedPreferences("save",MODE_PRIVATE);
                    String use = sharedPref.getString("username","false");
                    DatabaseReference reference2 = database.getReference("Admins/"+use);
                    reference2.child("Venues").child(venue.getLocation()).setValue(venue);

                    startActivity(new Intent(getApplicationContext(), AdminMain.class));
                }
            }
        });

    }
    public void popTimePicker1(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                shour = hour;
                sminute = minutes;
                sTime.setText(String.format(Locale.getDefault(), "%02d:%02d", shour, sminute));
                stime = true;
                venue.setStartHour(shour);
                venue.setStartMin(sminute);


            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog picker = new TimePickerDialog(this, style, onTimeSetListener, shour, sminute, true);

        picker.setTitle("Select Start Time");
        picker.show();

    }

    public void popTimePicker2(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                ehour = hour;
                eminute = minutes;
                eTime.setText(String.format(Locale.getDefault(), "%02d:%02d", ehour, eminute));
                etime = true;
                venue.setEndHour(ehour);
                venue.setEndMin(eminute);
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog picker = new TimePickerDialog(this, style, onTimeSetListener, ehour, eminute, true);
        picker.setTitle("Select End Time");
        picker.show();

    }


}
