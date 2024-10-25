package com.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogisticsFragment extends Fragment {

    private PieChart pieChart;
    private Button visualizeTripDaysButton, inviteUsersButton, viewNotesButton, listNotesButton;
    private List<String> userNotes;
    private SharedPreferences sharedPreferences; //to save Notes into the list of Notes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        // Set up the header text
        TextView headerTextView = view.findViewById(R.id.headerText);
        headerTextView.setText("Logistics");

        // Initialize UI elements
        pieChart = view.findViewById(R.id.pieChart);
        visualizeTripDaysButton = view.findViewById(R.id.visualizeTripDaysButton);
        inviteUsersButton = view.findViewById(R.id.inviteUsersButton);
        viewNotesButton = view.findViewById(R.id.viewNotesButton);
        listNotesButton = view.findViewById(R.id.listNotesButton);

        // Initialize the SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("NotesPrefs", Context.MODE_PRIVATE);
        userNotes = loadNotes(); // Load existing notes

        // Set up button listeners
        visualizeTripDaysButton.setOnClickListener(v -> visualizeTripDays());
        inviteUsersButton.setOnClickListener(v -> inviteUsers());
        viewNotesButton.setOnClickListener(v -> showNotesDialog());
        listNotesButton.setOnClickListener(v -> showNotesListDialog());

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

        // Set colors for the PieDataSet
        List<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.plannedDaysColor)); // color for planned days
        colors.add(getResources().getColor(R.color.unusedDaysColor)); // color for unused days
        dataSet.setColors(colors);

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

        // Handle adding a new note
        builder.setPositiveButton("Add Note", (dialog, which) -> {
            String newNote = noteInput.getText().toString();
            if (!TextUtils.isEmpty(newNote)) {
                userNotes.add(newNote);
                saveNotes(); // Save the updated list of notes
                Toast.makeText(getContext(), "Note added.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please enter a note.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(dialogView);
        builder.setNegativeButton("Close", null);
        builder.show();
    }

    private void showNotesListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("List of Notes");

        // Create a LinearLayout to display existing notes with delete buttons
        LinearLayout notesLayout = new LinearLayout(getContext());
        notesLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < userNotes.size(); i++) {
            String note = userNotes.get(i);
            LinearLayout noteLayout = new LinearLayout(getContext());
            noteLayout.setOrientation(LinearLayout.HORIZONTAL);
            noteLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView noteView = new TextView(getContext());
            noteView.setText(note);
            noteView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1)); // Changed: Set weight to 1
            noteView.setPadding(16, 8, 0, 8);
            noteLayout.addView(noteView);

            // Create a delete button for each note
            Button deleteButton = new Button(getContext());
            deleteButton.setText("Delete");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(16, 0, 0, 0); // Add some margin for spacing
            deleteButton.setLayoutParams(buttonParams); // Set layout params for delete button

            int index = i; // Capture the current index for the delete button listener
            deleteButton.setOnClickListener(v -> {
                userNotes.remove(index);
                saveNotes(); // Save the updated list of notes
                Toast.makeText(getContext(), "Note deleted.", Toast.LENGTH_SHORT).show();
                showUpdatedNotesList(notesLayout); // Update the current notes layout
            });

            noteLayout.addView(deleteButton);
            notesLayout.addView(noteLayout);
        }

        builder.setView(notesLayout);
        builder.setNegativeButton("Close", null);
        builder.show();
    }

    private void showUpdatedNotesList(LinearLayout notesLayout) {
        notesLayout.removeAllViews(); // Clear current views

        // Re-add notes and buttons
        for (int i = 0; i < userNotes.size(); i++) {
            String note = userNotes.get(i);
            LinearLayout noteLayout = new LinearLayout(getContext());
            noteLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView noteView = new TextView(getContext());
            noteView.setText(note);
            noteView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1)); // Changed: Set weight to 1
            noteView.setPadding(16, 8, 0, 8);
            noteLayout.addView(noteView);

            // Create a delete button for each note
            Button deleteButton = new Button(getContext());
            deleteButton.setText("Delete");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(16, 0, 0, 0);
            deleteButton.setLayoutParams(buttonParams); // Set layout params for delete button

            int index = i; // The current index for the delete button listener
            deleteButton.setOnClickListener(v -> {
                userNotes.remove(index);
                saveNotes(); // Save the updated list of notes
                Toast.makeText(getContext(), "Note deleted.", Toast.LENGTH_SHORT).show();
                showUpdatedNotesList(notesLayout); // Update the current notes layout
            });

            noteLayout.addView(deleteButton);
            notesLayout.addView(noteLayout);
        }
    }

    private void saveNotes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder notesBuilder = new StringBuilder();
        for (String note : userNotes) {
            notesBuilder.append(note).append(",");
        }
        editor.putString("userNotes", notesBuilder.toString());
        editor.apply();
    }

    private List<String> loadNotes() {
        String savedNotes = sharedPreferences.getString("userNotes", "");
        List<String> notesList = new ArrayList<>(Arrays.asList(savedNotes.split(",")));
        notesList.removeIf(String::isEmpty); // Remove empty strings if any
        return notesList;
    }

//    private void updateNotesDisplay(LinearLayout notesContainer) {
//        notesContainer.removeAllViews(); // Clear any existing notes
//
//        // Display each note in the container
//        for (String note : userNotes) {
//            TextView noteView = new TextView(getContext());
//            noteView.setText(note);
//            notesContainer.addView(noteView);
//        }
//    }
}
