package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    Switch mCheckadmin;
    EditText mUsername;
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.Email);
        mRegisterBtn = (Button) findViewById(R.id.RegisterButton);
        mLoginBtn = findViewById(R.id.createText);
        mPassword = findViewById(R.id.Password);
        mCheckadmin = (Switch)findViewById(R.id.AdminCheck);
        mUsername = findViewById(R.id.Username);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            if (mCheckadmin.isChecked()) {
                startActivity(new Intent(getApplicationContext(),AdminMain.class));

            }
            else {
                startActivity(new Intent(getApplicationContext(),CustomerMain.class));
            }
            finish();
        }
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String username = mUsername.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(username)){
                    mUsername.setError("Username is required");
                    return;
                }







                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password has to be >= 6 characters");
                }
                // register user
                DatabaseReference rootRef = db.getReference();
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean b = false;
                        if (snapshot.exists()){
                            for(DataSnapshot user: snapshot.child("Admins").getChildren()){
                                if(user.child("username").getValue().toString().equals(username) ){
                                    Log.i("console", "username found!");
                                    b = true;
                                }

                            }}
                        else{
                            Log.i("console", "snapshot doesnt exist");
                        }
                        if(!b ){
                            DB_Write.write_username(username,email);
                            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this,"User Created", Toast.LENGTH_SHORT).show();
                                        Admin a = new Admin(username,email,null);
                                        ArrayList<Venue> v = new ArrayList<Venue>();
                                        a.setVenues(v);
//
                                        ArrayList<Event> e = new ArrayList<Event>();
                                        DB_Write.createAdmin(a,Register.this);
                                        Customer c = new Customer(username,email,e);
                                        DB_Write.createCustomer(c,Register.this);
                                        SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();



                                        editor.putString("username",a.getUsername());
                                        editor.apply();
                                        if (mCheckadmin.isChecked()){
                                            editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                                            editor.putBoolean("value",true);
                                            editor.apply();
                                            Toast.makeText(Register.this, email, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this, CreateVenue.class);
                                            intent.putExtra("admin",a);
                                            startActivity(intent);

                                            startActivity(new Intent(getApplicationContext(),AdminMain.class));
                                        }else {
                                            Intent intent = new Intent(Register.this, ViewVenuesUser.class);
                                            editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                                            editor.putBoolean("value",false);
                                            editor.apply();
                                            intent.putExtra("Customer",c);
                                            startActivity(intent);

                                            //asad changed
                                            startActivity(new Intent(getApplicationContext(), CustomerMain.class));
                                        }
                                    }
                                    else{
                                        Toast.makeText(Register.this, "Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }
                        else {
                            mUsername.setError("Username is in Use");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }




        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }


}