package com.example.noifcalculator;

/**
 * Created by seojohann on 11/3/16.
 */

public class AddOperator extends Operator {

    public AddOperator() { }


    @Override
    public double evaluate() {
        return mLeftOperand.evaluate() + mRightOperand.evaluate();
    }
}
