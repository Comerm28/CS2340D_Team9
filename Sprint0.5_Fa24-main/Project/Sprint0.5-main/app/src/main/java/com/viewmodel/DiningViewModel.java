package com.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import com.model.Database;
import com.model.Reservation;
import com.model.UserDiningData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiningViewModel extends ViewModel {
    private CurrentUserInfo currentUserInfo;
    private List<Reservation> reservations; // Placeholder for reservations
    public DiningViewModel() {
        currentUserInfo = CurrentUserInfo.getInstance();
        reservations = new ArrayList<>();
    }
    
    public boolean addDiningReservation(String location, String website, String time, String date)
    {
        Date inputDate = isValidDate(date);
        if(!validReservation(location, website, time) && inputDate != null)
        {
            return false;
        }
        int t = Integer.parseInt(time);
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        userDiningData.addReservation(new Reservation(t, location, website, inputDate));
        Database.getInstance().updateDiningData(currentUserInfo.getUser(), userDiningData);

        return true;
    }

    public List<Reservation> getReservations() {
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        return userDiningData.getReservations();
    }


    private boolean validReservation(String location, String website, String time) {
        if(!website.matches(".*\\..*") || !time.matches("^([0-9]{2,4})$"))
        {
            return false;
        }
        UserDiningData userDiningData = currentUserInfo.getUserDiningData();
        for(Reservation r : userDiningData.getReservations())
        {
            if(r.getLocation().equals(location))
            {
                return false;
            }
        }

        return true;
    }

    public boolean isPastReservation(Reservation reservation)
    {
        int time = reservation.getTime();
        int hours, minutes;
        if (time < 1000) {
            hours = time / 100;
            minutes = time % 100;
        } else {
            hours = time / 100;
            minutes = time % 100;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reservation.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return currentUserInfo.getUserActualDateAndTime().compareTo(calendar.getTime()) > 0;
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
