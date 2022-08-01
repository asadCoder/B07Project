package com.example.b07project;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DB_Write {
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07newproject-default-rtdb.firebaseio.com/");

    public static void createAdmin( Admin u){
        // creates a user by adding it to the database
        // type of user can only be either "customers" or "owners"
 
    }
}
