package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    int startTime, endTime, capacity;
    Button sTime, eTime;
    Button createEve;
    int shour, sminute;
    int ehour, eminute;
    TextView eventName, location, maxcap;
    DatabaseReference ref;
    boolean stime;
    boolean etime;
    Event event;
    ArrayList<Event> events;
    private TextView Date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Venue v;
//    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        SharedPreferences sharedPref = getSharedPreferences("venue",MODE_PRIVATE);
        String vname = sharedPref.getString("vname", "error");
        String vloc = sharedPref.getString("vlocation", "error");
        int vStartH = sharedPref.getInt("vstartH", -1);
        int vStartM = sharedPref.getInt("vstartM", -1);
        int vEndH = sharedPref.getInt("vendH", -1);
        int vEndM = sharedPref.getInt("vendM", -1);
        SharedPreferences sharedPref2 = getSharedPreferences("save",MODE_PRIVATE);
        String user = sharedPref2.getString("username","f");
        ref = FirebaseDatabase.getInstance().getReference("Events");
        events = new ArrayList<Event>();
        event = new Event();
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
                    Event e = new Event(eventName, startHour,startMin,endHour,endMin,capacity,date,location,spotsLeft);
                    events.add(e);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref = FirebaseDatabase.getInstance().getReference();

//        ArrayList<Event> events = new ArrayList<Event>();
//        DB_ReadEvents reader = new DB_ReadEvents();
//        events = reader.readEvents("Admins/dhruv/Venues/Pan Am/Events");
//        temp =findViewById(R.id.textView);
//        temp.setText(events.get(0).eventName);
        sTime = findViewById(R.id.startTime);
        eTime = findViewById(R.id.endTime);
        eventName = findViewById(R.id.EventName);
        location = findViewById(R.id.Elocation);
        maxcap = findViewById(R.id.Ecapacity);
        createEve = findViewById(R.id.Ecreate);
        Date = findViewById(R.id.Date);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                event.date = date;
                Date.setText(date);
            }
        };

        createEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventn = eventName.getText().toString().trim();
                String eventloc = location.getText().toString().trim();
                capacity = Integer.parseInt(maxcap.getText().toString().trim());
                event.setCapacity(capacity);
                event.setSpotsLeft(capacity);
                event.setEventName(eventn);
                event.setLocation(eventloc);
                boolean b = false;
                for(Event e: events){
                    if(e.equals(event)){
                        b = true;
                    }
                }
                if(TextUtils.isEmpty(eventn) || TextUtils.isEmpty(event.getDate())|| TextUtils.isEmpty(eventloc) || TextUtils.isEmpty(maxcap.getText().toString().trim()) || !stime || !etime){
                    Toast.makeText(CreateEvent.this,"No fields can be empty", Toast.LENGTH_SHORT).show();
                }
                else if(b){
                    Toast.makeText(CreateEvent.this,"Event already exists", Toast.LENGTH_SHORT).show();
                }
                else if(event.getStartHour()>event.getEndHour() || ((event.getStartHour()==event.getEndHour()) && event.getStartMin()>=event.getEndMin())){
                    Toast.makeText(CreateEvent.this,"Enter valid start and end times", Toast.LENGTH_SHORT).show();
                }
                else if(event.getStartHour()<vStartH || (event.getStartHour()==vStartH && event.getStartMin()<vStartM)){
                    Toast.makeText(CreateEvent.this,"Start time needs to be after " + vname + " opens", Toast.LENGTH_SHORT).show();
                }
                else if(event.getEndHour()>vEndH || (event.getEndHour()==vEndH && event.getEndMin()>vEndM)){
                    Toast.makeText(CreateEvent.this,"End time needs to be before " + vname + " closes", Toast.LENGTH_SHORT).show();
                }
                else{

                    ref.child("Admins").child(user).child("Venues").child(vloc).child("Events").push().setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateEvent.this, "Event added", Toast.LENGTH_SHORT).show();
                        }
                    });

                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Events");
                    reference2.push().setValue(event);
                    DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Venues");
                    reference3.child(vloc).child("Events").push().setValue(event);
                    startActivity(new Intent(getApplicationContext(), SpecificVenue.class));

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
                event.setStartHour(shour);
                event.setStartMin(sminute);
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
                event.setEndHour(ehour);
                event.setEndMin(eminute);

            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog picker = new TimePickerDialog(this, style, onTimeSetListener, ehour, eminute, true);
        picker.setTitle("Select End Time");
        picker.show();

    }
}