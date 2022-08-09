package com.example.b07project;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends Fragment {
    Button signOutB;
    TextView acc, email, username;

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflator.inflate(R.layout.activity_profile, container, false);
        signOutB = (Button) mView.findViewById(R.id.button3);

        setProfile(mView);

        signOutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),Login.class));
            }
        });



        return mView;
    }

    //add set account type
    //add set username
    //add set email

    public void setProfile(View view)
    {
        SharedPreferences s = this.getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        String em =  s.getString("email", "f");
        String us = s.getString("username", "f");
        Boolean account = s.getBoolean("value",false);

        acc = (TextView) view.findViewById(R.id.account);
        if(account)
        {
            acc.setText("Account: Admin");

        }
        else {
            acc.setText("Account: Customer");
        }
        email = (TextView) view.findViewById(R.id.email);
        email.setText("Email: " + em);
        username = (TextView) view.findViewById(R.id.username);
        username.setText("Username: " + us );
    }


    public void checkSwitch(View view)
    {
        Switch simpleSwitch = (Switch) view.findViewById(R.id.modeSwitch);
        //displayed text of the Switch
        simpleSwitch.setText("Dark");
    }

}