package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewVenue extends AppCompatActivity {

    public TextView value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_venue);

        value = findViewById(R.id.textView2);
        String hashCode = getIntent().getStringExtra("hashCode");

        value.setText(hashCode);
    }
}