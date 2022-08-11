package com.example.b07project;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;

public class Event implements Serializable, Comparable<Event> {
    String admin;
    String venueName;
    String eventName;
    int startHour; //24 hour format
    int startMin;
    int endHour; //24 Hour format
    int endMin;
    int capacity;
    int spotsLeft;
    String location;
    String address;
    String date;

    public Event(){

    }

    public Event(String admin, String venueName, String eventName, String address, int startHour, int startMin, int endHour, int endMin,
                 int capacity, int spotsLeft, String location, String date) {
        this.admin = admin;
        this.venueName = venueName;
        this.eventName = eventName;
        this.address = address;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.capacity = capacity;
        this.spotsLeft = spotsLeft;
        this.location = location;
        this.date = date;
    }

//    @Override
//    public boolean equals(@Nullable Object obj) {
//        if(obj==null){
//            return false;
//        }
//        if(!(obj instanceof Event)){
//            return false;
//        }
//        Event e = (Event) obj;
//        if(this.getVenueName().equals(e.getVenueName()) && this.getEventName().equals(e.getEventName())
//                && this.getLocation().equals(e.getLocation()) && this.address.equals(e.address) &&
//                this.getDate().equals(e.getDate())
//                && this.getStartHour()==e.getStartHour() && this.getStartMin()==e.getStartMin() &&
//                this.getEndMin()==e.getEndMin() && this.getEndHour()==e.getEndHour() &&
//                this.getCapacity()==e.getCapacity()){
//            return true;
//        }
//        return false;
//    }

    public boolean overlap(Event e) {
        if (e.startHour > this.endHour || e.endHour < this.startHour) return false;
        else if( (e.startHour==this.endHour && e.startMin >= this.endMin) || e.endHour==this.startHour
                && e.endMin<=this.startMin) return false;
        else return true;
    }

//    @Override
//    public boolean equals(@Nullable Object obj) {
//        if(obj==null){
//            return false;
//        }
//        if(!(obj instanceof Event)){
//            return false;
//        }
//        Event e = (Event) obj;
//        if( !this.getLocation().equals(e.getLocation()) || !this.address.equals(e.address) ||
//                !this.getDate().equals(e.getDate()) || ( !this.overlap(e)) && !e.overlap(this) ) return false;
//
//
//        else return true;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return startHour == event.startHour && startMin == event.startMin && endHour == event.endHour && endMin == event.endMin && capacity == event.capacity && spotsLeft == event.spotsLeft && admin.equals(event.admin) && venueName.equals(event.venueName) && eventName.equals(event.eventName) && location.equals(event.location) && address.equals(event.address) && date.equals(event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin, venueName, eventName, startHour, startMin, endHour, endMin, capacity, spotsLeft, location, address, date);
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {this.admin = admin;}

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address =address;
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

    public String date(){
        String[] d = this.date.split("/");
        String result = "";
        for(String s:d){
            result = result + s +"|";
        }
        return result;
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

    public boolean changes(Event event){
        if(this.equals(event) && this.getSpotsLeft() != event.getSpotsLeft()) return true;
        return false;
    }


    @Override
    public int compareTo(Event event) {
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

    @Override
    public String toString() {
        return "Event{" +
                "venueName='" + venueName +
                "', eventName='" + eventName +
                "', address='" + address +
                "', startHour=" + startHour +
                ", startMin=" + startMin +
                ", endHour=" + endHour +
                ", endMin=" + endMin +
                ", capacity=" + capacity +
                ", location='" + location +
                "', date='" + date() +
                '}';
    }
}
