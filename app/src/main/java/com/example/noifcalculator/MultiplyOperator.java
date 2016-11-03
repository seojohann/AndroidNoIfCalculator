package com.example.noifcalculator;

/**
 * Created by seojohann on 11/3/16.
 */

public class MultiplyOperator extends Operator {

    public MultiplyOperator() { }

    @Override
    public double evaluate() {
        return mLeftOperand.evaluate() * mRightOperand.evaluate();
    }
}
