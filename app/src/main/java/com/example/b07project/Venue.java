package com.example.b07project;

import java.util.HashSet;

public class Venue {
    String venueHashCode;
    String venueName;
    int startHour; //24 hour format
    int startMin;
    int endHour; //24 Hour format
    int endMin;
    String date;
    String location;
    HashSet<Event> events;

    public Venue(String venueHashCode, String venueName, int starthour, int startmin, int endhour, int endmin, String date, String location, HashSet<Event> events) {
        this.venueHashCode = venueHashCode;
        this.venueName = venueName;
        this.startHour = starthour;
        this.startMin = startmin;
        this.endHour = endhour;
        this.endMin = endmin;
        this.date = date;
        this.location = location;
        this.events = events;
    }


    public Venue(){

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

    public String getVenueName() {
        return venueName;
    }

    public String getVenueHashCode() {
        return venueHashCode;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setVenueHashCode(String venueHashCode) {
        this.venueHashCode = venueHashCode;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashSet<Event> getEvents() {
        return events;
    }

    public void setEvents(HashSet<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) { events.add(event); }
}
