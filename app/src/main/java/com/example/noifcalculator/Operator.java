package com.example.noifcalculator;

/**
 * Created by seojohann on 11/2/16.
 * operators evaluates its left and right operands
 */

public abstract class Operator extends CalcTreeNode {

    protected CalcTreeNode mLeftOperand;
    protected CalcTreeNode mRightOperand;

    public void setLeftOperand(CalcTreeNode leftOperand) {
        mLeftOperand = leftOperand;
    }

    public void setRightOperand(CalcTreeNode rightOperand) {
        mRightOperand = rightOperand;
    }
}
