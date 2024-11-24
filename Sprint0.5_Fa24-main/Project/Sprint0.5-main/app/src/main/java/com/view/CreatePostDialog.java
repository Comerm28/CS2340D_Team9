package com.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.sprintproject.R;
import com.model.Destination;
import com.model.Post;
import com.viewmodel.CurrentUserInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreatePostDialog extends DialogFragment {

    private final OnPostCreatedListener listener;

    public CreatePostDialog(OnPostCreatedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_post, container, false);

        EditText destinationInput = view.findViewById(R.id.destinationInput);
        EditText startDateInput = view.findViewById(R.id.startDateInput);
        EditText endDateInput = view.findViewById(R.id.endDateInput);
        EditText accommodationsInput = view.findViewById(R.id.accommodationsInput);
        EditText diningReservationInput = view.findViewById(R.id.diningReservationInput);
        EditText ratingInput = view.findViewById(R.id.ratingInput);
        EditText notesInput = view.findViewById(R.id.notesInput);
        Button saveButton = view.findViewById(R.id.saveButton);

        setupDatePicker(startDateInput);
        setupDatePicker(endDateInput);

        saveButton.setOnClickListener(v -> {
            Date startDate = Destination.parseDate(startDateInput.getText().toString());
            Date endDate = Destination.parseDate(endDateInput.getText().toString());
            if (startDate == null || endDate == null) {
                Toast.makeText(
                        getContext(), "Invalid date format", Toast.LENGTH_SHORT
                ).show();
                return;
            }
            if (startDate.compareTo(endDate) > 0) {
                Toast.makeText(getContext(), "End date cannot be before start date.", Toast.LENGTH_SHORT).show();
                return;
            }

            String destination = destinationInput.getText().toString().trim();
            if (destination.isEmpty()) {
                Toast.makeText(getContext(), "Destination cannot be blank.", Toast.LENGTH_SHORT).show();
                return;
            }

            int rating;
            try {
                rating = Integer.parseInt(ratingInput.getText().toString().trim());
                if (rating < 0 || rating > 5) {
                    Toast.makeText(getContext(), "Rating must be between 0 and 5.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(
                        getContext(), "Failed to parse rating", Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Post post = new Post();
            post.setUsername(CurrentUserInfo.getInstance().getUser().getUsername());
            post.setDestination(destination);
            post.setStartDate(startDate);
            post.setEndDate(endDate);
            post.setAccommodations(accommodationsInput.getText().toString().trim());
            post.setDiningReservation(diningReservationInput.getText().toString().trim());
            post.setRating(rating);
            post.setNotes(notesInput.getText().toString().trim());
            listener.onPostCreated(post);
            dismiss();
        });

        return view;
    }

    public interface OnPostCreatedListener {
        void onPostCreated(Post post);
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
}
