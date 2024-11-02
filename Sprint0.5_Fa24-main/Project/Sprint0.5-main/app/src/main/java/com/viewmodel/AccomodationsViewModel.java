package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.Lodging;
import com.model.Reservation;

import java.util.Date;

public class AccomodationsViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;

    public AccomodationsViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
    }

    public boolean addAccomodation(String check_in, String check_out, String location,
                                   int num_rooms, int room_type_code)
    {
        if(isValidAccomodation(check_in, check_out, location, room_type_code)) {
            return false;
        }
        //todo make object and update database

        return true;
    }

    private boolean isValidAccomodation(String check_in, String check_out, String location,
                                        int room_type_code)
    {
        //todo implement checking on duplicates, make sure room type exists, make sure dates are valid
        return true;
    }

    public boolean isPastAccomodation(Lodging lodging)
    {
        //todo new date needs to be actual reservation date
        return currentUserInfo.getUserActualDateAndTime().compareTo(new Date()) > 0;
    }
}
