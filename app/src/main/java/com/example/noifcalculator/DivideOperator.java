package com.example.noifcalculator;

/**
 * Created by seojohann on 11/3/16.
 */

public class DivideOperator extends Operator {
    public DivideOperator() { }

    @Override
    public double evaluate() {
        return (double)mLeftOperand.evaluate() / (double)mRightOperand.evaluate();
    }
}
