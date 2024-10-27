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
import com.model.User;
import com.viewmodel.DestinationViewModel;
import com.model.Destination;
import com.viewmodel.CurrentUserInfo;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DestinationFragment extends Fragment {

    private LinearLayout vacationTimeCalculatorForm, travelLogsContainer;
    private EditText startDateInput, endDateInput, durationInput;
    private TextView calculatedField, travelLocationLabel, startDateLabel, endDateLabel;
    private Button calculateVacationTimeButton, logTravelButton;
    private EditText travelLocationInput, estimatedStartDateInput, estimatedEndDateInput;
    private List<String> travelLogs;
    private DestinationViewModel destinationViewModel;
    private View resultScreen;
    private Button resetButton;
    private TextView resultTextView;
    private Button calculateVacationTime;

    CurrentUserInfo currentUser = CurrentUserInfo.getInstance();

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
        resetButton = view.findViewById(R.id.resetButton);
        resultTextView = view.findViewById(R.id.resultTextView);
        calculateVacationTime = view.findViewById(R.id.calculateVacationTime);

        travelLogs = new ArrayList<>();

        calculateVacationTime.setOnClickListener(v -> calculateVacationDays());

        // Load existing travel logs
        loadTravelLogs();

        resultScreen = view.findViewById(R.id.resultScreen);
        resultScreen.setVisibility(View.GONE);
        resetButton.setOnClickListener(v -> resetVacationCalculator());

        calculateVacationTimeButton.setOnClickListener(v -> toggleVacationCalculator());
        logTravelButton.setOnClickListener(v -> showLogTravelForm());

        return view;
    }

    private void loadTravelLogs() {
        // Clear the existing logs
        travelLogs.clear();

        // Retrieve the user's destinations from CurrentUserInfo and populate travel logs
        currentUser.getDestinations(destinationList -> {
                    for (Destination destination : destinationList) {
                        String logEntry = "Destination: " + destination.getLocationName() + "                             " +
                                destination.getDurationDays() + " days planned";
                        travelLogs.add(logEntry);
                    }
                    // Update the display with the loaded travel logs
                    updateTravelLogsDisplay();
                }, fail ->
                        Toast.makeText(getContext(), fail, Toast.LENGTH_SHORT).show()
        );
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

    private void calculateVacationDays() {
        String startDate = startDateInput.getText().toString();
        String endDate = endDateInput.getText().toString();
        String duration = durationInput.getText().toString();

        boolean startDateFilled = !startDate.isEmpty();
        boolean endDateFilled = !endDate.isEmpty();
        boolean durationFilled = !duration.isEmpty();

        User user = CurrentUserInfo.getInstance().getUser();
        try {
            if (!startDateFilled && durationFilled && endDateFilled) {
                Date calculated = Destination.calculateMissingStartDate(Integer.parseInt(duration), Destination.parseDate(endDate));
                user.setStartDate(calculated);
                user.setEndDate(Destination.parseDate(endDate));
                user.setAllottedVacationDays(Integer.parseInt(duration));
                showResultDialog(calculated.toString());
            } else if (startDateFilled && durationFilled && !endDateFilled) {
                Date calculated = Destination.calculateMissingEndDate(Integer.parseInt(duration), Destination.parseDate(startDate));
                user.setStartDate(Destination.parseDate(startDate));
                user.setEndDate(calculated);
                user.setAllottedVacationDays(Integer.parseInt(duration));
                showResultDialog(calculated.toString());
            } else if (startDateFilled && endDateFilled && !durationFilled) {
                int calculated = Destination.calculateMissingDays(Destination.parseDate(startDate), Destination.parseDate(endDate));
                user.setStartDate(Destination.parseDate(startDate));
                user.setEndDate(Destination.parseDate(endDate));
                user.setAllottedVacationDays(calculated);
                showResultDialog(Integer.toString(calculated));
            } else {
                Toast.makeText(getContext(), "Please fill in two out of three fields.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Invalid date format.", Toast.LENGTH_SHORT).show();
        }
        CurrentUserInfo.getInstance().setUser(user);
    }

    private void showResultDialog(String text) {
        // Create a LinearLayout for the dialog
        LinearLayout dialogLayout = new LinearLayout(getContext());
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setPadding(20, 20, 20, 20); // Add some padding

        // Create TextView for the result
        TextView resultTextView = new TextView(getContext());
        resultTextView.setText("Result: " + text);
        resultTextView.setTextSize(18); // Set the text size
        dialogLayout.addView(resultTextView); // Add result TextView to the layout


        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Vacation Calculation Result")
                .setView(dialogLayout)
                .setNegativeButton("Reset", null) // Set the negative button with a placeholder
                .create();

        // Set the OnClickListener for the negative button which is the Reset Button
        dialog.setOnShowListener(dialogInterface -> {
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(v -> {
                resetVacationCalculator();
                dialog.dismiss(); // Dismiss the dialog
            });
        });

        dialog.show(); // Show the dialog
    }

    private void resetVacationCalculator() {
        startDateInput.setText("");
        endDateInput.setText("");
        durationInput.setText("");
        resultTextView.setText("");
        vacationTimeCalculatorForm.setVisibility(View.VISIBLE);
        resultScreen.setVisibility(View.GONE);
    }

    private void showLogTravelForm() {
        LinearLayout travelLogLayout = new LinearLayout(getContext());
        travelLogLayout.setOrientation(LinearLayout.VERTICAL);

        travelLocationLabel = new TextView(getContext());
        travelLocationLabel.setText("Travel Location");
        travelLocationInput = new EditText(getContext());
        startDateLabel = new TextView(getContext());
        startDateLabel.setText("Estimated Start Date (MM-DD-YYYY)");
        estimatedStartDateInput = new EditText(getContext());
        endDateLabel = new TextView(getContext());
        endDateLabel.setText("Estimated End Date (MM-DD-YYYY)");
        estimatedEndDateInput = new EditText(getContext());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String todayDate = dateFormat.format(calendar.getTime());

        travelLocationInput.setText("Atlanta");
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
        logTravelDialog = new AlertDialog.Builder(getContext())
                .setTitle("Log Travel")
                .setView(travelLogLayout)
                .setNegativeButton("Cancel", null)
                .create(); // Create the dialog

        // Set the click listener to submit the travel log
        logTravelSubmitButton.setOnClickListener(v -> submitTravelLog()); // Pass the dialog reference

        travelLogLayout.addView(logTravelSubmitButton);
        logTravelDialog.show(); // Show the dialog
    }

    private void submitTravelLog() {
        // Check for empty inputs
        if (TextUtils.isEmpty(travelLocationInput.getText().toString()) ||
                TextUtils.isEmpty(estimatedStartDateInput.getText().toString()) ||
                TextUtils.isEmpty(estimatedEndDateInput.getText().toString())) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String location = travelLocationInput.getText().toString();
        String startDate = estimatedStartDateInput.getText().toString();
        String endDate = estimatedEndDateInput.getText().toString();

        // Use ViewModel to log travel
        if (destinationViewModel.logTravel(endDate, startDate, location)) {
            // Log travel was successful
            int durationDays = destinationViewModel.getDurationFromStrings(startDate, endDate);
            String duration = durationDays >= 0 ? durationDays + " days planned" : "Invalid duration";

            String logEntry = "Destination: " + location + "                              " + duration;
            travelLogs.add(logEntry);
            updateTravelLogsDisplay();

            logTravelDialog.dismiss(); // Dismiss the dialog after submitting the log
        } else {
            Toast.makeText(getContext(), "Invalid input. Please check your dates and destination.", Toast.LENGTH_SHORT).show();
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
        } else if (travelLogs.size() < 5) {
            for (String log : travelLogs) {
                TextView logTextView = new TextView(getContext());
                logTextView.setText(log);
                logTextView.setPadding(8, 8, 8, 8);  // Add some padding
                travelLogsContainer.addView(logTextView);  // Add new logs
            }
        } else {
            for (int i = travelLogs.size() - 5; i < travelLogs.size(); i++) {
                TextView logTextView = new TextView(getContext());
                logTextView.setText(travelLogs.get(i));
                logTextView.setPadding(8, 8, 8, 8);
                travelLogsContainer.addView(logTextView);
            }
        }
    }

    //Travel log
    private int calculateDuration(String startDate, String endDate) {
        return destinationViewModel.getDurationFromStrings(startDate, endDate);  // ViewModel needs to replace with actual logic
    }
}
