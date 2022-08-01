package com.example.b07project;

import java.util.ArrayList;

public class Customer {
    String email;
    ArrayList<Event> myevents;



    public Customer(String email, ArrayList<Event> myevents) {
        this.email = email;
        this.myevents = myevents;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Event> getMyevents() {
        return myevents;
    }

    public void setMyevents(ArrayList<Event> myevents) {
        this.myevents = myevents;
    }
}
