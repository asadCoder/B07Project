package com.example.b07project;

import java.util.HashSet;

public class Venue {
    String venueName;
    int startTime; //24 hour format
    int endTime; //24 Hour format
    String date;
    String location;
    HashSet<Event> events;
    public Venue(){
        this.venueName = venueName;
        
        this.date = date;
        this.location = location;
        this.events = events;
    }
    public Venue(String venueName, int startTime, int endTime, String date, String location, HashSet<Event> events) {
        this.venueName = venueName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.location = location;
        this.events = events;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
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
}
