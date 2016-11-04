package com.example.noifcalculator;

import com.example.noifcalculator.expressiontree.Operator;
import com.example.noifcalculator.expressiontree.OperatorInserter;

/**
 * Created by john.seo on 11/3/2016.
 */

public interface CalculatorState {
    public abstract void handleOperand(double number);
    public abstract void handleOperator(Operator operator);
    public abstract double handleEnter();
    public abstract void handleOperator(OperatorInserter inserter);
}
