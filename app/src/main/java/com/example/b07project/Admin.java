package com.example.b07project;

import java.util.ArrayList;

public class Admin {
    String email;
    ArrayList<Venue> Venues;

    public Admin() {
        super();
    }

    public Admin(String email, ArrayList<Venue> venues) {

        this.email = email;
        Venues = venues;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Venue> getVenues() {
        return Venues;
    }

    public void setVenues(ArrayList<Venue> venues) {
        Venues = venues;
    }
}
