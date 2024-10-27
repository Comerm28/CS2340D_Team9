package com.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Destination {
    private String locationName;
    private Date startDate;
    private Date endDate;
    private int durationDays;

    public Destination() {
    }

    public Destination(String locationName, Date startDate, Date endDate) {
        this.locationName = locationName;
        this.startDate = startDate;
        this.endDate = endDate;
        long diffInMillis = Math.abs(endDate.getTime() - startDate.getTime());
        this.durationDays = (int) (diffInMillis / (24 * 60 * 60 * 1000));
    }

    public String getLocationName() {
        return locationName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long getDurationDays() {
        return durationDays;
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

    public static int calculateMissingDays(Date startDate, Date endDate) {
        return (int) TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());
    }


    public static Date parseDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }

        if (date.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            formatter.setLenient(false);
            try {
                return formatter.parse(date);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
