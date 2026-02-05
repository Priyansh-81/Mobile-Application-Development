package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerSource, spinnerDestination;
    private DatePicker datePicker;
    private ToggleButton toggleButton;
    private Button buttonSubmit, buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        datePicker = findViewById(R.id.datePicker);
        toggleButton = findViewById(R.id.toggleButton);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonReset = findViewById(R.id.buttonReset);

        String[] locations = {"Delhi", "Mumbai", "Bangalore", "London", "Tokyo", "Paris", "Sydney"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        spinnerSource.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = spinnerSource.getSelectedItem().toString();
                String destination = spinnerDestination.getSelectedItem().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                String tripType = toggleButton.isChecked() ? "Round Trip" : "One Way";

                Intent intent = new Intent(MainActivity.this, showDetails.class);
                intent.putExtra("source", source);
                intent.putExtra("destination", destination);
                intent.putExtra("date", day + "/" + (month + 1) + "/" + year);
                intent.putExtra("tripType", tripType);
                startActivity(intent);
            }
        });
    }

    private void resetFields() {
        spinnerSource.setSelection(0);
        spinnerDestination.setSelection(0);
        toggleButton.setChecked(false);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePicker.updateDate(year, month, day);
    }
}