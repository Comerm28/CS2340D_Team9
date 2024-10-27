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
import com.viewmodel.DestinationViewModel;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DestinationFragment extends Fragment {

    private LinearLayout vacationTimeCalculatorForm, travelLogsContainer;
    private EditText startDateInput, endDateInput, durationInput;
    private TextView calculatedField, travelLocationLabel, startDateLabel, endDateLabel;
    private Button calculateVacationTimeButton, logTravelButton;
    private EditText travelLocationInput, estimatedStartDateInput, estimatedEndDateInput;
    private List<String> travelLogs;
    private DestinationViewModel destinationViewModel;


    private AlertDialog logTravelDialog;

    private boolean isVacationCalculatorVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        // Initialization of views
        vacationTimeCalculatorForm = view.findViewById(R.id.vacationTimeCalculatorForm);
        travelLogsContainer = view.findViewById(R.id.travelLogsContainer);
        startDateInput = view.findViewById(R.id.startDateInput);
        endDateInput = view.findViewById(R.id.endDateInput);
        durationInput = view.findViewById(R.id.durationInput);
        calculatedField = view.findViewById(R.id.calculatedField);
        calculateVacationTimeButton = view.findViewById(R.id.calculateVacationTimeButton);
        logTravelButton = view.findViewById(R.id.logTravelButton);
        destinationViewModel = new DestinationViewModel();

        travelLogs = new ArrayList<>();

        // Call to update the display with the placeholder
        updateTravelLogsDisplay();

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

        travelLocationLabel = new TextView(getContext());
        travelLocationLabel.setText("Travel Location");
        travelLocationInput = new EditText(getContext());
        startDateLabel = new TextView(getContext());
        startDateLabel.setText("Estimated Start Date");
        estimatedStartDateInput = new EditText(getContext());
        endDateLabel = new TextView(getContext());
        endDateLabel.setText("Estimated End Date");
        estimatedEndDateInput = new EditText(getContext());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String todayDate = dateFormat.format(calendar.getTime());

        travelLocationInput.setText("Default location");
        estimatedStartDateInput.setText(todayDate);
        estimatedEndDateInput.setText(todayDate);

        travelLogLayout.addView(travelLocationLabel);
        travelLogLayout.addView(travelLocationInput);
        travelLogLayout.addView(startDateLabel);
        travelLogLayout.addView(estimatedStartDateInput);
        travelLogLayout.addView(endDateLabel);
        travelLogLayout.addView(estimatedEndDateInput);

        Button logTravelSubmitButton = new Button(getContext());
        logTravelSubmitButton.setText("Submit Travel Log");

        // Create the dialog reference
        AlertDialog logTravelDialog = new AlertDialog.Builder(getContext())
                .setTitle("Log Travel")
                .setView(travelLogLayout)
                .setNegativeButton("Cancel", null)
                .create(); // Create the dialog

        // Set the click listener to submit the travel log
        logTravelSubmitButton.setOnClickListener(v -> submitTravelLog(logTravelDialog)); // Pass the dialog reference

        travelLogLayout.addView(logTravelSubmitButton);
        logTravelDialog.show(); // Show the dialog
    }


    private void submitTravelLog(AlertDialog logTravelDialog){
        if (TextUtils.isEmpty(travelLocationInput.getText().toString()) ||
                TextUtils.isEmpty(estimatedStartDateInput.getText().toString()) ||
                TextUtils.isEmpty(estimatedEndDateInput.getText().toString())) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(destinationViewModel.logTravel(estimatedEndDateInput.getText().toString(), estimatedStartDateInput.getText().toString(), travelLocationInput.getText().toString()))
        {
            String location = travelLocationInput.getText().toString();
            String duration = calculateDuration(estimatedStartDateInput.getText().toString(), estimatedEndDateInput.getText().toString()) + " days planned";

            String logEntry = "Destination: " + location + "                              " + duration;
            travelLogs.add(logEntry);
            updateTravelLogsDisplay();

            logTravelDialog.dismiss(); // Dismiss the dialog after submitting the log
        }
        else
        {
            //todo handle ui when destinationviewmodel does not mess with user input
            logTravelDialog.dismiss();
        }
    }

    private void updateTravelLogsDisplay() {
        travelLogsContainer.removeAllViews();  // Clear existing views

        // Check if there are any logs; if not, show the default placeholder
        if (travelLogs.isEmpty()) {
            TextView defaultLogView = new TextView(getContext());
            defaultLogView.setText("Destination:                                                0 days planned");
            defaultLogView.setPadding(8, 8, 8, 8);  // Add some padding
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
        return destinationViewModel.getDurationFromStrings(startDate, endDate);  // ViewModel needs to replace with actual logic
    }
}