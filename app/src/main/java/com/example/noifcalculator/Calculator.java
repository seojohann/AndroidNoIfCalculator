package com.example.noifcalculator;

/**
 * Created by seojohann on 11/3/16.
 */

public class Calculator {
    private CalcTreeNode mExpressionTree;

    public Calculator() {

    }

    public void userInput() {

    }

    public double evaluate() {
        return mExpressionTree.evaluate();
    }
}
