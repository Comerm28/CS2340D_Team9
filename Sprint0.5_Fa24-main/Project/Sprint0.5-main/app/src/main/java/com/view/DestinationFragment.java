package com.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.sprintproject.R;

import java.util.ArrayList;
import java.util.List;

public class DestinationFragment extends Fragment {

    private LinearLayout vacationTimeCalculatorForm;
    private EditText startDateInput, endDateInput, durationInput;
    private TextView calculatedField;
    private Button calculateVacationTimeButton, autoCalculateButton, logTravelButton, submitButton, cancelButton;
    private List<String> travelLogs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        // Initialize UI components
        TextView headerTextView = view.findViewById(R.id.headerText);
        headerTextView.setText("Destination");

        vacationTimeCalculatorForm = view.findViewById(R.id.vacationTimeCalculatorForm);
        vacationTimeCalculatorForm.setVisibility(View.GONE); // Initially hidden

        startDateInput = view.findViewById(R.id.startDateInput);
        endDateInput = view.findViewById(R.id.endDateInput);
        durationInput = view.findViewById(R.id.durationInput);
        calculatedField = view.findViewById(R.id.calculatedField);

        calculateVacationTimeButton = view.findViewById(R.id.calculateVacationTimeButton);
        autoCalculateButton = view.findViewById(R.id.autoCalculateButton);
        logTravelButton = view.findViewById(R.id.logTravelButton);
        submitButton = view.findViewById(R.id.submitButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        travelLogs = new ArrayList<>();

        // Set up button listeners
        calculateVacationTimeButton.setOnClickListener(v -> {
            // Show the vacation time input form
            if (vacationTimeCalculatorForm.getVisibility() == View.GONE) {
                vacationTimeCalculatorForm.setVisibility(View.VISIBLE);
            } else {
                vacationTimeCalculatorForm.setVisibility(View.GONE);
            }
        });

        autoCalculateButton.setOnClickListener(v -> {
            // Logic for automatic calculation based on two filled inputs
            // To be implemented by ViewModel
            Toast.makeText(getContext(), "Auto calculation logic to be implemented", Toast.LENGTH_SHORT).show();
        });

        submitButton.setOnClickListener(v -> {
            // Logic for submitting travel log
            // To be implemented by ViewModel
            Toast.makeText(getContext(), "Submit logic to be implemented", Toast.LENGTH_SHORT).show();
        });

        cancelButton.setOnClickListener(v -> {
            // Logic for canceling input
            vacationTimeCalculatorForm.setVisibility(View.GONE);
        });

        return view;
    }
}
