package com.priyansh.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_order_summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        TextView tvOrderDetails = findViewById(R.id.tvOrderDetails);
        TextView tvTotalCost = findViewById(R.id.tvTotalCost);

        String order = getIntent().getStringExtra("order");
        int total = getIntent().getIntExtra("total", 0);

        tvOrderDetails.setText(order.isEmpty() ? "No items selected" : order);
        tvTotalCost.setText("Total Cost: ₹" + total);
    }
}