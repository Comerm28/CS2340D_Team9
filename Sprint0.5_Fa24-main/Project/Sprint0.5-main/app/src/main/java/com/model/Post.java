package com.model;

import java.util.Date;

public class Post {
    private String username;
    private String destination;
    private Date startDate;
    private Date endDate;
    private String accommodations;
    private String diningReservation;
    private String notes;
    private int rating;

    public Post() { }

    public String getUsername() {
        return username;
    }

    public String getDestination() {
        return destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getAccommodations() {
        return accommodations;
    }

    public String getDiningReservation() {
        return diningReservation;
    }

    public String getNotes() {
        return notes;
    }

    public int getRating() {
        return rating;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setAccommodations(String accommodations) {
        this.accommodations = accommodations;
    }

    public void setDiningReservation(String diningReservation) {
        this.diningReservation = diningReservation;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
