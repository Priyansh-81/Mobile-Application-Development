package com.priyansh.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etItemName, etItemCost;
    private Button btnAddItem, btnAddToCart;
    private Spinner spinnerItems;
    private TextView tvTotalCost;
    private DatabaseHelper dbHelper;
    private List<String> itemNames;
    private List<Double> itemCosts;
    private double totalCost = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItemName = findViewById(R.id.etItemName);
        etItemCost = findViewById(R.id.etItemCost);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        spinnerItems = findViewById(R.id.spinnerItems);
        tvTotalCost = findViewById(R.id.tvTotalCost);

        dbHelper = new DatabaseHelper(this);
        itemNames = new ArrayList<>();
        itemCosts = new ArrayList<>();

        loadSpinnerData();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etItemName.getText().toString().trim();
                String costStr = etItemCost.getText().toString().trim();

                if (!name.isEmpty() && !costStr.isEmpty()) {
                    double cost = Double.parseDouble(costStr);
                    if (dbHelper.insertItem(name, cost)) {
                        Toast.makeText(MainActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                        etItemName.setText("");
                        etItemCost.setText("");
                        loadSpinnerData();
                    } else {
                        Toast.makeText(MainActivity.this, "Error Adding Item", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex = spinnerItems.getSelectedItemPosition();
                if (selectedIndex != -1 && !itemCosts.isEmpty()) {
                    double cost = itemCosts.get(selectedIndex);
                    totalCost += cost;
                    tvTotalCost.setText("Total Cost: " + totalCost);
                } else {
                    Toast.makeText(MainActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadSpinnerData() {
        itemNames.clear();
        itemCosts.clear();
        Cursor cursor = dbHelper.getAllItems();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COST));
                itemNames.add(name + " ($" + cost + ")");
                itemCosts.add(cost);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);
    }
}