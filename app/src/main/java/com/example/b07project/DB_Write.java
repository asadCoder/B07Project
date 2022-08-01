package com.example.b07project;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.content.SharedPreferences;
public class DB_Write {
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();


    public static void createAdmin( Admin u,Context context){

     //

        DatabaseReference ref = db.getReference("Admins/"+u.username);

        ref.setValue(u);
        // creates a user by adding it to the database
        // type of user can only be either "customers" or "owners"

    }
    public static void createCustomer( Customer u, Context context){
        DatabaseReference ref = db.getReference("Customers/"+u.username);
//        auth = FirebaseAuth.getInstance();



        ref.setValue(u);
        // creates a user by adding it to the database
        // type of user can only be either "customers" or "owners"


    }
    public static void write_username(String Username){
        DatabaseReference ref = db.getReference("Usernames/"+Username);
        ref.setValue(Username);
    }

}
