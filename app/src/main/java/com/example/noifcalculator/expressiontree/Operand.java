package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/3/16.
 */

public class Operand extends CalcTreeNode {
    private double mValue;

    public Operand(double value) {
        mValue = value;
    }

    @Override
    public double evaluate() {
        /*
         * even though Operand has left and right children, just force return its value when
         * evaluate is called since we can't check children's without conditionals
         */
        return mValue;
    }


    @Override
    public String printToString() {
        return String.valueOf(mValue);
    }
}
