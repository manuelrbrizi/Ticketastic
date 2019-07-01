package com.example.ticketastic;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.Serializable;

public class Event implements Serializable {
    private String name;
    private String description;
    private String type;
    private String image;
    private ArrayList<String> date;
    private ArrayList<String> schedule;
    private int price;
    private int promotion;

    public Event(String name, String description, String type, String image, String date, String schedule, int price, int promotion) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.image = image;
        this.date = parse(date);
        this.schedule = parse(schedule);
        this.price = price;
        this.promotion = promotion;
    }

    public boolean isPromoted(){ return promotion == 1; }

    public String getDescription(){ return description; }

    public int getPrice(){ return price; }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    private ArrayList<String> parse(String s){
        String[] parsed = s.split(",");
        return new ArrayList<>(Arrays.asList(parsed));
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public ArrayList<String> getSchedule() {
        return schedule;
    }
}
