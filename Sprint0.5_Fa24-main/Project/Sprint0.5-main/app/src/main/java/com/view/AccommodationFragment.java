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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.sprintproject.R;
import com.model.Lodging;
import com.viewmodel.AccomodationsViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AccommodationFragment extends Fragment {
    private RecyclerView accommodationsRecyclerView;
    private AccommodationsAdapter accommodationsAdapter;
    private AccomodationsViewModel accommodationsViewModel;
    private FloatingActionButton addAccommodationFab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation, container, false);

        accommodationsRecyclerView = view.findViewById(R.id.accommodationsRecyclerView);
        accommodationsViewModel = new ViewModelProvider(this).get(AccomodationsViewModel.class);
        accommodationsAdapter = new AccommodationsAdapter(accommodationsViewModel.getLodgings());
        accommodationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accommodationsRecyclerView.setAdapter(accommodationsAdapter);

        FloatingActionButton addAccommodationFab = view.findViewById(R.id.fabAddAccommodation);
        addAccommodationFab.setOnClickListener(v -> showAddAccommodationDialog());

        return view;
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
        Button addButton = dialogView.findViewById(R.id.btnAddAccommodation);

        setupDatePicker(checkInInput);
        setupDatePicker(checkOutInput);

        ArrayAdapter<Lodging.RoomType> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Lodging.RoomType.values());
        roomTypeSpinner.setAdapter(spinnerAdapter);

        AlertDialog dialog = builder.create();

        addButton.setOnClickListener(v -> {
            try {
                String location = locationInput.getText().toString();
                String checkInDate = checkInInput.getText().toString();
                String checkOutDate = checkOutInput.getText().toString();
                int numRooms = Integer.parseInt(numberOfRoomsInput.getText().toString());
                Lodging.RoomType selectedRoomType = (Lodging.RoomType) roomTypeSpinner.getSelectedItem();

                if (accommodationsViewModel.addAccommodation(checkInDate, checkOutDate, location, numRooms, selectedRoomType.ordinal())) {
                    accommodationsAdapter.notifyDataSetChanged();
                    dialog.dismiss();  // Correctly dismiss the dialog
                } else {
                    Toast.makeText(getContext(), "Failed to add accommodation. Check your inputs.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    private class AccommodationsAdapter extends RecyclerView.Adapter<AccommodationsAdapter.ViewHolder> {
        private final List<Lodging> lodgings;

        AccommodationsAdapter(List<Lodging> lodgings) {
            this.lodgings = lodgings;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Lodging lodging = lodgings.get(position);
            holder.locationView.setText(lodging.getLocation());
            holder.dateView.setText(String.format("Check-in: %s - Check-out: %s",
                    new SimpleDateFormat("MM/dd/yyyy").format(lodging.getCheckInDate()),
                    new SimpleDateFormat("MM/dd/yyyy").format(lodging.getCheckOutDate())));
            holder.roomInfoView.setText(String.format("Rooms: %d, Type: %s",
                    lodging.getNumberOfRooms(), lodging.getRoomType()));
        }

        @Override
        public int getItemCount() {
            return lodgings.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView locationView, dateView, roomInfoView;

            ViewHolder(View itemView) {
                super(itemView);
                locationView = itemView.findViewById(R.id.locationView);
                dateView = itemView.findViewById(R.id.dateView);
                roomInfoView = itemView.findViewById(R.id.roomInfoView);
            }
        }
    }
}
