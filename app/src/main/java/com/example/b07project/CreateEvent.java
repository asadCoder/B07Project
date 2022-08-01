package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    int startTime, endTime, capacity;
    Button sTime, eTime;
    Button createEve;
    int shour, sminute;
    int ehour, eminute;
    TextView eventName, location, maxcap;
    boolean stime;
    boolean etime;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        event = new Event();

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
                    //Also execute this if it the time isn't between start and end time of venue
                }
                else{
                    capacity = Integer.parseInt(maxcap.getText().toString().trim());
                    event.setCapacity(capacity);
                    event.setEventName(eventn);
                    event.setLocation(eventloc);

                    //Add to the Venue's list of events
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