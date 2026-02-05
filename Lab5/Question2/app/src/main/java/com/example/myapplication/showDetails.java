package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class showDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        TextView textViewSourceValue = findViewById(R.id.textViewSourceValue);
        TextView textViewDestinationValue = findViewById(R.id.textViewDestinationValue);
        TextView textViewDateValue = findViewById(R.id.textViewDateValue);
        TextView textViewTripTypeValue = findViewById(R.id.textViewTripTypeValue);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textViewSourceValue.setText(extras.getString("source"));
            textViewDestinationValue.setText(extras.getString("destination"));
            textViewDateValue.setText(extras.getString("date"));
            textViewTripTypeValue.setText(extras.getString("tripType"));
        }
    }
}