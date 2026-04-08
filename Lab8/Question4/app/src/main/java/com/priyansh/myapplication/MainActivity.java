package com.priyansh.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etEmail;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        dbHelper = new DatabaseHelper(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        dbHelper.saveOrUpdateUser(name, email);
    }

    private void loadData() {
        Cursor cursor = dbHelper.getUserData();
        if (cursor != null && cursor.moveToFirst()) {
            // Index 0 is ID, Index 1 is Name, Index 2 is Email
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            
            etName.setText(name);
            etEmail.setText(email);
            cursor.close();
        }
    }
}
