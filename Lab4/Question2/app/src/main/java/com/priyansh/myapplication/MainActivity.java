package com.priyansh.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lollipop = findViewById(R.id.btnLollipop);
        Button marshmallow = findViewById(R.id.btnMarshmallow);
        Button nougat = findViewById(R.id.btnNougat);
        Button oreo = findViewById(R.id.btnOreo);

        lollipop.setOnClickListener(v ->
                showCustomToast("Android Lollipop", R.drawable.android_lollipop));

        marshmallow.setOnClickListener(v ->
                showCustomToast("Android Marshmallow", R.drawable.android_marshmallow));

        nougat.setOnClickListener(v ->
                showCustomToast("Android Nougat", R.drawable.android_nougat));

        oreo.setOnClickListener(v ->
                showCustomToast("Android Oreo", R.drawable.android_oreo));
    }

    private void showCustomToast(String text, int imageRes) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, null);

        ImageView icon = view.findViewById(R.id.toastIcon);
        TextView message = view.findViewById(R.id.toastText);

        icon.setImageResource(imageRes);
        message.setText(text);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}