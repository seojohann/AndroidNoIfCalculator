package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/4/16.
 */

public interface OperatorState {
    public void onAddSubtractOpInput(Operator operator);
    public void onMultiplyDivideOpInput(Operator operator);
    public double onEvaluate();
}
