package com.inge.ticketastic.classes;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.Serializable;

public class Event implements Serializable {
    private int id;
    private String name;
    private String description;
    private String type;
    private int image;
    private int totalTickets;
    private int leftTickets;
    private ArrayList<String> date;
    private ArrayList<String> schedule;
    private int price;
    private int promotion;

    public Event(int id, String name, String description, String type, int image, int totalTickets, int leftTickets, String date, String schedule, int price, int promotion) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.image = image;
        this.totalTickets = totalTickets;
        this.leftTickets = leftTickets;
        this.date = parse(date);
        this.schedule = parse(schedule);
        this.price = price;
        this.promotion = promotion;
    }

    public int getTotalTickets(){return totalTickets;}

    public int getLeftTickets(){ return leftTickets; }

    public int getId(){return id;}

    public boolean isPromoted(){ return promotion == 1; }

    String getDescription(){ return description; }

    int getPrice(){ return price; }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getImage() {
        return image;
    }

    private ArrayList<String> parse(String s){
        String[] parsed = s.split(",");
        return new ArrayList<>(Arrays.asList(parsed));
    }

    ArrayList<String> getDate() {
        return date;
    }

    ArrayList<String> getSchedule() {
        return schedule;
    }
}
