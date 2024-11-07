package com.model;

import java.util.Date;

public class Reservation {
    static enum Reservation_Type {
        Fancy,
        Casual
    }
    private int time;
    private String location;
    private String website;
    private Date date;
    private int reviewStars;

    public Reservation() {}

    public Reservation(int time, String location, String website, Date date) {
        this.time = time;
        this.location = location;
        this.website = website;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
