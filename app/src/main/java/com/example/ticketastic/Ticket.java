package com.example.ticketastic;

import java.io.Serializable;

class Ticket implements Serializable {
    private String code;
    private String eventName;
    private int image;
    private String eventDate;
    private String schedule;
    private String username;
    private int price;
    private int quantity;
    private int promoted;

    Ticket(String code, String eventName, int image, String eventDate, String schedule, String username, int price, int quantity, int promoted){
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

    boolean isPromoted(){ return promoted == 1; }

    int getPromoted(){ return promoted; }

    String getCode(){ return code; }

    int getQuantity() { return quantity; }

    int getPrice(){ return price; }

    String getUsername() {
        return username;
    }

    String getSchedule() {
        return schedule;
    }

    String getEventName() {
        return eventName;
    }

    int getImage() {
        return image;
    }

    String getEventDate() {
        return eventDate;
    }
}
