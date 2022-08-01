package com.example.b07project;

import java.util.HashSet;

public class Event {
    String eventName;
    int startTime; //24 hour format
    int endTime; //24 Hour format
    int capacity;
    String date;
    String location;
    public Event(){

    }

    public Event(String eventName, int startTime, int endTime, int capacity, String date, String location) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.date = date;
        this.location = location;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
