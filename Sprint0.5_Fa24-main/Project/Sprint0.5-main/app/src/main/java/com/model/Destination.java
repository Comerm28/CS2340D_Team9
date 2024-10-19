package com.model;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Destination {
    private String locationName;
    private Date startDate;
    private int durationDays;

    public Destination(String locationName, Date startDate, int durationDays) {
        this.locationName = locationName;
        this.startDate = startDate;
        this.durationDays = durationDays;
    }

    public String getLocationName() {
        return locationName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public long getDurationDays() {
        return durationDays;
    }

    public Date getEndDate() {
        return new Date(startDate.getTime() + durationDays);
    }

    public static Date calculateMissingStartDate(int durationDays, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, -durationDays);
        return calendar.getTime();
    }

    public static Date calculateMissingEndDate(int durationDays, Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, durationDays);
        return calendar.getTime();
    }

    public static long calculateMissingDays(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());
    }
}
