package com.model;

public abstract class Reservation {
    protected String location;
    protected String website;
    protected int reviewStars;
    protected Random random;

    protected Reservation() {
        //to accomodate firebase
    }

    protected Reservation(String location, String website) {
        this.location = location;
        this.website = website;
        random = new Random();
        this.reviewStars = (int) (random.nextInt() * 5) + 1;
    }

    protected Reservation(String location) {
        this.location = location;
        random = new Random();
        this.reviewStars = (int) (random.nextInt() * 5) + 1;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
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
