package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Reservation;

import java.util.Date;

public class DiningViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    public DiningViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
    }
    
    public boolean addDiningReservation(String location, String website, String time)
    {
        if(!validReservation(location, time))
        {
            return false;
        }
        //todo handle creation and addition of object into database

        return true;
    }

    private boolean validReservation(String Location, String time) {
        //todo check for duplicates and proper formatting of time
        return true;
    }

    public boolean isPastReservation(Reservation reservation)
    {
        //todo new date needs to be actual reservation date
        return currentUserInfo.getUserActualDateAndTime().compareTo(new Date()) > 0;
    }
}
