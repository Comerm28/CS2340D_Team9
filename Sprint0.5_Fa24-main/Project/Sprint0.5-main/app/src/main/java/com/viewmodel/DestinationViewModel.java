package com.viewmodel;

import static java.lang.Integer.*;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserDestinationData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DestinationViewModel extends ViewModel {
    public boolean logTravel(String end, String start, String destination) {
        Date st = isValidDate(start);
        Date en = isValidDate(end);
        if(st != null && en != null && st.compareTo(en) <= 0 && isValidDestination(destination))
        {
            UserDestinationData userDestinationData = CurrentUserInfo.getInstance().getUserDestinationData();
            if(userDestinationData.isCollaborating())
            {
                userDestinationData = Database.getInstance().getUserDestinationData(userDestinationData.getCollaboratorUsername());
                userDestinationData.addDestination(new Destination(destination, st, en));
                Database.getInstance().updateDestinationData(new User(userDestinationData.getCollaboratorUsername()), userDestinationData);
            }
            else{
                userDestinationData.addDestination(new Destination(destination, st, en));
                CurrentUserInfo.getInstance().updateDestinationData();
            }
            return true;
        }

        return false;
    }

    private boolean isValidDestination(String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            return false;
        }
        return destination.matches("^[a-zA-Z0-9_]+$");
    }

    private Date isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }

        if(date.matches("^\\d{2}-\\d{2}-\\d{4}$"))
        {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            formatter.setLenient(false);
            try
            {
                return formatter.parse(date);
            }
            catch(ParseException e) {
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
        if(st != null && en != null)
        {
            return (int) Destination.calculateMissingDays(st, en);
        }
        return -1;
    }

    //these three are meant for the calculate button
    public String getDurationFromDates(String startDate, String endDate)
    {
        Date st = isValidDate(startDate);
        Date en = isValidDate(endDate);
        if(st != null && en != null && st.compareTo(en) <= 0)
        {
            int dur = (int) Destination.calculateMissingDays(st, en);
            UserDestinationData currentDestData = CurrentUserInfo.getInstance().getUserDestinationData();
            if(currentDestData.isCollaborating())
            {
                currentDestData = Database.getInstance().getUserDestinationData(currentDestData.getCollaboratorUsername());
                currentDestData.setAllotedVacationDays(dur);
                Database.getInstance().updateDestinationData(new User(currentDestData.getCollaboratorUsername()), currentDestData);
            }
            else {
                currentDestData.setAllotedVacationDays(dur);
                Database.getInstance().updateDestinationData(CurrentUserInfo.getInstance().getUser(), currentDestData);
            }
            return Integer.toString(dur);
        }

        return null;
    }

    public String getEndDateFromInfo(String duration, String startDate)
    {
        Date en = isValidDate(startDate);
        if(duration.matches("\\d+") && en != null)
        {
            int dur = Integer.parseInt(duration);
            if(dur >= 0){
                Date start = Destination.calculateMissingStartDate(dur, en);
                UserDestinationData currentDestData = CurrentUserInfo.getInstance().getUserDestinationData();
                if(currentDestData.isCollaborating())
                {
                    currentDestData = Database.getInstance().getUserDestinationData(currentDestData.getCollaboratorUsername());
                    currentDestData.setAllotedVacationDays(dur);
                    Database.getInstance().updateDestinationData(new User(currentDestData.getCollaboratorUsername()), currentDestData);
                }
                else {
                    currentDestData.setAllotedVacationDays(dur);
                    Database.getInstance().updateDestinationData(CurrentUserInfo.getInstance().getUser(), currentDestData);
                }
                return start.toString();
            }

            return null;
        }

        return null;
    }

    public String getStartDateFromInfo(String duration, String endDate)
    {
        Date st = isValidDate(endDate);
        if(duration.matches("\\d+") && st != null)
        {
            int dur = Integer.parseInt(duration);
            if(dur >= 0){
                Date start = Destination.calculateMissingStartDate(dur, st);
                UserDestinationData currentDestData = CurrentUserInfo.getInstance().getUserDestinationData();
                if(currentDestData.isCollaborating())
                {
                    currentDestData = Database.getInstance().getUserDestinationData(currentDestData.getCollaboratorUsername());
                    currentDestData.setAllotedVacationDays(dur);
                    Database.getInstance().updateDestinationData(new User(currentDestData.getCollaboratorUsername()), currentDestData);
                }
                else {
                    currentDestData.setAllotedVacationDays(dur);
                    Database.getInstance().updateDestinationData(CurrentUserInfo.getInstance().getUser(), currentDestData);
                }
                return start.toString();
            }

            return null;
        }

        return null;
    }
}