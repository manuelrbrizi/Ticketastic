package com.inge.ticketastic.classes;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String code;
    private String eventName;
    private int image;
    private String eventDate;
    private String schedule;
    private String username;
    private int price;
    private int quantity;
    private int promoted;

    public Ticket(String code, String eventName, int image, String eventDate, String schedule, String username, int price, int quantity, int promoted){
        this.code = code;
        this.eventName = eventName;
        this.image = image;
        this.eventDate = eventDate;
        this.schedule = schedule;
        this.username = username;
        this.price = price;
        this.quantity = quantity;
        this.promoted = promoted;
    }

    public boolean isPromoted(){ return promoted == 1; }

    public int getPromoted(){ return promoted; }

    public String getCode(){ return code; }

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

    public int getImage() {
        return image;
    }

    public String getEventDate() {
        return eventDate;
    }
}
