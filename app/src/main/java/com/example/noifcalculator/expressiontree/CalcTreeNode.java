package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/3/16.
 * any input on calculator. it could be a number/operand or operator(+,-,*,/)
 */

public abstract class CalcTreeNode {
    protected CalcTreeNode mLeftOperand;
    protected CalcTreeNode mRightOperand;

    public abstract double evaluate();
    public abstract CalcTreeNode insert(CalcTreeNode parent);
    //TODO need to figure out substituing ExpressionTree to avoid coupling.. Interface probably
    public abstract void insert(ExpressionTree tree);

    public void setLeftOperand(CalcTreeNode leftOperand) {
        mLeftOperand = leftOperand;
    }
    public void setRightOperand(CalcTreeNode rightOperand) {
        mRightOperand = rightOperand;
    }
    public CalcTreeNode getLeftOperand() {
        return mLeftOperand;
    }
    public CalcTreeNode getRightOperand() {
        return mRightOperand;
    }
}
