package com.priyansh.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name, review, year, score;
    Button submit, show;
    ToggleButton mode;

    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        year = findViewById(R.id.year);
        score = findViewById(R.id.score);
        review = findViewById(R.id.review);
        submit = findViewById(R.id.submit);
        show = findViewById(R.id.show);
        mode = findViewById(R.id.toggleButton);
        helper = new DBHelper(this);

        mode.setTextOn("Insert");
        mode.setTextOff("Update");
        
        // Initial state
        updateSubmitButton();

        mode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateSubmitButton();
        });

        submit.setOnClickListener(v -> {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("movie_name", name.getText().toString());
            contentValues.put("year", Integer.parseInt(year.getText().toString()));
            contentValues.put("score", Integer.parseInt(score.getText().toString()));
            contentValues.put("review", review.getText().toString());

            if (mode.isChecked()) {
                long res = db.insert("movies", null, contentValues);
                if (res != -1) Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            } else {
                int res = db.update("movies", contentValues, "movie_name=?", new String[]{name.getText().toString()});
                if (res > 0) Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });

        show.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
            startActivity(intent);
        });

        // Check if we came here from Update option
        Intent intent = getIntent();
        if (intent.hasExtra("movie_name")) {
            name.setText(intent.getStringExtra("movie_name"));
            year.setText(String.valueOf(intent.getIntExtra("year", 0)));
            score.setText(String.valueOf(intent.getIntExtra("score", 0)));
            review.setText(intent.getStringExtra("review"));
            mode.setChecked(false); // Set to Update mode
            updateSubmitButton();
        }
    }

    private void updateSubmitButton() {
        if (mode.isChecked()) {
            submit.setText("Insert");
        } else {
            submit.setText("Update");
        }
    }
}
