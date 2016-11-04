package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/3/16.
 */

public class SubtractOperator extends OperatorAddSubtract {

    public SubtractOperator() { }

    @Override
    public double evaluate() {
        return mLeftOperand.evaluate() - mRightOperand.evaluate();
    }
}
