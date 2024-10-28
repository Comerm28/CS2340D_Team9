
package com.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.model.Destination;
import com.model.UserDestinationData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DestinationViewModel extends ViewModel {
    public boolean logTravel(String end, String start, String destination) {
        Date st = isValidDate(start);
        Date en = isValidDate(end);
        if (st != null && en != null && st.compareTo(en) <= 0 && isValidDestination(destination)) {
            UserDestinationData userDestinationData = CurrentUserInfo.getInstance()
                    .getUserDestinationData();
            userDestinationData.addDestination(new Destination(destination, st, en));
            CurrentUserInfo.getInstance().updateDestinationData();
            return true;
        }

        return false;
    }

    public boolean isValidDestination(String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            return false;
        }
        return destination.matches("^[a-zA-Z0-9_]+$");
    }

    private Date isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }

        if (date.matches("^\\d{2}-\\d{2}-\\d{4}$")) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =
                    new SimpleDateFormat("MM-dd-yyyy");
            formatter.setLenient(false);
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    //meant for the calculateduration method
    //dont use for the calculate button
    public int getDurationFromStrings(String start, String end) {
        Date st = isValidDate(start);
        Date en = isValidDate(end);
        if (st != null && en != null) {
            return (int) Destination.calculateMissingDays(st, en);
        }
        return -1;
    }


    public String getEndDateFromInfo(String duration, String startDate) {
        Date end = isValidDate(startDate);
        if (!duration.matches("\\d+") || end == null) {
            return null;
        }

        int dur = Integer.parseInt(duration);
        if (dur < 0) {
            return null;
        }

        return Destination.calculateMissingStartDate(dur, end).toString();
    }

    public String getStartDateFromInfo(String duration, String endDate) {
        Date start = isValidDate(endDate);
        if (!duration.matches("\\d+") || start == null) {
            return null;
        }

        int dur = Integer.parseInt(duration);
        if (dur < 0) {
            return null;
        }

        return Destination.calculateMissingStartDate(dur, start).toString();
    }
}
