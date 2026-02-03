package com.priyansh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox cbPizza, cbBurger, cbPasta, cbCoffee;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbPasta = findViewById(R.id.cbPasta);
        cbCoffee = findViewById(R.id.cbCoffee);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            StringBuilder orderDetails = new StringBuilder();
            int totalCost = 0;

            if (cbPizza.isChecked()) {
                orderDetails.append("Pizza - ₹150\n");
                totalCost += 150;
            }
            if (cbBurger.isChecked()) {
                orderDetails.append("Burger - ₹100\n");
                totalCost += 100;
            }
            if (cbPasta.isChecked()) {
                orderDetails.append("Pasta - ₹120\n");
                totalCost += 120;
            }
            if (cbCoffee.isChecked()) {
                orderDetails.append("Coffee - ₹50\n");
                totalCost += 50;
            }

            Intent intent = new Intent(MainActivity.this, activity_order_summary.class);
            intent.putExtra("order", orderDetails.toString());
            intent.putExtra("total", totalCost);
            startActivity(intent);

            disableInputs();
        });
    }

    private void disableInputs() {
        cbPizza.setEnabled(false);
        cbBurger.setEnabled(false);
        cbPasta.setEnabled(false);
        cbCoffee.setEnabled(false);
        btnSubmit.setEnabled(false);
    }
}