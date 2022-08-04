package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.view.View;

import android.content.SharedPreferences;
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    MyEventsFragment myEvents = new MyEventsFragment();
    ViewVenuesFragment settingsFragment = new ViewVenuesFragment();
    UpcomingFragment notificationFragment = new UpcomingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences("save",MODE_PRIVATE);
        boolean isadmin = sharedPref.getBoolean("value",false);
        if (isadmin){
            startActivity(new Intent(getApplicationContext(), AdminMain.class));
        }


        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, myEvents).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.venues:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myEvents).commit();
                        return true;
                    case R.id.upcoming:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                        return true;
                    case R.id.myevents:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }



    public void logout(View view){
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}