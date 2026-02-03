package com.priyansh.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnShow;
    ToggleButton toggleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = findViewById(R.id.btnShow);
        toggleBtn = findViewById(R.id.toggleBtn);

        btnShow.setOnClickListener(v ->
                showCustomToast("Button Clicked!", R.drawable.button_img)
        );

        toggleBtn.setOnClickListener(v -> {
            if (toggleBtn.isChecked()) {
                showCustomToast("Toggle is ON", R.drawable.toggle_on);
            } else {
                showCustomToast("Toggle is OFF", R.drawable.toggle_off);
            }
        });
    }

    private void showCustomToast(String message, int imageRes) {
        LayoutInflater inflater = getLayoutInflater();
        View toastView = inflater.inflate(R.layout.custom_toast, null);

        ImageView image = toastView.findViewById(R.id.toastImage);
        TextView text = toastView.findViewById(R.id.toastText);

        image.setImageResource(imageRes);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }
}