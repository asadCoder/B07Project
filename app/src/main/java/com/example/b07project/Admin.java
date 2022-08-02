package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Admin implements Parcelable {
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

    protected Admin(Parcel in) {
        username = in.readString();
        email = in.readString();
    }

    public static final Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
    }
}
