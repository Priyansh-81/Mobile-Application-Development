package com.priyansh.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    ListView listView;
    DBHelper helper;
    ArrayList<String> movieNames;
    ArrayList<Integer> movieIds;
    ArrayList<Integer> movieYears;
    ArrayList<Integer> movieScores;
    ArrayList<String> movieReviews;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        listView = findViewById(R.id.movieListView);
        helper = new DBHelper(this);

        loadMovies();

        registerForContextMenu(listView);
    }

    private void loadMovies() {
        movieNames = new ArrayList<>();
        movieIds = new ArrayList<>();
        movieYears = new ArrayList<>();
        movieScores = new ArrayList<>();
        movieReviews = new ArrayList<>();

        Cursor cursor = helper.getAllMovies();
        if (cursor.moveToFirst()) {
            do {
                movieIds.add(cursor.getInt(0));
                movieNames.add(cursor.getString(1));
                movieScores.add(cursor.getInt(2));
                movieReviews.add(cursor.getString(3));
                movieYears.add(cursor.getInt(4));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieNames);
        listView.setAdapter(adapter);
        
        if (movieNames.isEmpty()) {
            Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.movie_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        int id = item.getItemId();
        if (id == R.id.menu_details) {
            Toast.makeText(this, "Opening details for " + movieNames.get(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("name", movieNames.get(position));
            intent.putExtra("year", movieYears.get(position));
            intent.putExtra("score", movieScores.get(position));
            intent.putExtra("review", movieReviews.get(position));
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_update) {
            Toast.makeText(this, "Loading update page for " + movieNames.get(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("movie_name", movieNames.get(position));
            intent.putExtra("year", movieYears.get(position));
            intent.putExtra("score", movieScores.get(position));
            intent.putExtra("review", movieReviews.get(position));
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_delete) {
            helper.deleteMovie(movieIds.get(position));
            Toast.makeText(this, movieNames.get(position) + " Deleted", Toast.LENGTH_SHORT).show();
            loadMovies();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
