package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner vehicleTypeSpinner;
    private EditText vehicleNumberEditText, rcNumberEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner);
        vehicleNumberEditText = findViewById(R.id.vehicleNumberEditText);
        rcNumberEditText = findViewById(R.id.rcNumberEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
                String vehicleNumber = vehicleNumberEditText.getText().toString();
                String rcNumber = rcNumberEditText.getText().toString();

                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                intent.putExtra("vehicleType", vehicleType);
                intent.putExtra("vehicleNumber", vehicleNumber);
                intent.putExtra("rcNumber", rcNumber);
                startActivity(intent);
            }
        });
    }
}