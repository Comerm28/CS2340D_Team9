package com.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sprintproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.model.DiningReservation;
import com.viewmodel.DiningViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DiningFragment extends Fragment {

    private LinearLayout reservationsContainer;
    private DiningViewModel diningViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_dining, container, false);
        reservationsContainer = view.findViewById(R.id.reservationsContainer);
        FloatingActionButton addReservationFab = view.findViewById(R.id.fabAddReservation);
        // Assumed button ID in your layout
        Button sortDateButton = view.findViewById(R.id.sortByDateButton);
        // Assumed button ID in your layout
        Button sortTimeButton = view.findViewById(R.id.sortByTimeButton);
        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class);

        addReservationFab.setOnClickListener(v -> showAddReservationDialog());
        Button sortDateButton.setOnClickListener(v -> {
            diningViewModel.sortReservationsByDate();
            loadReservations();  // Reload the UI immediately after sorting
        });
        Button sortTimeButton.setOnClickListener(v -> {
            diningViewModel.sortReservationsByTime();
            loadReservations();  // Reload the UI immediately after sorting
        });
        loadReservations();

        return view;
    }

    private void showAddReservationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView =
                LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_reservation, null);
        builder.setView(dialogView);

        final EditText locationInput = dialogView.findViewById(R.id.locationInput);
        final EditText timeInput = dialogView.findViewById(R.id.timeInput);
        final EditText websiteInput = dialogView.findViewById(R.id.websiteInput);
        final EditText dateInput = dialogView.findViewById(R.id.dateInput);

        setupDatePicker(dateInput);
        setupTimePicker(timeInput);

        Button addButton = dialogView.findViewById(R.id.addReservationButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        final AlertDialog dialog = builder.create();

        addButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString();
            String time = timeInput.getText().toString();
            String website = websiteInput.getText().toString();
            String date = dateInput.getText().toString();

            if (!location.isEmpty() && !time.isEmpty() && !website.isEmpty() && !date.isEmpty()) {
                if (diningViewModel.addDiningReservation(location, website, time, date)) {
                    loadReservations();
                    dialog.dismiss();
                } else {
                    Toast.makeText(
                        getContext(), "Invalid or duplicate reservation", Toast.LENGTH_SHORT
                    ).show();
                }
            } else {
                Toast.makeText(
                        getContext(), "All fields must be filled", Toast.LENGTH_SHORT
                ).show();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void setupDatePicker(EditText dateInput) {
        dateInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String formattedDate = String.format(
                    Locale.getDefault(), "%02d-%02d-%04d", month + 1, dayOfMonth, year
                );
                dateInput.setText(formattedDate);
            }, calendar.get(Calendar.YEAR),
               calendar.get(Calendar.MONTH),
               calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void setupTimePicker(EditText timeInput) {
        timeInput.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);
            new TimePickerDialog(getContext(), (view, hourOfDay, minuteOfHour) -> {
                String formattedTime =
                        String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
                timeInput.setText(formattedTime);
            }, hour, minute, true).show();
        });
    }

    private void loadReservations() {
        reservationsContainer.removeAllViews();
        List<DiningReservation> reservations = diningViewModel.getReservations();
        for (DiningReservation reservation : reservations) {
            displayReservation(reservation);
        }
    }

    private void displayReservation(DiningReservation reservation) {
        View reservationView = LayoutInflater.from(getContext())
                .inflate(R.layout.reservation_item, reservationsContainer, false);
        TextView restaurantName = reservationView.findViewById(R.id.restaurantName);
        TextView restaurantDetails = reservationView.findViewById(R.id.restaurantDetails);
        TextView reservationDetails = reservationView.findViewById(R.id.reservationDetails);
        // Reusing this TextView for stars
        TextView reviewStarsText = reservationView.findViewById(R.id.reviewStars);

        restaurantName.setText(reservation.getLocation());
        restaurantDetails.setText(reservation.getWebsite());
        reservationDetails.setText(
            String.format("Time: %s, Date: %s", reservation.parseTime(), reservation.parseDate())
        );

        // Format the review stars
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < reservation.getReviewStars(); i++) {
            stars.append("★");
        }
        for (int i = reservation.getReviewStars(); i < 5; i++) {
            stars.append("☆");
        }
        reviewStarsText.setText(stars.toString()); // Display stars in the reused TextView

        // Check if the reservation is past and update the appearance
        if (diningViewModel.isPastReservation(reservation)) {
            reservationDetails.setTextColor(
                getContext().getResources().getColor(R.color.expired_color)
            ); // Change color to indicate expired
            reservationDetails.setText(reservationDetails.getText() + " (Expired)");
        }

        reservationsContainer.addView(reservationView);
    }

}
