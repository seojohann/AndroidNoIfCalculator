package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/3/16.
 */

public class AddOperator extends OperatorAddSubtract {

    public AddOperator() { }


    @Override
    public double evaluate()
    {
        double left = mLeftOperand.evaluate();
        double right = mRightOperand.evaluate();
        double answer = left + right;
        return answer;
    }

    @Override
    public String printToString() {
        return mLeftOperand.printToString() + " + " + mRightOperand.printToString();
    }
}
