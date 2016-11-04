package com.example.noifcalculator.expressiontree;

import android.util.Log;

/**
 * Created by seojohann on 11/3/16.
 */

public class Operand extends CalcTreeNode {
    private double mValue;

    public Operand(double value) {
        mValue = value;
    }

    @Override
    public CalcTreeNode insert(CalcTreeNode parent) {
        parent.setRightOperand(this);
        return parent;
    }

    @Override
    public void insert(ExpressionTree tree) {
        insert(tree.getLastUsedOp());
    }

    @Override
    public double evaluate() {
        /*
         * even though Operand has left and right children, just force return its value when
         * evaluate is called since we can't check children's without conditionals
         */
        Log.d("jsbomb", "value(" + mValue + ")");
        return mValue;
    }
}
