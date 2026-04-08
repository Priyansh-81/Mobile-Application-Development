package com.priyansh.toastandbutton;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn, cbtn;
    ToggleButton tbtn, ctbtn;

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

        btn=findViewById(R.id.button);
        tbtn=findViewById(R.id.toggleButton);
        cbtn=findViewById(R.id.buttonCustom);
        ctbtn=findViewById(R.id.toggleButtonCustom);

        btn.setOnClickListener(v -> {
            Toast.makeText(this,"Button Clicked",Toast.LENGTH_SHORT).show();
        });

        tbtn.setOnClickListener(v->{
            if(tbtn.isChecked()){
                Toast.makeText(this,"Toggle button ON", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Toggle Button OFF",Toast.LENGTH_SHORT).show();
            }
        });

        cbtn.setOnClickListener(v -> {
            showCustom("Button Clicked",R.drawable.ic_launcher_background);
        });
        ctbtn.setOnClickListener(v->{
            if (ctbtn.isChecked()) {
                showCustom("Custom Toggle ON",R.drawable.ic_launcher_foreground);
            }else{
                showCustom("Custom Toggle OFF",R.drawable.ic_launcher_background);
            }
        });
    }

    void showCustom(String str,int img){
        LayoutInflater inf=getLayoutInflater();
        View toast=inf.inflate(R.layout.custom_toast,null);

        ImageView image=toast.findViewById(R.id.imageView);
        TextView t=toast.findViewById(R.id.textView);

        t.setText(str);
        image.setImageResource(img);

        Toast toast1=new Toast(getApplicationContext());
        toast1.setDuration(Toast.LENGTH_SHORT);
        toast1.setView(toast);
        toast1.show();
    }
}