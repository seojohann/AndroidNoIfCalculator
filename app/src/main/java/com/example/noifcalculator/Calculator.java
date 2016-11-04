package com.example.noifcalculator;

import android.widget.TextView;

import com.example.noifcalculator.expressiontree.AddOperator;
import com.example.noifcalculator.expressiontree.DivideOperator;
import com.example.noifcalculator.expressiontree.ExpressionTree;
import com.example.noifcalculator.expressiontree.MultiplyOperator;
import com.example.noifcalculator.expressiontree.OperatorInserter;
import com.example.noifcalculator.expressiontree.SubtractOperator;

/**
 * Created by seojohann on 11/3/16.
 */

public class Calculator {
    private static final String OP_FORMAT = " %s ";
    private static final String EMPTY = "";

    private ExpressionTree mExpressionTree;
    private CalculatorState mStateMachine;

    private double mNumber;

    private TextView mTextView;

    /**
     * initial state of calculator. calculator also returns to this state after evaluating
     * expression. can only accept operand in this state and go to FIRST_OPERATOR state after
     * accepting the first input
     */
    private final CalculatorState CLEARED_STATE = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
            mStateMachine = PRE_OPERATOR;
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            //do nothing since no pre operand is there
        }

        @Override
        public void handleEnter() {
            //do nothing since nothing is there to evaluate
        }
    };

    /**
     * state where a single digit number is entered. calculator can accept additional number input,
     * operator, or enter. in case of inserting an operator, move to POST_OPERATOR afterward.
     */
    private final CalculatorState FIRST_OPERATOR = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            inserter.insertOperator();
            mStateMachine = POST_OPERATOR;
        }

        @Override
        public void handleEnter() {
            double answer = evaluate();
            resetExpression();
        }
    };

    /**
     * state where a single digit number is entered, and possibly before operator is entered.
     * calculator can accept additional number input, operator, or enter. in case of inserting
     * an operator, move to POST_OPERATOR afterward.
     */
    private final CalculatorState PRE_OPERATOR = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            inserter.insertOperator();
            mStateMachine = POST_OPERATOR;
        }

        @Override
        public void handleEnter() {
            double answer = evaluate();
            resetExpression();
        }
    };

    /**
     * state after operator has been inserted. should only except operand to complete the expression
     */
    private final CalculatorState POST_OPERATOR = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
            mStateMachine = PRE_OPERATOR;
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            //do nothing since it's waiting for operand, not another operator
        }

        @Override
        public void handleEnter() {
            //do nothing since expression is not complete
        }
    };

    public Calculator(TextView textView) {
        mExpressionTree = new ExpressionTree();
        mStateMachine = CLEARED_STATE;
        mNumber = 0;
        mTextView = textView;
    }

    private void inputNumber(double number) {
        mNumber = mNumber * 10 + number;
    }

    public void inputOperand(double number) {
        mStateMachine.handleOperand(number);
    }

    private OperatorInserter addInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.inputNumber(mNumber);
            mExpressionTree.insertAddSubtractOperator(new AddOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter subtractInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.inputNumber(mNumber);
            mExpressionTree.insertAddSubtractOperator(new SubtractOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter multiplyInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.inputNumber(mNumber);
            mExpressionTree.insertMultiplyDivideOperator(new MultiplyOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter divideInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.inputNumber(mNumber);
            mExpressionTree.insertMultiplyDivideOperator(new DivideOperator());
            mNumber = 0;
        }
    };

    public void inputAddOp() {
        mStateMachine.handleOperator(addInserter);
    }

    public void inputSubtractOp() {
        mStateMachine.handleOperator(subtractInserter);
    }

    public void inputMultiplyOp() {
        mStateMachine.handleOperator(multiplyInserter);
    }

    public void inputDivideOp() {
        mStateMachine.handleOperator(divideInserter);
    }

    public double evaluate() {
        //put the right operand to the tree
        mExpressionTree.inputNumber(mNumber);
        mNumber = 0;
        //and now evaluate the expression tree
        return mExpressionTree.evaluate();
    }

    public void resetExpression() {
        mExpressionTree.resetTree();
        mNumber = 0;
        mStateMachine = CLEARED_STATE;
        mTextView.setText(EMPTY);
    }
}
