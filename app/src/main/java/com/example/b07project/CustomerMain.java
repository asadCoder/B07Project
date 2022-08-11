package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerMain extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewVenuesUser viewVenuesUserF = new ViewVenuesUser();
    Scroll upcomingEventsF = new Scroll();
    MyEventsUser myEventsUserF = new MyEventsUser();
    Profile profileF = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        SharedPreferences sharedPref = getSharedPreferences("save",MODE_PRIVATE);
        boolean isadmin = sharedPref.getBoolean("value",false);
        if (isadmin){
            startActivity(new Intent(getApplicationContext(), AdminMasterActivity.class));
        }
        myEventsUserF.setUser(sharedPref.getString("username","f"));
        upcomingEventsF.setUser(sharedPref.getString("username","f"));
        viewVenuesUserF.setUser(sharedPref.getString("username","f"));
        bottomNavigationView  = findViewById(R.id.bottom_navigation);
        setTitle("Venues");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewVenuesUserF).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.venues:
                        setTitle("Venues");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewVenuesUserF).commit();
                        return true;
                    case R.id.upcoming:
                        setTitle("Upcoming Events");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,upcomingEventsF).commit();
                        return true;
                    case R.id.myevents:
                        setTitle("My Events");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,myEventsUserF).commit();
                        return true;
                    case R.id.profile:
                        setTitle("Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileF).commit();
                        return true;
                }

                return false;
            }
        });
    }

}
