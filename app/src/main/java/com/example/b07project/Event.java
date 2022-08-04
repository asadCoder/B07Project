package com.example.b07project;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

public class Event implements Serializable, Comparable<Event> {
    String eventName;
    int startHour; //24 hour format
    int startMin;
    int endHour; //24 Hour format
    int endMin;
    int capacity;
    int spotsLeft;
    String location;
    String date;

    public Event(){

    }

    public Event(String eventName, int startHour, int startMin, int endHour, int endMin,
                 int capacity, int spotsLeft, String location, String date) {
        this.eventName = eventName;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.capacity = capacity;
        this.spotsLeft = spotsLeft;
        this.location = location;
        this.date = date;
    }


    public String getEventName() {
        return eventName;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSpotsLeft() {
        return spotsLeft;
    }

    public void setSpotsLeft(int spotsLeft) {
        this.spotsLeft = spotsLeft;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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
    public int compareTo(Event event) {
//        int day;
//        int month;
//        int year;
        String[] mainDates = this.date.trim().split("/");
        String[] comparedDates = event.date.trim().split("/");
        int mainDay = Integer.parseInt(mainDates[1]);
        int mainMonth = Integer.parseInt(mainDates[0]);
        int mainYear = Integer.parseInt(mainDates[2]);
        int comparedDay = Integer.parseInt(comparedDates[1]);
        int comparedMonth = Integer.parseInt(comparedDates[0]);
        int comparedYear = Integer.parseInt(comparedDates[2]);
        if(mainYear > comparedYear) return 1;
        if(mainYear < comparedYear) return -1;
        if (mainMonth > comparedMonth) return 1;
        if (mainMonth < comparedMonth) return -1;
        if (mainDay > comparedDay) return 1;
        if (mainDay < comparedDay) return -1;
        if (this.startHour > event.startHour) return 1;
        if (this.startHour < event.startHour) return -1;
        if (this.startMin > event.startMin) return 1;
        if (this.startMin < event.startMin) return -1;
        return 0;

    }
}
