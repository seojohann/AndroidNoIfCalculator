package com.example.noifcalculator;

import com.example.noifcalculator.expressiontree.OperatorInserter;

/**
 * Created by john.seo on 11/3/2016.
 */

public interface CalculatorState {
    void handleOperand(double number);
    void handleEnter();
    void handleOperator(OperatorInserter inserter);
}
