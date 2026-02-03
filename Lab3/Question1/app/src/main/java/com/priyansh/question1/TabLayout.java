package com.priyansh.question1;

import android.os.Bundle;
import android.widget.TabHost;
import androidx.appcompat.app.AppCompatActivity;

public class TabLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec;

        spec = tabHost.newTabSpec("Artists");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Artists");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Albums");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Albums");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Songs");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Songs");
        tabHost.addTab(spec);
    }
}