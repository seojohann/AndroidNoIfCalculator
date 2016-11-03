package com.example.noifcalculator;

/**
 * Created by seojohann on 11/3/16.
 */

public class SubtractOperator extends Operator {

    public SubtractOperator() { }

    @Override
    public double evaluate() {
        return mLeftOperand.evaluate() - mRightOperand.evaluate();
    }
}
