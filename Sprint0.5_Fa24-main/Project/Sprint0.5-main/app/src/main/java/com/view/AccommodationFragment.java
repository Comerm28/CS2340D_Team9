package com.view;

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
    private LinearLayout accommodationsContainer;
    private AccomodationsViewModel accommodationsViewModel;
    private FloatingActionButton addAccommodationFab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation, container, false);

        accommodationsContainer = view.findViewById(R.id.accommodationsContainer);
        accommodationsViewModel = new ViewModelProvider(this).get(AccomodationsViewModel.class);

        loadAccommodations();

        addAccommodationFab = view.findViewById(R.id.fabAddAccommodation);
        addAccommodationFab.setOnClickListener(v -> showAddAccommodationDialog());

        return view;
    }

    private void loadAccommodations() {
        List<AccommodationReservation> accommodations = accommodationsViewModel.getAccommodations();
        accommodationsContainer.removeAllViews();
        for (AccommodationReservation accommodation : accommodations) {
            View accommodationView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_item, accommodationsContainer, false);
            ((TextView) accommodationView.findViewById(R.id.locationView)).setText(accommodation.getLocation());
            ((TextView) accommodationView.findViewById(R.id.dateView)).setText(String.format("Check-in: %s - Check-out: %s",
                    new SimpleDateFormat("MM/dd/yyyy").format(accommodation.getCheckInDate()),
                    new SimpleDateFormat("MM/dd/yyyy").format(accommodation.getCheckOutDate())));
            ((TextView) accommodationView.findViewById(R.id.roomInfoView)).setText(String.format("Rooms: %d, Type: %s",
                    accommodation.getNumRooms(), accommodation.getRoomType().displayString));
            accommodationsContainer.addView(accommodationView);
        }
    }

    private void showAddAccommodationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_accommodation, null);
        builder.setView(dialogView);

        EditText locationInput = dialogView.findViewById(R.id.etLocation);
        EditText checkInInput = dialogView.findViewById(R.id.etCheckIn);
        EditText checkOutInput = dialogView.findViewById(R.id.etCheckOut);
        EditText numberOfRoomsInput = dialogView.findViewById(R.id.etNumberOfRooms);
        Spinner roomTypeSpinner = dialogView.findViewById(R.id.spRoomType);

        setupDatePicker(checkInInput);
        setupDatePicker(checkOutInput);

        ArrayAdapter<AccommodationReservation.RoomType> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, AccommodationReservation.RoomType.values());
        roomTypeSpinner.setAdapter(spinnerAdapter);

        AlertDialog dialog = builder.create();

        Button addButton = dialogView.findViewById(R.id.btnAddAccommodation);
        addButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString();
            String checkInDate = checkInInput.getText().toString();
            String checkOutDate = checkOutInput.getText().toString();
            int numRooms = Integer.parseInt(numberOfRoomsInput.getText().toString());
            AccommodationReservation.RoomType selectedRoomType = (AccommodationReservation.RoomType) roomTypeSpinner.getSelectedItem();

            if (accommodationsViewModel.addAccommodation(checkInDate, checkOutDate, location, numRooms, selectedRoomType)) {
                loadAccommodations();
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Failed to add accommodation. Check your inputs.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void setupDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) ->
                    editText.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Calendar.Builder().setDate(year, month, dayOfMonth).build().getTime())),
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
}
