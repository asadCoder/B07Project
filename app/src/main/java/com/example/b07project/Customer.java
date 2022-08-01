package com.example.b07project;

import java.util.ArrayList;

public class Customer {
    String username;
    String email;
    ArrayList<Event> myevents;



    public Customer(String username, String email, ArrayList<Event> myevents) {
        this.username = username;
        this.email = email;
        this.myevents = myevents;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
