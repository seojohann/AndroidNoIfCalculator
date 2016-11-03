package com.example.noifcalculator;

/**
 * Created by seojohann on 11/2/16.
 * operators evaluates its left and right operands
 */

public abstract class Operator extends CalculatorInput {

    protected CalculatorInput mLeftOperand;
    protected CalculatorInput mRightOperand;

    public void setLeftOperand(CalculatorInput leftOperand) {
        mLeftOperand = leftOperand;
    }

    public void setRightOperand(CalculatorInput rightOperand) {
        mRightOperand = rightOperand;
    }
}
