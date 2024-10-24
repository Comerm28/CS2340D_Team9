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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;

public class LogisticsFragment extends Fragment {

    private PieChart pieChart;
    private Button visualizeTripDaysButton, inviteUsersButton, viewNotesButton;
    private List<String> userNotes;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        // Set up the header text
        TextView headerTextView = view.findViewById(R.id.headerText);
        headerTextView.setText("Logistics");

        // Initialize UI elements
        pieChart = view.findViewById(R.id.pieChart);
        visualizeTripDaysButton = view.findViewById(R.id.visualizeTripDaysButton);
        inviteUsersButton = view.findViewById(R.id.inviteUsersButton);
        viewNotesButton = view.findViewById(R.id.viewNotesButton);

        userNotes = new ArrayList<>();

        // Set up button listeners
        visualizeTripDaysButton.setOnClickListener(v -> visualizeTripDays());
        inviteUsersButton.setOnClickListener(v -> inviteUsers());
        viewNotesButton.setOnClickListener(v -> showNotesDialog());

        return view;
    }

    private void visualizeTripDays() {
        // Example values for allotted vs. planned days
        float allottedDays = 10;
        float plannedDays = 7;
        float unusedDays = allottedDays - plannedDays;

        // Create entries for the pie chart
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(plannedDays, "Planned Days"));
        entries.add(new PieEntry(unusedDays, "Unused Allotted Days"));

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        PieData pieData = new PieData(dataSet);

        // Display the pie chart
        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh chart
        pieChart.setVisibility(View.VISIBLE);
    }

    private void inviteUsers() {
        // Simulating the invite functionality with a Toast
        Toast.makeText(getContext(), "Invite users to contribute to the trip planning.", Toast.LENGTH_SHORT).show();
    }

    private void showNotesDialog() {
        // Create a dialog to show notes and allow the user to add new notes
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("User Notes");

        // Create a layout for the dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_notes, null);
        EditText noteInput = dialogView.findViewById(R.id.noteInput);
        LinearLayout notesContainer = dialogView.findViewById(R.id.notesContainer);

        // Display existing notes
        updateNotesDisplay(notesContainer);

        // Handle adding a new note
        builder.setPositiveButton("Add Note", (dialog, which) -> {
            String newNote = noteInput.getText().toString();
            if (!TextUtils.isEmpty(newNote)) {
                userNotes.add(newNote);
                updateNotesDisplay(notesContainer);
                Toast.makeText(getContext(), "Note added.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please enter a note.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(dialogView);
        builder.setNegativeButton("Close", null);
        builder.show();
    }

    private void updateNotesDisplay(LinearLayout notesContainer) {
        notesContainer.removeAllViews(); // Clear any existing notes

        // Display each note in the container
        for (String note : userNotes) {
            TextView noteView = new TextView(getContext());
            noteView.setText(note);
            notesContainer.addView(noteView);
        }
    }
}
