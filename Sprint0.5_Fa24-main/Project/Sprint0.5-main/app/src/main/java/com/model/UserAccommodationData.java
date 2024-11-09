package com.model;

import java.util.Date;

public class UserAccommodationData {
    private String username;
    private Date checkInTime;
    private Date checkOutTime;
    private String location;
    private int numberOfRooms;
    private RoomType roomType;

    public UserAccommodationData() {}

    public UserAccommodationData(String username){
        username = username;
    }

    public String getUsername(){return username;}

    public void setUsername(String newName){
        this.username = newName;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
       this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public enum RoomType {
        Single("Single"),
        KingSuite("King Suite"),
        Deluxe("Deluxe");

        public final String displayString;

        RoomType(String displayString) {
            this.displayString = displayString;
        }
    }
}