package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;

public class Admin implements Serializable {
    String username;
    String email;
    ArrayList<Venue> Venues;

    public Admin() {
        super();
    }

    public Admin(String username, String email, ArrayList<Venue> venues) {
        this.username = username;
        this.email = email;
        Venues = venues;
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

    public ArrayList<Venue> getVenues() {
        return Venues;
    }

    public void setVenues(ArrayList<Venue> venues) {
        Venues = venues;
    }
}
