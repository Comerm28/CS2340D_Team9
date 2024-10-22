package com.view;

import android.app.AlertDialog;
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

    private LinearLayout vacationTimeCalculatorForm, travelLogsContainer;
    private EditText startDateInput, endDateInput, durationInput;
    private TextView calculatedField;
    private Button calculateVacationTimeButton, logTravelButton;
    private EditText travelLocationInput, estimatedStartDateInput, estimatedEndDateInput;
    private List<String> travelLogs;

    // Declare logTravelDialog at the class level
    private AlertDialog logTravelDialog;

    private boolean isVacationCalculatorVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        vacationTimeCalculatorForm = view.findViewById(R.id.vacationTimeCalculatorForm);
        travelLogsContainer = view.findViewById(R.id.travelLogsContainer);

        startDateInput = view.findViewById(R.id.startDateInput);
        endDateInput = view.findViewById(R.id.endDateInput);
        durationInput = view.findViewById(R.id.durationInput);
        calculatedField = view.findViewById(R.id.calculatedField);
        calculateVacationTimeButton = view.findViewById(R.id.calculateVacationTimeButton);
        logTravelButton = view.findViewById(R.id.logTravelButton);

        travelLogs = new ArrayList<>();

        calculateVacationTimeButton.setOnClickListener(v -> toggleVacationCalculator());
        logTravelButton.setOnClickListener(v -> showLogTravelForm());

        return view;
    }

    private void toggleVacationCalculator() {
        if (isVacationCalculatorVisible) {
            vacationTimeCalculatorForm.setVisibility(View.GONE);
            isVacationCalculatorVisible = false;
        } else {
            vacationTimeCalculatorForm.setVisibility(View.VISIBLE);
            isVacationCalculatorVisible = true;
        }
    }

    private void showLogTravelForm() {
        LinearLayout travelLogLayout = new LinearLayout(getContext());
        travelLogLayout.setOrientation(LinearLayout.VERTICAL);

        travelLocationInput = new EditText(getContext());
        estimatedStartDateInput = new EditText(getContext());
        estimatedEndDateInput = new EditText(getContext());

        travelLocationInput.setHint("Travel Location");
        estimatedStartDateInput.setHint("Estimated Start Date");
        estimatedEndDateInput.setHint("Estimated End Date");

        travelLogLayout.addView(travelLocationInput);
        travelLogLayout.addView(estimatedStartDateInput);
        travelLogLayout.addView(estimatedEndDateInput);

        Button logTravelSubmitButton = new Button(getContext());
        logTravelSubmitButton.setText("Submit Travel Log");

        // Set the click listener for the submit button
        logTravelSubmitButton.setOnClickListener(v -> submitTravelLog());

        travelLogLayout.addView(logTravelSubmitButton);

        // Create the dialog reference
        logTravelDialog = new AlertDialog.Builder(getContext())
                .setTitle("Log Travel")
                .setView(travelLogLayout)
                .setNegativeButton("Cancel", null)
                .create(); // Create the dialog

        logTravelDialog.show(); // Show the dialog
    }

    private void submitTravelLog() {
        if (TextUtils.isEmpty(travelLocationInput.getText().toString()) ||
                TextUtils.isEmpty(estimatedStartDateInput.getText().toString()) ||
                TextUtils.isEmpty(estimatedEndDateInput.getText().toString())) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String location = travelLocationInput.getText().toString();
        String duration = calculateDuration(estimatedStartDateInput.getText().toString(), estimatedEndDateInput.getText().toString()) + " days";

        String logEntry = "Destination: " + location + " - " + duration;
        travelLogs.add(logEntry);
        updateTravelLogsDisplay();

        logTravelDialog.dismiss(); // Dismiss the dialog after submitting the log
    }

    private void updateTravelLogsDisplay() {
        travelLogsContainer.removeAllViews();  // Clear existing views

        // Check if there are any logs; if not, show the default placeholder
        if (travelLogs.isEmpty()) {
            TextView defaultLogView = new TextView(getContext());
            defaultLogView.setText("Destination: 0 days planned.");
            travelLogsContainer.addView(defaultLogView);
        } else {
            for (String log : travelLogs) {
                TextView logTextView = new TextView(getContext());
                logTextView.setText(log);
                logTextView.setPadding(8, 8, 8, 8);  // Add some padding
                travelLogsContainer.addView(logTextView);  // Add new logs
            }
        }
    }

    private int calculateDuration(String startDate, String endDate) {
        // Placeholder logic for duration calculation
        return 5;  // Replace with actual logic
    }
}
