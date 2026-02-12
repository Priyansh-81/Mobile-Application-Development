package com.priyansh.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentView = findViewById(R.id.content_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.workout_plans) {
            contentView.setText("Workout Plans: Weight Loss, Cardio");
            return true;
        } else if (itemId == R.id.trainers) {
            contentView.setText("Trainers: John Doe (Specialization: Weight Training), Jane Smith (Specialization: Yoga)");
            return true;
        } else if (itemId == R.id.membership) {
            contentView.setText("Membership: Gold - $50/month, Platinum - $80/month");
            return true;
        } else if (itemId == R.id.contact_us) {
            contentView.setText("Contact Us: 123-456-7890");
            return true;
        } else if (itemId == R.id.about_us) {
            contentView.setText("About Us: XYZ Fitness Center, since 2023.");
            return true;
        } else if (itemId == R.id.homepage) {
            contentView.setText("Welcome to XYZ Fitness Center!");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}