package com.example.noifcalculator;

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
        return mValue;
    }
}
