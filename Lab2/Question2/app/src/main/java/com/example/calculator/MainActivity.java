package com.example.calculator;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.MatrixKt;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView res,cal;
    MaterialButton[] numbers=new MaterialButton[10];
    int[] buttonids = {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5, R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9};

    MaterialButton add,sub,mul,div;
    MaterialButton c,ac,eq,ob,cb,dot;
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
        res = findViewById(R.id.res);
        cal= findViewById(R.id.calc);

        for(int i=0;i<10;i++){
            assign_id(numbers[i],buttonids[i]);
        }
        assign_id(mul,R.id.btnmul);
        assign_id(div,R.id.btndiv);
        assign_id(add,R.id.btnplus);
        assign_id(sub,R.id.btnsub);

        assign_id(c,R.id.btnx);
        assign_id(ac,R.id.btnAC);
        assign_id(dot,R.id.btndot);
        assign_id(ob,R.id.btnOP);
        assign_id(cb,R.id.btnCB);
        assign_id(eq,R.id.btneq);

    }
    void assign_id(MaterialButton btn, int id){
        btn=findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String btntext=button.getText().toString();
        String expression=cal.getText().toString();
        if(btntext.equals("AC")){
            res.setText("0");
            cal.setText("");
            return;
        }
        if(btntext.equals("=")){
            res.setText(res.getText());
        }
        if(btntext.equals("C")){
            expression=expression.substring(0,expression.length()-1);
        }else {
            if(!btntext.equals("=")){
                expression=expression+btntext;
            }
        }

        cal.setText(expression);
        String finalResult=getResult(expression);
        if(!finalResult.equals("Err")){
            res.setText(finalResult);
        }
    }

    String getResult(String expression){
        try{
            Context context=Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable=context.initStandardObjects();
            String result=context.evaluateString(scriptable,expression,"Javascript",1,null).toString();
            return result;
        }catch(Exception e){
            return "Err";
        }
    }
}