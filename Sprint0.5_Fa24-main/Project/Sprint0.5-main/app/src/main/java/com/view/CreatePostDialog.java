package com.view;

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
import com.model.Database;
import com.model.Destination;
import com.model.Post;
import com.viewmodel.CurrentUserInfo;

import java.util.Date;

public class CreatePostDialog extends DialogFragment {

    private final OnPostCreatedListener listener;

    public interface OnPostCreatedListener {
        void onPostCreated(Post post);
    }

    public CreatePostDialog(OnPostCreatedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create_post, container, false);

        EditText destinationInput = view.findViewById(R.id.destinationInput);
        EditText startDateInput = view.findViewById(R.id.startDateInput);
        EditText endDateInput = view.findViewById(R.id.endDateInput);
        EditText accommodationsInput = view.findViewById(R.id.accommodationsInput);
        EditText diningReservationInput = view.findViewById(R.id.diningReservationInput);
        EditText ratingInput = view.findViewById(R.id.ratingInput);
        EditText notesInput = view.findViewById(R.id.notesInput);
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            Date startDate = Destination.parseDate(startDateInput.getText().toString());
            Date endDate = Destination.parseDate(endDateInput.getText().toString());
            if (startDate == null || endDate == null) {
                Toast.makeText(
                        getContext(), "Invalid date format", Toast.LENGTH_SHORT
                ).show();
                return;
            }

            int rating;
            try {
                rating = Integer.parseInt(ratingInput.getText().toString());
            } catch (Exception e) {
                Toast.makeText(
                        getContext(), "Failed to parse rating", Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Post post = new Post(
                    CurrentUserInfo.getInstance().getUser().getUsername(),
                    destinationInput.getText().toString(),
                    startDate,
                    endDate,
                    accommodationsInput.getText().toString(),
                    diningReservationInput.getText().toString(),
                    rating,
                    notesInput.getText().toString()
            );
            listener.onPostCreated(post);
            dismiss();
        });

        return view;
    }
}
