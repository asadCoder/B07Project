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
    //    MyEventsFragment myEvents = new MyEventsFragment();
    ViewVenuesUser viewVenuesUserF = new ViewVenuesUser();
    Scroll upcomingEventsF = new Scroll();
    MyEventsUser myEventsUserF = new MyEventsUser();
    //    ViewVenuesFragment settingsFragment = new ViewVenuesFragment();
    UserProfileFragment profileFragment = new UserProfileFragment();
    //    UpcomingFragment notificationFragment = new UpcomingFragment();
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

        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewVenuesUserF).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.venues:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewVenuesUserF).commit();
                        return true;
                    case R.id.upcoming:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,upcomingEventsF).commit();
                        return true;
                    case R.id.myevents:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,myEventsUserF).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
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
