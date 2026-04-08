package com.priyansh.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etMovieName, etYear, etRating;
    private Button btnSave;
    private ListView lvMovies;
    private TextView tvDetailName, tvDetailYear, tvDetailRating;
    private DatabaseHelper dbHelper;
    private ArrayList<String> movieNames;
    private ArrayList<Integer> movieIds;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        etMovieName = findViewById(R.id.etMovieName);
        etYear = findViewById(R.id.etYear);
        etRating = findViewById(R.id.etRating);
        btnSave = findViewById(R.id.btnSave);
        lvMovies = findViewById(R.id.lvMovies);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailYear = findViewById(R.id.tvDetailYear);
        tvDetailRating = findViewById(R.id.tvDetailRating);

        movieNames = new ArrayList<>();
        movieIds = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieNames);
        lvMovies.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovie();
            }
        });

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayMovieDetails(movieIds.get(position));
            }
        });

        loadMovies();
    }

    private void saveMovie() {
        String name = etMovieName.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();

        if (name.isEmpty() || yearStr.isEmpty() || ratingStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearStr);
        int rating = Integer.parseInt(ratingStr);

        if (rating < 1 || rating > 5) {
            Toast.makeText(this, "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertMovie(name, year, rating);
        if (isInserted) {
            Toast.makeText(this, "Review Saved", Toast.LENGTH_SHORT).show();
            etMovieName.setText("");
            etYear.setText("");
            etRating.setText("");
            loadMovies();
        } else {
            Toast.makeText(this, "Failed to Save Review", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMovies() {
        movieNames.clear();
        movieIds.clear();
        Cursor cursor = dbHelper.getAllMovies();
        if (cursor.moveToFirst()) {
            do {
                movieIds.add(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID)));
                movieNames.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void displayMovieDetails(int id) {
        Cursor cursor = dbHelper.getMovieById(id);
        if (cursor.moveToFirst()) {
            tvDetailName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NAME)));
            tvDetailYear.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_YEAR))));
            tvDetailRating.setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RATING))));
        }
        cursor.close();
    }
}
