package com.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sprintproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.model.DiningReservation;
import com.model.Reservation;
import com.viewmodel.CurrentUserInfo;
import com.viewmodel.DiningViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DiningFragment extends Fragment {

    private LinearLayout reservationsContainer;
    private FloatingActionButton addReservationFab;
    private DiningViewModel diningViewModel; // Instance of ViewModel

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining, container, false);
        reservationsContainer = view.findViewById(R.id.reservationsContainer);
        addReservationFab = view.findViewById(R.id.fabAddReservation);
        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class); // Initialize ViewModel

        addReservationFab.setOnClickListener(v -> showAddReservationDialog());
        loadReservations(); // Load existing reservations if any

        return view;
    }

    private void showAddReservationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_reservation, null);
        builder.setView(dialogView);

        final EditText locationInput = dialogView.findViewById(R.id.locationInput);
        final EditText timeInput = dialogView.findViewById(R.id.timeInput);
        final EditText websiteInput = dialogView.findViewById(R.id.websiteInput);
        final EditText dateInput = dialogView.findViewById(R.id.dateInput); // Added date input

        setupDatePicker(dateInput); // Set up the date picker dialog

        Button addButton = dialogView.findViewById(R.id.addReservationButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        final AlertDialog dialog = builder.create();

        addButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString();
            String time = timeInput.getText().toString();
            String website = websiteInput.getText().toString();
            String date = dateInput.getText().toString();  // Use the selected date

            if (!location.isEmpty() && !time.isEmpty() && !website.isEmpty() && !date.isEmpty()) {
                if (diningViewModel.addDiningReservation(location, website, time, date)) {
                    loadReservations();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Invalid or duplicate reservation", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void setupDatePicker(EditText dateInput) {
        dateInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year1, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        dateInput.setText(dateFormat.format(selectedDate.getTime()));
                    }, year, month, day);
            datePickerDialog.show();
        });
    }


    private void loadReservations() {
        reservationsContainer.removeAllViews();
        List<DiningReservation> reservations = diningViewModel.getReservations(); // Assuming this gets from db
        for (DiningReservation reservation : reservations) {
            displayReservation(reservation);
        }
    }

    private void displayReservation(DiningReservation reservation) {
        View reservationView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_item, reservationsContainer, false);
        TextView restaurantName = reservationView.findViewById(R.id.restaurantName);
        TextView restaurantDetails = reservationView.findViewById(R.id.restaurantDetails);
        TextView reservationDetails = reservationView.findViewById(R.id.reservationDetails);
        TextView reservationDate = reservationView.findViewById(R.id.reservationDate); // New TextView for date

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // Choose your desired format
        String formattedDate = reservation.getDate() != null ? dateFormat.format(reservation.getDate()) : "No Date"; // Format the Date object to String

        restaurantName.setText(String.format("%s", reservation.getLocation()));
        String stars = "";
        for (int i = 0; i < reservation.getReviewStars(); i++) {
            stars += "★";
        }
        for (int i = 0; i < 5 - reservation.getReviewStars(); i++) {
            stars += "☆";
        }
        restaurantDetails.setText(String.format("%s %s", reservation.getWebsite(), stars));
        reservationDetails.setText(String.format("Date: %s, Time: %s", reservation.parseDate(), reservation.parseTime()));
        reservationDate.setText(formattedDate); // Set the formatted date string

        reservationsContainer.addView(reservationView);
    }


}
