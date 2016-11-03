package com.example.noifcalculator;

/**
 * Created by seojohann on 11/3/16.
 */

public class Calculator {
    private CalculatorInput mCalculatorInput;

    public Calculator() {

    }

    public void insertCalculatorInput(CalculatorInput input) {
        mCalculatorInput = input;
    }

    public double evaluate() {
        return mCalculatorInput.evaluate();
    }
}
