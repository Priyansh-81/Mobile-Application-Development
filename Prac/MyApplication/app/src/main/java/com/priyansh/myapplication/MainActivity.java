package com.priyansh.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Spinner sp;
    Button btn,btn1;

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

        String[] items={
                "Buns",
                "Patty",
                "Ketchup",
                "Onion",
                "Tomato"
        };

        //lv=findViewById(R.id.listview);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);

        //lv.setAdapter(adapter);
        sp=findViewById(R.id.sp);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items);
        sp.setAdapter(adapter1);

        String item=sp.getSelectedItem().toString();
        Toast.makeText(this,item, Toast.LENGTH_SHORT).show();

        btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> {

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dp = new DatePickerDialog(
                    this,
                    (view, y, m, d) -> {
                        String date = d + "/" + (m+1) + "/" + y;
                        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
                    },
                    year, month, day
            );

            dp.show();
        });

//        btn1.findViewById(R.id.button2);
//        btn1.setOnClickListener(v -> {
//
//            Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            int minute = c.get(Calendar.MINUTE);
//
//            TimePickerDialog tp = new TimePickerDialog(
//                    MainActivity.this,
//                    (view, h, m) -> {
//                        String time = h + ":" + m;
//                        Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();
//                    },
//                    hour, minute, true
//            );
//
//            tp.show();
//        });


    }
}