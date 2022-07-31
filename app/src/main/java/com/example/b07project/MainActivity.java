package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import android.content.SharedPreferences;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences("save",MODE_PRIVATE);
        boolean isadmin = sharedPref.getBoolean("value",false);
        if (isadmin){
            startActivity(new Intent(getApplicationContext(), AdminMain.class));
        }


    }



    public void logout(View view){
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}