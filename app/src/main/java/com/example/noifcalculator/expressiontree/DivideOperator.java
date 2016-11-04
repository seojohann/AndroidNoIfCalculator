package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/3/16.
 */

public class DivideOperator extends OperatorMultDivide {
    public DivideOperator() { }

    @Override
    public double evaluate() {
        return (double)mLeftOperand.evaluate() / (double)mRightOperand.evaluate();
    }
}
