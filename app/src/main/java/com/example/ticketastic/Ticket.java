package com.example.ticketastic;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String eventName;
    private String image;
    private String eventDate;
    private String schedule;
    private String username;
    private int price;
    private int quantity;

    Ticket(String eventName, String image, String eventDate, String schedule, String username, int price, int quantity){
        this.eventName = eventName;
        this.image = image;
        this.eventDate = eventDate;
        this.schedule = schedule;
        this.username = username;
        this.price = price;
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity; }

    public int getPrice(){ return price; }

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
