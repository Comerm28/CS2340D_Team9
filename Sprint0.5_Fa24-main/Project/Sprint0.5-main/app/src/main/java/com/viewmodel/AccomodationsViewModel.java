package com.viewmodel;


import androidx.lifecycle.ViewModel;

import com.model.AccommodationReservation;
import com.model.Database;
import com.model.UserAccommodationData;

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

<<<<<<< Updated upstream
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
=======
    public boolean addAccommodation(String checkIn, String checkOut, String location,
                                   String numRooms, AccommodationReservation.RoomType roomType) {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        boolean worked = false;
        if (isValidAccomodation(checkIn, checkOut, location, numRooms)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date checkInDate = sdf.parse(checkIn);
                Date checkOutDate = sdf.parse(checkOut);
                int rooms = Integer.parseInt(numRooms);
                AccommodationReservation newAccommodationReservation = new AccommodationReservation(
                        location, checkInDate, checkOutDate, rooms, roomType);
                userAccommodationData.addReservation(newAccommodationReservation);
                worked = true;
>>>>>>> Stashed changes
            } catch (Exception e) {
                return false;
                // Return false if date parsing fails or room type code is out of bounds
            }
        }
        Database db = Database.getInstance();
        db.updateUserAccommodationData(currentUserInfo.getUser(), new UserAccommodationData(currentUserInfo.getUser().getUsername()));

<<<<<<< Updated upstream
=======
    private boolean isValidAccomodation(String checkin, String checkOut, String location,
                                        String numRooms) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date st;
        Date en;
        int rooms;
        try {
            st = sdf.parse(checkin);
            en = sdf.parse(checkOut);
            rooms = Integer.parseInt(numRooms);
        } catch (Exception e) {
            return false;
        }

        if (st.compareTo(en) > 0) {
            return false;
        }

        if (rooms <= 0) {
            return false;
        }

        for (AccommodationReservation a
                : currentUserInfo.getUserAccommodationData().getReservations()) {
            if (location.equals(a.getLocation())) {
                return false;
            }
        }
>>>>>>> Stashed changes

        return true;
    }

<<<<<<< Updated upstream
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
=======
    public boolean isPastAccommodation(AccommodationReservation lodging) {
        return lodging.getCheckOutDate().before(new Date());
    }

    public void sortAccommodationsByCheckIn() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckInSort().sort(
                new ArrayList<>(userAccommodationData.getReservations()));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }

    public void sortAccommodationsByCheckOut() {
        UserAccommodationData userAccommodationData = currentUserInfo.getUserAccommodationData();
        List<AccommodationReservation> sortedAccommodations = new CheckOutSort().sort(
                new ArrayList<>(userAccommodationData.getReservations()));
        userAccommodationData.getReservations().clear();
        userAccommodationData.getReservations().addAll(sortedAccommodations);
    }
>>>>>>> Stashed changes

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
