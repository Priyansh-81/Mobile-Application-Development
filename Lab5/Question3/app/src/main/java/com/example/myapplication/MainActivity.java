package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Spinner movieSpinner, theatreSpinner;
    private Button datePickerButton, timePickerButton, bookNowButton, resetButton;
    private TextView selectedDateTextView, selectedTimeTextView;
    private ToggleButton ticketTypeToggle;

    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieSpinner = findViewById(R.id.movie_spinner);
        theatreSpinner = findViewById(R.id.theatre_spinner);
        datePickerButton = findViewById(R.id.date_picker_button);
        timePickerButton = findViewById(R.id.time_picker_button);
        bookNowButton = findViewById(R.id.book_now_button);
        resetButton = findViewById(R.id.reset_button);
        selectedDateTextView = findViewById(R.id.selected_date_textview);
        selectedTimeTextView = findViewById(R.id.selected_time_textview);
        ticketTypeToggle = findViewById(R.id.ticket_type_toggle);

        // Populate spinners
        ArrayAdapter<CharSequence> movieAdapter = ArrayAdapter.createFromResource(this,
                R.array.movies_array, android.R.layout.simple_spinner_item);
        movieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieSpinner.setAdapter(movieAdapter);

        ArrayAdapter<CharSequence> theatreAdapter = ArrayAdapter.createFromResource(this,
                R.array.theatres_array, android.R.layout.simple_spinner_item);
        theatreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theatreSpinner.setAdapter(theatreAdapter);

        // Set up current date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        selectedDateTextView.setText(day + "-" + (month + 1) + "-" + year);

        datePickerButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        selectedDateTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        MainActivity.this.year = year;
                        MainActivity.this.month = monthOfYear;
                        MainActivity.this.day = dayOfMonth;
                    }, year, month, day);
            datePickerDialog.show();
        });

        timePickerButton.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                    (view, hourOfDay, minute) -> {
                        selectedTimeTextView.setText(hourOfDay + ":" + minute);
                        MainActivity.this.hour = hourOfDay;
                        MainActivity.this.minute = minute;
                        updateSubmitButtonState();
                    }, hour, minute, false);
            timePickerDialog.show();
        });

        ticketTypeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSubmitButtonState();
        });

        bookNowButton.setOnClickListener(v -> {
            if (validateInputs()) {
                Intent intent = new Intent(MainActivity.this, BookingDetailsActivity.class);
                intent.putExtra("movie", movieSpinner.getSelectedItem().toString());
                intent.putExtra("theatre", theatreSpinner.getSelectedItem().toString());
                intent.putExtra("date", selectedDateTextView.getText().toString());
                intent.putExtra("time", selectedTimeTextView.getText().toString());
                intent.putExtra("ticketType", ticketTypeToggle.isChecked() ? "Premium" : "Standard");
                intent.putExtra("availableSeats", (int) (Math.random() * 100)); // Dummy data
                startActivity(intent);
            }
        });

        resetButton.setOnClickListener(v -> {
            movieSpinner.setSelection(0);
            theatreSpinner.setSelection(0);
            final Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
            selectedDateTextView.setText(day + "-" + (month + 1) + "-" + year);
            selectedTimeTextView.setText("");
            ticketTypeToggle.setChecked(false);
            updateSubmitButtonState();
        });

        updateSubmitButtonState();
    }

    private void updateSubmitButtonState() {
        if (ticketTypeToggle.isChecked()) {
            bookNowButton.setEnabled(hour >= 12);
        } else {
            bookNowButton.setEnabled(true);
        }
    }

    private boolean validateInputs() {
        if (movieSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a movie", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (theatreSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a theatre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedTimeTextView.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}