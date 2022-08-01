package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Locale;

public class CreateEvent extends AppCompatActivity {

    int startTime, endTime;
    Button sTime, eTime;
    int shour, sminute;
    int ehour, eminute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        sTime = findViewById(R.id.startTime);
        eTime = findViewById(R.id.endTime);


    }


    public void popTimePicker1(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                shour = hour;
                sminute = minutes;
                sTime.setText(String.format(Locale.getDefault(), "%02d:%02d", shour, sminute));

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

            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog picker = new TimePickerDialog(this, style, onTimeSetListener, ehour, eminute, true);
        picker.setTitle("Select End Time");
        picker.show();

    }
}