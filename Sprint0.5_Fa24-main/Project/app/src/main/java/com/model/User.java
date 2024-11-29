package com.model;

import java.util.Date;

public class User {
    private String username;
    private int allottedVacationDays;
    private Date startDate;
    private Date endDate;
    private boolean isCollaborating;
    private String collaboratorUsername;

    public User() { }

    public User(String username) {
        this.username = username;
        isCollaborating = false;
        collaboratorUsername = "";
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

    public boolean isCollaborating() {
        return isCollaborating;
    }

    public void setCollaborating(boolean collaborating) {
        isCollaborating = collaborating;
    }

    public String getCollaboratorUsername() {
        return collaboratorUsername;
    }

    public void setCollaboratorUsername(String collaboratorUsername) {
        this.collaboratorUsername = collaboratorUsername;
    }
}