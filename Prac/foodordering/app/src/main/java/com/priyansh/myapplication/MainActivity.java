package com.priyansh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.CheckBox;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CheckBox pasta, pizza, human, strogenoff;
    Button btn;
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
        pasta=findViewById(R.id.pasta);
        pizza=findViewById(R.id.pizza);
        human=findViewById(R.id.human);
        strogenoff=findViewById(R.id.strogenoff);
        btn=findViewById(R.id.submit);


        btn.setOnClickListener(v -> {
            int total=0;
            StringBuilder str=new StringBuilder();

            if(pasta.isChecked()){
                str.append(pasta.getText().toString()).append("\n");
                total+=250;
            }
            if(pizza.isChecked()){
                str.append(pizza.getText().toString()).append("\n");
                total+=550;
            }
            if(human.isChecked()){
                str.append(human.getText().toString()).append("\n");
                total+=15000;
            }
            if(strogenoff.isChecked()){
                str.append(strogenoff.getText().toString()).append("\n");
                total+=300;
            }
            Intent intent=new Intent(MainActivity.this, Summary.class);
            intent.putExtra("List",str.toString());
            intent.putExtra("Total",total);
            startActivity(intent);
        });


    }
}