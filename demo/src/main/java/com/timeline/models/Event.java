package com.timeline.models;

import java.time.LocalDateTime;


public class Event {
    // This is the class file for the Event object.
    private int id;
    private String name;
    private String startTime;
    private String endTime;
    private String description;
    private String imageName;

    public Event() {

    }


    // Constructor for fetching from database
    public Event(String name, String description, String startTime, String endTime, String imageName) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event: " + getName();   //just for test purpose currently
    }

}
