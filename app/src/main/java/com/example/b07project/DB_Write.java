package com.example.b07project;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DB_Write {
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth auth;

    public static void createAdmin( Admin u){
        DatabaseReference ref = db.getReference("Admins");
//        auth = FirebaseAuth.getInstance();



        ref.push().setValue(u);
        // creates a user by adding it to the database
        // type of user can only be either "customers" or "owners"

    }
    public static void createCustomer( Customer u){
        DatabaseReference ref = db.getReference("Customers");
//        auth = FirebaseAuth.getInstance();



        ref.push().setValue(u);
        // creates a user by adding it to the database
        // type of user can only be either "customers" or "owners"

    }
}
