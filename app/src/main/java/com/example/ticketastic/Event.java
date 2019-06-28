package com.example.ticketastic;

import java.io.Serializable;

public class Event implements Serializable {
    private String name;
    private String type;
    private String url;

    public Event(String name, String type, String url) {
        this.name = name;
        this.type = type;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

}
