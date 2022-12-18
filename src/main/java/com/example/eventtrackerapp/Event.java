package com.example.eventtrackerapp;

import android.widget.Button;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {

    public static ArrayList<Event> eventList = new ArrayList<>();


    public static ArrayList<Event> eventsForDay(LocalDate date){
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventList){
            if(event.getDate().equals(date)){
                events.add(event);
            }
        }

        return events;
    }


    private String name;
    private LocalDate date;
    Button time;


    public Event(String name, LocalDate date, Button time) {
        this.name = name;
        this.date = date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
