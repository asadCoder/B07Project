package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    Switch mAdmin;
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = (Button) findViewById(R.id.LoginButton);
        mCreateBtn =  findViewById(R.id.createText);
        mAdmin = (Switch) findViewById(R.id.admlogin);



        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password has to be >= 6 characters");
                }

                DatabaseReference rootRef = db.getReference();
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean b = false;
                        String U="";
                        if (snapshot.exists()){
                            for(DataSnapshot user: snapshot.child("Admins").getChildren()){
                                if(user.child("email").getValue().toString().equals(email) ){
                                    Log.i("console", "email found!");
                                    b = true;
                                    U = user.child("username").getValue().toString();
                                }

                            }

                        }
                        else{
                            Log.i("console", "snapshot doesnt exist");
                            U="not";
                        }
                        Log.i("console", U);
                        if(b){
                            Log.i("console", U);
                            SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();



                            editor.putString("username",U);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()){


                            Toast.makeText(Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();

                            if(mAdmin.isChecked()){
                                editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                                editor.putBoolean("value",true);
                                editor.apply();

                                startActivity(new Intent(getApplicationContext(), AdminMain.class));
                            }
                            else {
//                                System.out.println("HELLOOOOOOO");
                                editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                                editor.putBoolean("value",false);
                                editor.apply();

                                startActivity(new Intent(getApplicationContext(), CustomerMain.class));
                            }

                        }
                        else{
                            Toast.makeText(Login.this, "Error!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}