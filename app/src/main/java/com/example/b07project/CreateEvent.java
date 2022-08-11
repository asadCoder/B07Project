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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    int startTime, endTime, capacity;
    Button sTime, eTime;
    Button createEve;
    int shour, sminute;
    int ehour, eminute;
    TextView eventName, location, maxcap, date;
    FirebaseDatabase database;
    DatabaseReference ref;
    boolean stime;
    boolean etime;
    boolean BeforeCurDate;
    Event event;
    ArrayList<Event> events = new ArrayList<Event>();
    Venue v;
    private TextView Date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SimpleDateFormat dateFormat;
    String currentDate;
    FloatingActionButton backbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        SharedPreferences sharedPref = getSharedPreferences("venue",MODE_PRIVATE);
        String vname = getIntent().getStringExtra("vname");
        int vStartH = getIntent().getIntExtra("vstartH", 0);
        int vStartM = getIntent().getIntExtra("vstartM", 0);
        int vEndH = getIntent().getIntExtra("vendH", 0);
        int vEndM = getIntent().getIntExtra("vendM", 0);
        SharedPreferences sharedPref2 = getSharedPreferences("save",MODE_PRIVATE);
        String user = sharedPref2.getString("username","f");
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        event = new Event();

        backbut = findViewById(R.id.BackBut3);
        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminMasterActivity.class));
            }
        });
        ref = database.getReference("Venues/"+getIntent().getStringExtra("address")+"/Events");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String date = snapshot.child("date").getValue().toString();
                    int startHour = Integer.parseInt(snapshot.child("startHour").getValue().toString());
                    int startMin = Integer.parseInt(snapshot.child("startMin").getValue().toString());
                    int endHour = Integer.parseInt(snapshot.child("endHour").getValue().toString());
                    int endMin = Integer.parseInt(snapshot.child("endMin").getValue().toString());
                    String venueName = snapshot.child("venueName").getValue().toString();
                    String eventName = snapshot.child("eventName").getValue().toString();
                    String location = snapshot.child("location").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String admin = snapshot.child("admin").getValue().toString();

                    int capacity = Integer.parseInt(snapshot.child("capacity").getValue().toString());
                    int spotsLeft = Integer.parseInt(snapshot.child("spotsLeft").getValue().toString());

                    //Eventually a sorting alorithm will go here so that the location is priority
                    Event e = new Event(admin, venueName, eventName, address, startHour,startMin,endHour,endMin,capacity,spotsLeft,location,date);
                    if(!events.contains(e)) events.add(e);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                event.setDate(date);
                Date.setText(date);
            }
        };

        createEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("RRRRRR  "+events.size());
                if(events != null) System.out.println("AAAAAAAAAA");
                else System.out.println("BBBBBBBB");
                String eventn = eventName.getText().toString().trim();
                String eventloc = location.getText().toString().trim();
                String eventDate = Date.getText().toString().trim();

                if(!eventDate.isEmpty() && stime) {
                    Calendar cal = Calendar.getInstance();
                    dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    currentDate = dateFormat.format(cal.getTime());
                    String curarry[] = currentDate.split("/");
                    int curM = Integer.parseInt(curarry[0]);
                    int curD = Integer.parseInt(curarry[1]);
                    int curY = Integer.parseInt(curarry[2]);
                    String evearr[] = eventDate.split("/");
                    int month = Integer.parseInt(evearr[0]);
                    int day = Integer.parseInt(evearr[1]);
                    int year = Integer.parseInt(evearr[2]);

                    SimpleDateFormat t = new SimpleDateFormat("HH:mm");
                    String curTime = t.format(cal.getTime());
                    String curtarr[] = curTime.split(":");
                    int cur_H = Integer.parseInt(curtarr[0]);
                    int cur_M = Integer.parseInt(curtarr[1]);

                    System.out.println(event.getStartMin());


                    if (year < curY || (year == curY && curM > month) || (year == curY && curM == month && curD > day) || (year == curY && curM == month && curD == day && event.getStartHour() < cur_H) || (year == curY && curM == month && curD == day && event.getStartHour() == cur_H && event.getStartMin() <= cur_M)) {
                        BeforeCurDate = true;
                    }
                    else{
                        BeforeCurDate = false;
                    }
                }



                if(TextUtils.isEmpty(eventn) || TextUtils.isEmpty(eventloc) || TextUtils.isEmpty(eventDate) || TextUtils.isEmpty(maxcap.getText().toString().trim()) || !stime || !etime){
                    Toast.makeText(CreateEvent.this,"No fields can be empty", Toast.LENGTH_SHORT).show();
                } else if(event.getStartHour()>event.getEndHour() || ((event.getStartHour()==event.getEndHour()) && event.getStartMin()>=event.getEndMin())){
                    Toast.makeText(CreateEvent.this,"Enter valid start and end times", Toast.LENGTH_SHORT).show();
                }
                else if( BeforeCurDate){
                    Toast.makeText(CreateEvent.this,"The start time of the event must be after the current time", Toast.LENGTH_SHORT).show();
                }
                else if(event.getStartHour()<vStartH || (event.getStartHour()==vStartH && event.getStartMin()<vStartM)){
                    Toast.makeText(CreateEvent.this,"Start time needs to be after " + vname + " opens", Toast.LENGTH_SHORT).show();
                }
                else if(event.getEndHour()>vEndH || (event.getEndHour()==vEndH && event.getEndMin()>vEndM)){
                    System.out.println("VENDh:  " + vEndH + "   vEndm:   "+ vEndM);
                    Toast.makeText(CreateEvent.this,"End time needs to be before " + vname + " closes", Toast.LENGTH_SHORT).show();
                }
                else {

                    capacity = Integer.parseInt(maxcap.getText().toString().trim());
                    event.setCapacity(capacity);
                    event.setSpotsLeft(capacity);
                    event.setEventName(eventn);
                    event.setLocation(eventloc);
                    event.setDate(eventDate);
                    event.setVenueName(vname);
                    event.setAddress(getIntent().getStringExtra("address"));
                    event.setAdmin(user);
                    if(events.contains(event)) {
                        Toast.makeText(CreateEvent.this, "Event already exists", Toast.LENGTH_SHORT).show();
                    }else{

                        ref = database.getReference("Events/"+event.toString());
                        ref.setValue(event);
//                        ref.child("Events").child(event.toString()).setValue(event);

                        ref = database.getReference("Venues/"+getIntent().getStringExtra("address")+"/Events/"+event.toString());
                        ref.setValue(event);
//                        ref.child("Venues").child(getIntent().getStringExtra("address")).child("Events").child(event.toString()).setValue(event);

                        ref = database.getReference("Admins/" + user + "/Venues/" +
                                getIntent().getStringExtra("address") + "/Events/" + event.toString());
//                        ref.child("Admins").child(user).child("Venues").child(getIntent().getStringExtra("address")).child("Events").child(event.toString()).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                            ref.setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CreateEvent.this, "Event added", Toast.LENGTH_SHORT).show();
                            }
                        });
                          ViewVenuesAdmin adminm = new ViewVenuesAdmin();
                          startActivity(new Intent(getApplicationContext(), AdminMasterActivity.class));

                    }
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
                System.out.println("sH" + shour );

                event.setStartMin(sminute);
                System.out.println("sM" + sminute );


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
                System.out.println("eH" + ehour );
                event.setEndMin(eminute);
                System.out.println("eM" + eminute );


            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog picker = new TimePickerDialog(this, style, onTimeSetListener, ehour, eminute, true);
        picker.setTitle("Select End Time");
        picker.show();

    }



}