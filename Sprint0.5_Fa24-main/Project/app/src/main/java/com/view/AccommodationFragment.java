package com.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.model.AccommodationReservation;
import com.viewmodel.AccomodationsViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AccommodationFragment extends Fragment {
    private final String dateFormat = "MM/dd/yyyy";
    private AccomodationsViewModel accommodationsViewModel;
    private LinearLayout accommodationsContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation, container, false);

        accommodationsContainer = view.findViewById(R.id.accommodationsContainer);
        accommodationsViewModel = new ViewModelProvider(this).get(AccomodationsViewModel.class);
        FloatingActionButton addAccommodationFab = view.findViewById(R.id.fabAddAccommodation);
        Button sortCheckInButton = view.findViewById(R.id.sortByCheckInButton);
        Button sortCheckOutButton = view.findViewById(R.id.sortByCheckOutButton);

        addAccommodationFab.setOnClickListener(v -> showAddAccommodationDialog());
        sortCheckInButton.setOnClickListener(v -> {
            accommodationsViewModel.sortAccommodationsByCheckIn();
            loadAccommodations();  // Reload the UI immediately after sorting
        });
        sortCheckOutButton.setOnClickListener(v -> {
            accommodationsViewModel.sortAccommodationsByCheckOut();
            loadAccommodations();  // Reload the UI immediately after sorting
        });

        loadAccommodations();  // Initial load

        return view;
    }

    private void loadAccommodations() {
        accommodationsContainer.removeAllViews();
        List<AccommodationReservation> accommodations = accommodationsViewModel.getAccommodations();
        for (AccommodationReservation accommodation : accommodations) {
            displayAccommodation(accommodation);
        }
    }

    @SuppressLint("DefaultLocale")
    private void displayAccommodation(AccommodationReservation accommodation) {
        View accommodationView = LayoutInflater.from(getContext())
                .inflate(R.layout.accommodation_item, accommodationsContainer, false);
        TextView reviewStarsText = accommodationView.findViewById(R.id.reviewStars);
        TextView locationView = accommodationView.findViewById(R.id.locationView);
        TextView dateView = accommodationView.findViewById(R.id.dateView);
        TextView roomInfoView = accommodationView.findViewById(R.id.roomInfoView);

        // Format the review stars
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < accommodation.getReviewStars(); i++) {
            stars.append("★");
        }
        for (int i = accommodation.getReviewStars(); i < 5; i++) {
            stars.append("☆");
        }
        locationView.setText(accommodation.getLocation());
        reviewStarsText.setText(stars.toString());
        dateView.setText(String.format("Check-in: %s - Check-out: %s",
                new SimpleDateFormat(dateFormat).format(accommodation.getCheckInDate()),
                new SimpleDateFormat(dateFormat).format(accommodation
                        .getCheckOutDate())));
        roomInfoView.setText(String.format("Rooms: %d, Type: %s",
                accommodation.getNumRooms(), accommodation.getRoomType().getDisplayString()));

        // Check if the accommodation is past and update the appearance
        if (accommodationsViewModel.isPastAccommodation(accommodation)) {
            dateView.setTextColor(getContext().getResources().getColor(R.color.expired_color));
            dateView.setText(dateView.getText() + " (Expired)");
        }

        accommodationsContainer.addView(accommodationView);
    }


    private void showAddAccommodationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_accommodation, null);
        builder.setView(dialogView);

        EditText locationInput = dialogView.findViewById(R.id.etLocation);
        EditText checkInInput = dialogView.findViewById(R.id.etCheckIn);
        EditText checkOutInput = dialogView.findViewById(R.id.etCheckOut);
        EditText numberOfRoomsInput = dialogView.findViewById(R.id.etNumberOfRooms);
        Spinner roomTypeSpinner = dialogView.findViewById(R.id.spRoomType);

        setupDatePicker(checkInInput);
        setupDatePicker(checkOutInput);

        ArrayAdapter<AccommodationReservation.RoomType> spinnerAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                        AccommodationReservation.RoomType.values());
        roomTypeSpinner.setAdapter(spinnerAdapter);

        AlertDialog dialog = builder.create();

        Button addButton = dialogView.findViewById(R.id.btnAddAccommodation);
        addButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString();
            String checkInDate = checkInInput.getText().toString();
            String checkOutDate = checkOutInput.getText().toString();
            String numRooms = numberOfRoomsInput.getText().toString();
            AccommodationReservation.RoomType selectedRoomType =
                    (AccommodationReservation.RoomType) roomTypeSpinner.getSelectedItem();

            if (accommodationsViewModel.addAccommodation(checkInDate, checkOutDate,
                    location, numRooms, selectedRoomType)) {
                loadAccommodations();
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Failed to add accommodation. Check your inputs.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void setupDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                    editText.setText(new SimpleDateFormat(dateFormat)
                            .format(new Calendar.Builder().setDate(year, month, dayOfMonth)
                                    .build().getTime())),
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
}
