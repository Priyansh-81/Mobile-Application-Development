package com.priyansh.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView detailName = findViewById(R.id.detailName);
        TextView detailYear = findViewById(R.id.detailYear);
        TextView detailScore = findViewById(R.id.detailScore);
        TextView detailReview = findViewById(R.id.detailReview);

        String name = getIntent().getStringExtra("name");
        int year = getIntent().getIntExtra("year", 0);
        int score = getIntent().getIntExtra("score", 0);
        String review = getIntent().getStringExtra("review");

        detailName.setText(name);
        detailYear.setText("Year: " + year);
        detailScore.setText("Score: " + score);
        detailReview.setText("Review: " + review);
    }
}
