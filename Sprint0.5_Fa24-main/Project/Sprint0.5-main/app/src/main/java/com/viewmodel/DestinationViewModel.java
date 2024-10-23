package com.viewmodel;

import static java.security.AccessController.getContext;

import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.model.Destination;
import com.model.UserDestinationData;

import java.util.Date;

public class DestinationViewModel extends ViewModel {
    public boolean logTravel(String end, String start, String destination)
    {
        //todo add in checking to see if start date is before end date, prob convert them to date objects
        if(isValidDate(end) && isValidDate(start) && isValidDestination(destination))
        {
            UserDestinationData userDestinationData = CurrentUserInfo.getInstance().getUserDestinationData();
            userDestinationData.addDestination(new Destination(destination));
            CurrentUserInfo.getInstance().updateDestinationData();
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

    private boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }

//    private Date stringToDate(String date){
//        //todo implement
//    }
}
