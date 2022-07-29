package com.example.b07project;

import java.util.HashSet;

public class Venue {
    String venueName;
    int startTime; //24 hour format
    int endTime; //24 Hour format
    String date;
    String location;
    HashSet<Event> events;

}
