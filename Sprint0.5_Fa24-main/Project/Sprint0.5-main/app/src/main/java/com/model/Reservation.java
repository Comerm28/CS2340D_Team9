package com.model;

public abstract class Reservation {
    protected String location;
    protected String website;
    protected int reviewStars;

    public Reservation() {}

    public Reservation(String location, String website) {
        this.location = location;
        this.website = website;
        this.reviewStars = (int)(Math.random() * 5) + 1;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite(){
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getReviewStars() {
        return reviewStars;
    }

    public void setReviewStars(int reviewStars) {
        this.reviewStars = reviewStars;
    }
}
