package com.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.sprintproject.R;
import com.model.Post;
import com.model.PostFactory;
import com.viewmodel.CurrentUserInfo;

import java.util.Calendar;
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
            PostFactory postFactory = new PostFactory();
            String[] postDetails = new String[7];
            postDetails[0] = destinationInput.getText().toString();
            postDetails[1] = startDateInput.getText().toString();
            postDetails[2] = endDateInput.getText().toString();
            postDetails[3] = accommodationsInput.getText().toString();
            postDetails[4] = diningReservationInput.getText().toString();
            postDetails[5] = ratingInput.getText().toString();
            postDetails[6] = notesInput.getText().toString();
            Post post = postFactory.createPost(getContext(),
                    CurrentUserInfo.getInstance().getUser().getUsername(), postDetails);
            if (post != null) {
                listener.onPostCreated(post);
                dismiss();
            }
        });
        return view;
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

    public interface OnPostCreatedListener {
        void onPostCreated(Post post);
    }
}
