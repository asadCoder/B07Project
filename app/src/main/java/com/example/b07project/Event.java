package com.example.b07project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

public class Event implements Serializable, Comparable<Event> {
    String eventName;
    String sport;
    int startHour; //24 hour format
    int startMin;
    int endHour; //24 Hour format
    int endMin;
    int capacity;
    int spotsLeft;
    LocalDate date;
    String location;
    String dateS;
    public Event(){

    }


    public Event(String eventName, String location, String sport, int startHour, int startMin, int endHour, int endMin, int capacity, int spotsLeft, LocalDate date) {
        this.eventName = eventName;
        this.sport = sport;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.capacity = capacity;
        this.spotsLeft = spotsLeft;
        this.date = date;
        this.location = location;
    }

    public Event(String eventName, int startHour, int startMin, int endHour, int endMin, int capacity, String location, String dateS) {
        this.eventName = eventName;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.capacity = capacity;
        this.location = location;
        this.dateS = dateS;
    }

    public Event(String eventName, int startHour, int startMin, int endHour, int endMin, int capacity, int spotsLeft, String location, String dateS) {
        this.eventName = eventName;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.capacity = capacity;
        this.spotsLeft = spotsLeft;
        this.location = location;
        this.dateS = dateS;
    }


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setSport(String sport) {
        this.sport = sport;
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

    public void setSpotsLeft(int spotsLeft) {
        this.spotsLeft = spotsLeft;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public String getSport() {
        return sport;
    }


    public int getCapacity() {
        return capacity;
    }

    public int getSpotsLeft() {
        return spotsLeft;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return startHour == event.startHour && startMin == event.startMin && endHour == event.endHour && endMin == event.endMin && capacity == event.capacity && spotsLeft == event.spotsLeft && eventName.equals(event.eventName) && sport.equals(event.sport) && date.equals(event.date) && location.equals(event.location) && dateS.equals(event.dateS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, sport, startHour, startMin, endHour, endMin, capacity, spotsLeft, location, dateS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Event event) {
                if (this.date.compareTo(event.date) > 0) return 1;
        else if (this.date.compareTo(event.date) < 0) return -1;
        else{
            if(this.startHour > event.startHour) return 1;
            else if(this.startHour< event.startHour) return -1;
            else {
                if(this.startMin > event.startMin) return 1;
                else if(this.startMin< event.startMin) return -1;
                else return 0;
            }
        }
    }
}
