package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMasterActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    ViewVenuesAdmin viewVenuesAdminF = new ViewVenuesAdmin();
    Profile profileF = new Profile();
    //Fragment viewAdmin = new AdminMainFragment();
    Fragment viewBookings = new AdminAllEventsFragment();
    Fragment profile = new AdminProfileFragment();
//    //    ViewVenuesFragment settingsFragment = new ViewVenuesFragment();
//    UserProfileFragment profileFragment = new UserProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_master);
        SharedPreferences sharedPref = getSharedPreferences("save",MODE_PRIVATE);
        boolean isadmin = sharedPref.getBoolean("value",false);
        if (!isadmin){
            startActivity(new Intent(getApplicationContext(), CustomerMain.class));
        }
        System.out.println("bro\n\n\n");
        viewVenuesAdminF.setAdmin(sharedPref.getString("username","f"));
        bottomNavigationView  = findViewById(R.id.bottom_navigation1);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewVenuesAdminF).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myvenues2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewVenuesAdminF).commit();
                        return true;
                    case R.id.booked2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, viewBookings).commit();
                        return true;
                    case R.id.profile2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileF).commit();
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed () {
        startActivity(new Intent(this, AdminMasterActivity.class));
    }
}