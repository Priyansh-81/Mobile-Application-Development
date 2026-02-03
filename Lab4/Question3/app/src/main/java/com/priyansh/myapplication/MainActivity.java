package com.priyansh.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleMode;
    ImageView modeImage;
    Button btnChangeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleMode = findViewById(R.id.toggleMode);
        modeImage = findViewById(R.id.modeImage);
        btnChangeMode = findViewById(R.id.btnChangeMode);

        updateUI(toggleMode.isChecked());

        toggleMode.setOnClickListener(v -> {
            updateUI(toggleMode.isChecked());
        });

        btnChangeMode.setOnClickListener(v -> {
            toggleMode.setChecked(!toggleMode.isChecked());
            updateUI(toggleMode.isChecked());
        });
    }

    private void updateUI(boolean isWifi) {
        if (isWifi) {
            modeImage.setImageResource(R.drawable.wifi);
            Toast.makeText(this, "Current Mode: Wi-Fi", Toast.LENGTH_SHORT).show();
        } else {
            modeImage.setImageResource(R.drawable.mobile_data);
            Toast.makeText(this, "Current Mode: Mobile Data", Toast.LENGTH_SHORT).show();
        }
    }
}