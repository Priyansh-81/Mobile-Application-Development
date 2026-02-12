package com.priyansh.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView menuIcon;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuIcon = findViewById(R.id.menu_icon);
        imageView = findViewById(R.id.image_view);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.my_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.image1) {
                            imageView.setImageResource(R.drawable.image1);
                            imageView.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Image - 1", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.image2) {
                            imageView.setImageResource(R.drawable.image2);
                            imageView.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Image - 2", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}