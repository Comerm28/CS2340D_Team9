package com.view;

import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.viewmodel.LogisticsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogisticsFragment extends Fragment {

    private PieChart pieChart;
    private Button visualizeTripDaysButton, inviteUsersButton, viewNotesButton, listNotesButton;
    private EditText inviteUserEditText;
    private List<String> userNotes;
    private SharedPreferences sharedPreferences;
    private LogisticsViewModel logisticsViewModel;
    private AlertDialog notesDialog; // Reference for the notes dialog

    private boolean isPieChartVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        logisticsViewModel = new LogisticsViewModel();

        // Set up the header text
        TextView headerTextView = view.findViewById(R.id.headerText);
        headerTextView.setText("Logistics");

        // Initialize UI elements
        pieChart = view.findViewById(R.id.pieChart);
        visualizeTripDaysButton = view.findViewById(R.id.visualizeTripDaysButton);
        inviteUsersButton = view.findViewById(R.id.inviteUsersButton);
        viewNotesButton = view.findViewById(R.id.viewNotesButton);
        listNotesButton = view.findViewById(R.id.listNotesButton);
        inviteUserEditText = view.findViewById(R.id.inviteUserEditText);

        sharedPreferences = requireActivity().getSharedPreferences("NotesPrefs", Context.MODE_PRIVATE);
        userNotes = loadNotes();

        visualizeTripDaysButton.setOnClickListener(v -> toggleVisualizeTripDays());
        inviteUsersButton.setOnClickListener(v -> inviteUser());
        viewNotesButton.setOnClickListener(v -> showAddNoteDialog());
        listNotesButton.setOnClickListener(v -> showNotesListDialog());

        return view;
    }

    private void toggleVisualizeTripDays() {
        isPieChartVisible = !isPieChartVisible;
        if (isPieChartVisible) {
            visualizeTripDays();
        } else {
            pieChart.setVisibility(View.GONE);
        }
    }

    private void visualizeTripDays() {
        logisticsViewModel.getAllottedDays(allottedDays -> {
                    logisticsViewModel.getPlannedDays(plannedDays -> {

                                List<PieEntry> entries = new ArrayList<>();
                                entries.add(new PieEntry(plannedDays, "Planned Days"));
                                entries.add(new PieEntry(allottedDays, "Allotted Days"));

                                PieDataSet dataSet = new PieDataSet(entries, "");
                                dataSet.setColors(new int[]{
                                        ContextCompat.getColor(getContext(), R.color.blue),
                                        ContextCompat.getColor(getContext(), R.color.green)
                                });
                                PieData pieData = new PieData(dataSet);

                                pieChart.setData(pieData);
                                pieChart.invalidate();
                                pieChart.setVisibility(View.VISIBLE);

                            }, fail ->
                                    Toast.makeText(getContext(), fail, Toast.LENGTH_SHORT).show()
                    );
                }, fail ->
                        Toast.makeText(getContext(), fail, Toast.LENGTH_SHORT).show()
        );

    }

    private void inviteUser() {
        String username = inviteUserEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(username)) {
            logisticsViewModel.inviteUser(username, () -> Toast.makeText(getContext(),
                            "User invited successfully.", Toast.LENGTH_SHORT).show(),
                    fail -> Toast.makeText(getContext(), "User not found or invitation failed.", Toast.LENGTH_SHORT).show()
            );
        } else {
            Toast.makeText(getContext(), "Enter a username to invite.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Note");

        final EditText input = new EditText(getContext());
        input.setHint("Enter your note");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String note = input.getText().toString().trim(); // Trim input to avoid empty notes
            if (!TextUtils.isEmpty(note)) {
                logisticsViewModel.addNoteToCurrentVacation(note, () -> {
                    saveNotes();
                    Toast.makeText(getContext(), "Note added.", Toast.LENGTH_SHORT).show();
                    userNotes.add(note);
                }, fail -> Toast.makeText(getContext(), fail, Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(getContext(), "Note cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);

        try {
            // Show the dialog
            builder.show();
        } catch (Exception e) {
            // Log the error
            Log.e("LogisticsFragment", "Error showing add note dialog", e);
            Toast.makeText(getContext(), "Failed to open the note dialog.", Toast.LENGTH_SHORT).show();
        }
    }


    private void showNotesListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("List of Notes");

        LinearLayout notesLayout = new LinearLayout(getContext());
        notesLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < userNotes.size(); i++) {
            String note = userNotes.get(i);
            LinearLayout noteLayout = createNoteLayout(note, i);
            notesLayout.addView(noteLayout);
        }

        builder.setView(notesLayout);
        builder.setNegativeButton("Close", null);

        // Create and show the dialog
        notesDialog = builder.create();
        notesDialog.show();
    }

    private LinearLayout createNoteLayout(String note, int index) {
        LinearLayout noteLayout = new LinearLayout(getContext());
        noteLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView noteView = new TextView(getContext());
        noteView.setText(note);
        noteView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        noteView.setPadding(16, 8, 0, 8);
        noteLayout.addView(noteView);

        Button deleteButton = new Button(getContext());
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(v -> {
            userNotes.remove(index);
            saveNotes();
            Toast.makeText(getContext(), "Note deleted.", Toast.LENGTH_SHORT).show();
            if (notesDialog != null && notesDialog.isShowing()) {
                notesDialog.dismiss(); // Dismiss the current dialog
            }
            showNotesListDialog(); // Show updated notes list
        });

        noteLayout.addView(deleteButton);
        return noteLayout;
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
        notesList.removeIf(String::isEmpty);
        return notesList;
    }
}
