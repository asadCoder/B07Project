package com.example.b07project;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.HashSet;

public class Event implements Serializable {
    String eventName;
    int startHour; //24 hour format
    int startMin;
    int endHour; //24 Hour format
    int endMin;
    int capacity;
    String date;
    String location;

    public Event(){

    }


    public Event(String eventName, int startHour, int startMin, int endHour, int endMin, int capacity, String date, String location) {
        this.eventName = eventName;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.capacity = capacity;
        this.date = date;
        this.location = location;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj==null){
            return false;
        }
        if(!(obj instanceof Event)){
            return false;
        }
        Event e = (Event) obj;
        if(this.getEventName().equals(e.getEventName()) && this.getLocation().equals(e.getLocation()) && this.getDate().equals(e.getDate()) && this.getStartHour()==e.getStartHour() && this.getStartMin()==e.getStartMin() && this.getEndMin()==e.getEndMin() && this.getEndHour()==e.getEndHour() && this.getCapacity()==e.getCapacity()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", startHour=" + startHour +
                ", startMin=" + startMin +
                ", endHour=" + endHour +
                ", endMin=" + endMin +
                ", capacity=" + capacity +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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
