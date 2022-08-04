package com.example.b07project;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Venue implements Parcelable, Serializable {
    String venueName;
    int startHour; //24 hour format
    int startMin;
    int endHour; //24 Hour format
    int endMin;
    String location;
    ArrayList<Event> events;




    public Venue(){

    }

    public Venue(String venueName, int startHour, int startMin, int endHour, int endMin, String location, ArrayList<Event> events) {
        this.venueName = venueName;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.location = location;
        this.events = events;
    }

    protected Venue(Parcel in) {
        venueName = in.readString();
        startHour = in.readInt();
        startMin = in.readInt();
        endHour = in.readInt();
        endMin = in.readInt();
        location = in.readString();
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };



    public String endtime(){
        String em=  String.valueOf(getEndMin()).trim();
        String eh = String.valueOf(getEndHour()).trim();
        if (em.length() == 1) {
            em = "0"+em;
        }
        if (eh.length()==1){
            eh = "0"+eh;
        }


        String ti = eh + ":"+em;
        return ti;
    }
    public String starttime(){
        String em=  String.valueOf(getStartMin()).trim();
        String eh = String.valueOf(getStartHour()).trim();
        if (em.length() == 1) {
            em = "0"+em;
        }
        if (eh.length()==1){
            eh = "0"+eh;
        }


        String ti = eh + ":"+em;
        return ti;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(venueName);
        parcel.writeInt(startHour);
        parcel.writeInt(startMin);
        parcel.writeInt(endHour);
        parcel.writeInt(endMin);
        parcel.writeString(location);
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
