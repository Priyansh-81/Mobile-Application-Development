package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class ShowDetails extends AppCompatActivity {

    private TextView vehicleTypeTextView, vehicleNumberTextView, rcNumberTextView;
    private Button confirmButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        vehicleTypeTextView = findViewById(R.id.vehicleTypeTextView);
        vehicleNumberTextView = findViewById(R.id.vehicleNumberTextView);
        rcNumberTextView = findViewById(R.id.rcNumberTextView);
        confirmButton = findViewById(R.id.confirmButton);
        editButton = findViewById(R.id.editButton);

        Intent intent = getIntent();
        String vehicleType = intent.getStringExtra("vehicleType");
        String vehicleNumber = intent.getStringExtra("vehicleNumber");
        String rcNumber = intent.getStringExtra("rcNumber");

        vehicleTypeTextView.setText("Vehicle Type: " + vehicleType);
        vehicleNumberTextView.setText("Vehicle Number: " + vehicleNumber);
        rcNumberTextView.setText("RC Number: " + rcNumber);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialNumber = UUID.randomUUID().toString();
                Toast.makeText(ShowDetails.this, "Parking Confirmed! Serial Number: " + serialNumber, Toast.LENGTH_LONG).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}