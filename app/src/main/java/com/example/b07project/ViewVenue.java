package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewVenue extends AppCompatActivity {

    TextView value;
    Button createEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_venue);

        value = findViewById(R.id.textView2);
        String hashCode = getIntent().getStringExtra("address");

        value.setText(hashCode);

        createEvent = findViewById(R.id.createEvent);
        createEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateEvent.class));

            }
        });
    }
}