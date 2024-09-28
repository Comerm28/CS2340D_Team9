package com.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.sprintproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.view.Logistics;

public class MainAppScreensActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.screens, new Logistics())
                    .commit();
        }

        Button button1 = findViewById(R.id.logistics);
        Button button2 = findViewById(R.id.destination);
        Button button3 = findViewById(R.id.dining);
        Button button4 = findViewById(R.id.accomodations);
        Button button5 = findViewById(R.id.transportation);
        Button button6 = findViewById(R.id.travelCommunity);

        button1.setOnClickListener(v -> switchFragment(new Logistics()));
    }

    private void switchFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.screens, f)
                .commit();
    }
}