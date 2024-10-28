package com.model;

import java.util.Date;

public class User {
    private String username;
    private int allottedVacationDays;
    private Date startDate;
    private Date endDate;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public static String formatEmail(String username) {
        return username + "@a.com";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAllottedVacationDays() {
        return allottedVacationDays;
    }

    public void setAllottedVacationDays(int allottedVacationDays) {
        this.allottedVacationDays = allottedVacationDays;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}