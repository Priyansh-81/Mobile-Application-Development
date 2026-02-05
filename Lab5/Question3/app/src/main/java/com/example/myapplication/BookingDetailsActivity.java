package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        TextView bookingDetailsTextView = findViewById(R.id.booking_details_textview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String movie = extras.getString("movie");
            String theatre = extras.getString("theatre");
            String date = extras.getString("date");
            String time = extras.getString("time");
            String ticketType = extras.getString("ticketType");
            int availableSeats = extras.getInt("availableSeats");

            String details = "Movie: " + movie + "\n"
                    + "Theatre: " + theatre + "\n"
                    + "Date: " + date + "\n"
                    + "Time: " + time + "\n"
                    + "Ticket Type: " + ticketType + "\n"
                    + "Available Seats: " + availableSeats;

            bookingDetailsTextView.setText(details);
        }
    }
}