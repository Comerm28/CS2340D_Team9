package com.view;

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
        EditText durationInput = view.findViewById(R.id.durationInput);
        EditText accommodationsInput = view.findViewById(R.id.accommodationsInput);
        EditText diningReservationInput = view.findViewById(R.id.diningReservationInput);
        EditText transportationInput = view.findViewById(R.id.transportationInput);
        EditText notesInput = view.findViewById(R.id.notesInput);
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            Post post = new Post(
                    "Username1", // Placeholder username
                    destinationInput.getText().toString(),
                    durationInput.getText().toString(),
                    accommodationsInput.getText().toString(),
                    diningReservationInput.getText().toString(),
                    transportationInput.getText().toString(),
                    notesInput.getText().toString()
            );
            listener.onPostCreated(post);
            dismiss();
        });

        return view;
    }
}
