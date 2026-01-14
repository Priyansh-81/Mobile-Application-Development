package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView display;
    boolean isResultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] allBtns = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv, R.id.btnMod
        };

        for (int id : allBtns) {
            findViewById(id).setOnClickListener(v -> onButtonClick((Button) v));
        }

        findViewById(R.id.btnEq).setOnClickListener(v -> calculate());

        findViewById(R.id.clrAllbtn).setOnClickListener(v -> {
            display.setText("0");
            isResultShown = false;
        });

        findViewById(R.id.deleteBtn).setOnClickListener(v -> deleteLast());
    }

    private void onButtonClick(Button b) {

        String value = b.getText().toString();
        String current = display.getText().toString();

        if (current.equals("0") || isResultShown) {
            display.setText(value);
            isResultShown = false;
        } else {
            display.append(value);
        }
    }

    private void deleteLast() {

        String text = display.getText().toString();

        if (text.length() > 1) {
            display.setText(text.substring(0, text.length() - 1));
        } else {
            display.setText("0");
        }

        isResultShown = false;
    }

    private void calculate() {

        try {
            String expr = display.getText().toString();

            // Step 1: Split numbers and operators
            ArrayList<Double> numbers = new ArrayList<>();
            ArrayList<Character> ops = new ArrayList<>();

            String temp = "";

            for (int i = 0; i < expr.length(); i++) {
                char ch = expr.charAt(i);

                if (Character.isDigit(ch)) {
                    temp += ch;
                } else {
                    numbers.add(Double.parseDouble(temp));
                    temp = "";
                    ops.add(ch);
                }
            }
            numbers.add(Double.parseDouble(temp));

            // Step 2: Handle × ÷ %
            for (int i = 0; i < ops.size(); i++) {

                char op = ops.get(i);

                if (op == '×' || op == '*' || op == '÷' || op == '/' || op == '%') {

                    double a = numbers.get(i);
                    double b = numbers.get(i + 1);
                    double res = 0;

                    if (op == '×' || op == '*') res = a * b;
                    else if (op == '÷' || op == '/') {
                        if (b == 0) {
                            display.setText("Error");
                            isResultShown = true;
                            return;
                        }
                        res = a / b;
                    }
                    else if (op == '%') res = a % b;

                    numbers.set(i, res);
                    numbers.remove(i + 1);
                    ops.remove(i);
                    i--; // stay at same index
                }
            }

            double result = numbers.get(0);

            for (int i = 0; i < ops.size(); i++) {
                char op = ops.get(i);
                double next = numbers.get(i + 1);

                if (op == '+') result += next;
                else if (op == '-') result -= next;
            }

            // Step 4: Display result
            if (result == (int) result)
                display.setText(String.valueOf((int) result));
            else
                display.setText(String.valueOf(result));

            isResultShown = true;

        } catch (Exception e) {
            display.setText("Error");
            isResultShown = true;
        }
    }
}