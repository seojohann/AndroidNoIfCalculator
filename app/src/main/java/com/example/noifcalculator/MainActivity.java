package com.example.noifcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button mBtn_0;
    private Button mBtn_1;
    private Button mBtn_2;
    private Button mBtn_3;
    private Button mBtn_4;
    private Button mBtn_5;
    private Button mBtn_6;
    private Button mBtn_7;
    private Button mBtn_8;
    private Button mBtn_9;
    private Button mBtn_add;
    private Button mBtn_subtract;
    private Button mBtn_multiply;
    private Button mBtn_divide;
    private Button mBtn_enter;

    private TextView mTV_input;

    private Calculator mCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        initViews();
        setClickListeners();
    }

    private void initViews() {
        mTV_input = (TextView)findViewById(R.id.txt_input);

        mBtn_0 = (Button)findViewById(R.id.btn_0);
        mBtn_1 = (Button)findViewById(R.id.btn_1);
        mBtn_2 = (Button)findViewById(R.id.btn_2);
        mBtn_3 = (Button)findViewById(R.id.btn_3);
        mBtn_4 = (Button)findViewById(R.id.btn_4);
        mBtn_5 = (Button)findViewById(R.id.btn_5);
        mBtn_6 = (Button)findViewById(R.id.btn_6);
        mBtn_7 = (Button)findViewById(R.id.btn_7);
        mBtn_8 = (Button)findViewById(R.id.btn_8);
        mBtn_9 = (Button)findViewById(R.id.btn_9);

        mBtn_add = (Button)findViewById(R.id.btn_add);
        mBtn_subtract = (Button)findViewById(R.id.btn_subtract);
        mBtn_multiply = (Button)findViewById(R.id.btn_multiply);
        mBtn_divide = (Button)findViewById(R.id.btn_divide);

        mBtn_enter = (Button)findViewById(R.id.btn_enter);
    }

    private void setClickListeners() {
        setOperandListeners();
    }

    private View.OnClickListener mOperandClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button thisButton = (Button)v;
            double value = Double.parseDouble(thisButton.getText().toString());
            Operand operand = new Operand(value);
            //TODO insert operand to calculator model/context..
            Toast.makeText(getApplicationContext(), (int)value + " pressed", Toast.LENGTH_SHORT).show();
        }
    };

    private void setOperandListeners() {
        mBtn_0.setOnClickListener(mOperandClickListener);
        mBtn_1.setOnClickListener(mOperandClickListener);
        mBtn_2.setOnClickListener(mOperandClickListener);
        mBtn_3.setOnClickListener(mOperandClickListener);
        mBtn_4.setOnClickListener(mOperandClickListener);
        mBtn_5.setOnClickListener(mOperandClickListener);
        mBtn_6.setOnClickListener(mOperandClickListener);
        mBtn_7.setOnClickListener(mOperandClickListener);
        mBtn_8.setOnClickListener(mOperandClickListener);
        mBtn_9.setOnClickListener(mOperandClickListener);
    }

    private View.OnClickListener mAddClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Operator operator = new AddOperator();
            //TODO insert operator to calculator model/context..
        }
    };

    private void setOperatorListeners() {
        mBtn_add.setOnClickListener(mAddClickListener);
    }

    private void initCalculator() {
        mCalculator = new Calculator();
    }
}
