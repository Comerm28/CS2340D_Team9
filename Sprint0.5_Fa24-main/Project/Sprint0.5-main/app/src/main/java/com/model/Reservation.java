package com.model;

public class Reservation {
    static enum Reservation_Type {
        Fancy,
        Casual
    }
    private int time;
    private String location;
    private String website;
    private int reviewStars;

    public Reservation(int time, String location, String website) {
        this.time = time;
        this.location = location;
        this.website = website;
    }

    public String getLocation(){
        return location;
    }

    public int getTime(){
        return time;
    }

    public String getWebsite(){
        return website;
    }
}
