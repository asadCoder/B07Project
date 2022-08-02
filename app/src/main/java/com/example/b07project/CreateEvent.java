package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
    Venue v;
//    TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        SharedPreferences sharedPref = getSharedPreferences("venue",MODE_PRIVATE);
        String vname = sharedPref.getString("vname", "error");
        int vStartH = sharedPref.getInt("vstartH", -1);
        int vStartM = sharedPref.getInt("vstartM", -1);
        int vEndH = sharedPref.getInt("vendH", -1);
        int vEndM = sharedPref.getInt("vendM", -1);
        SharedPreferences sharedPref2 = getSharedPreferences("save",MODE_PRIVATE);
        String user = sharedPref2.getString("username","f");
        ref = FirebaseDatabase.getInstance().getReference();
        event = new Event();

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

        createEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventn = eventName.getText().toString().trim();
                String eventloc = location.getText().toString().trim();

                if(TextUtils.isEmpty(eventn) || TextUtils.isEmpty(eventloc) || TextUtils.isEmpty(maxcap.getText().toString().trim()) || !stime || !etime){
                    Toast.makeText(CreateEvent.this,"No fields can be empty", Toast.LENGTH_SHORT).show();
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
                    capacity = Integer.parseInt(maxcap.getText().toString().trim());
                    event.setCapacity(capacity);
                    event.setEventName(eventn);
                    event.setLocation(eventloc);
                    ref.child("Admins").child(user).child("Venues").child(vname).child("Events").child(event.getEventName()).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CreateEvent.this, "Event added", Toast.LENGTH_SHORT).show();
                        }
                    });

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