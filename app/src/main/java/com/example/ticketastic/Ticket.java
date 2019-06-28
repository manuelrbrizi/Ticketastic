package com.example.ticketastic;

public class Ticket {
    private String eventName;
    private String image;
    private String eventDate;
    private String schedule;
    private String username;

    Ticket(String eventName, String image, String eventDate, String schedule, String username){
        this.eventName = eventName;
        this.image = image;
        this.eventDate = eventDate;
        this.schedule = schedule;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getEventName() {
        return eventName;
    }

    public String getImage() {
        return image;
    }

    public String getEventDate() {
        return eventDate;
    }
}
