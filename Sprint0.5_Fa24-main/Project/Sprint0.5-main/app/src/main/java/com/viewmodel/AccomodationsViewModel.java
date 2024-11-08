package com.viewmodel;

import androidx.lifecycle.ViewModel;

import com.model.AccommodationReservation;
import com.model.Reservation;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class AccomodationsViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    private List<AccommodationReservation> lodgings;

    public AccomodationsViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
        lodgings = new ArrayList<>();
    }

    //temporary to make view show accomodations
    public List<AccommodationReservation> getLodgings() {
        return lodgings;
    }

    public boolean addAccommodation(String check_in, String check_out, String location, String website,
                                    int num_rooms, AccommodationReservation.RoomType roomType)
    {
        if(isValidAccomodation(check_in, check_out, location)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date checkInDate = sdf.parse(check_in);
                Date checkOutDate = sdf.parse(check_out);

                AccommodationReservation newAccommodationReservation = new AccommodationReservation(location, website, checkInDate, checkOutDate, num_rooms, roomType);
                lodgings.add(newAccommodationReservation);

                return true;
            } catch (Exception e) {
                return false; // Return false if date parsing fails or room type code is out of bounds
            }
        }
        //todo make object and update database

        return true;
    }

    private boolean isValidAccomodation(String check_in, String check_out, String location)
    {
        //todo implement checking on duplicates, make sure room type exists, make sure dates are valid
        return true;
    }

    public boolean isPastAccomodation(AccommodationReservation lodging)
    {
        //todo new date needs to be actual reservation date
        return currentUserInfo.getUserActualDateAndTime().compareTo(new Date()) > 0;
    }
}
