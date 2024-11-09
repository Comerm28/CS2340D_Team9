package com.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.model.AccommodationReservation;
import com.model.Database;
import com.model.Destination;
import com.model.User;
import com.model.UserAccommodationData;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class AccomodationsViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    private List<AccommodationReservation> lodgings;

    public AccomodationsViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
        //todo get useraccomodation data from currentuserinfo and update it and update database
        lodgings = new ArrayList<>();
    }

    //temporary to make view show accomodations
    public List<AccommodationReservation> getAccommodations() {
        return lodgings;
    }

    public boolean addAccommodation(String check_in, String check_out, String location,
                                   int num_rooms, AccommodationReservation.RoomType roomType)
    {
        if(isValidAccomodation(check_in, check_out, location)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date checkInDate = sdf.parse(check_in);
                Date checkOutDate = sdf.parse(check_out);

                AccommodationReservation newAccommodationReservation = new AccommodationReservation(location, checkInDate, checkOutDate, num_rooms, roomType);
                lodgings.add(newAccommodationReservation);
                return true;
            } catch (Exception e) {
                return false; // Return false if date parsing fails or room type code is out of bounds
            }
        }
        Database db = Database.getInstance();
        db.updateUserAccommodationData(currentUserInfo.getUser(), new UserAccommodationData(currentUserInfo.getUser().getUsername()));


        return true;
    }

    private boolean isValidAccomodation(String check_in, String check_out, String location)
    {
        //todo implement checking on duplicates, make sure room type exists, make sure dates are valid
        return true;
    }

    public boolean isPastAccomodation(AccommodationReservation lodging)
    {
        return currentUserInfo.getUserActualDateAndTime().compareTo(lodging.getCheckInDate()) > 0;
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
}
