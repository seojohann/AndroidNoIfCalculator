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


    private ExpressionTree.DivisorErrorHandler mZeroDivisorErrorHandler =
            new ExpressionTree.DivisorErrorHandler() {
        @Override
        public void onError() {

            resetExpression();

            mTextView.setText(R.string.zero_divisor_error_msg);
        }
    };

    /**
     * calculator state - there are times when certain keys shouldn't be handled depending on
     * the state of the calculator
     */
    abstract class CalculatorState {
        void handleOperand(double number) { }
        void insertOperand() { mExpressionTree.inputNumber(mNumber); }
        void handleEnter(OperatorInserter inserter) { }

        /**
         * operator is considered to insert to expression tree
         * @param inserter - use inserter to distinguish which operator is being inserted
         */
        void handleOperator(OperatorInserter inserter) { }

        void evaluate() { }
    }

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
        public void handleEnter(OperatorInserter inserter) {
            //do nothing since nothing is there to evaluate
        }

        @Override
        public void evaluate() {
            //can't evaluate
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
        public void handleEnter(OperatorInserter inserter) {
            inserter.insertOperator();
        }

        @Override
        public void evaluate() {
            //should be able to evaluate
            double result = mExpressionTree.evaluate();
            mTextView.setText(String.valueOf(result));
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
        public void handleEnter(OperatorInserter inserter) {
            //do nothing since expression is not complete
        }

        @Override
        public void evaluate() {
            //can't evaluate
        }
    };

    public Calculator(TextView textView) {
        mExpressionTree = new ExpressionTree();
        mExpressionTree.setZeroDivisorErrorHandler(mZeroDivisorErrorHandler);
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
            mExpressionTree.insertAddSubtractOperator(new AddOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter subtractInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.insertAddSubtractOperator(new SubtractOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter multiplyInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.insertMultiplyDivideOperator(new MultiplyOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter divideInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mExpressionTree.insertMultiplyDivideOperator(new DivideOperator());
            mNumber = 0;
        }
    };

    private OperatorInserter enterInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mNumber = 0;
            //and now evaluate the expression tree
            mStateMachine.evaluate();
        }
    };

    public void inputAddOp() {
        mStateMachine.insertOperand();
        mStateMachine.handleOperator(addInserter);
    }

    public void inputSubtractOp() {
        mStateMachine.insertOperand();
        mStateMachine.handleOperator(subtractInserter);
    }

    public void inputMultiplyOp() {
        mStateMachine.insertOperand();
        mStateMachine.handleOperator(multiplyInserter);
    }

    public void inputDivideOp() {
        mStateMachine.insertOperand();
        mStateMachine.handleOperator(divideInserter);
    }

    public double evaluate() {
        mStateMachine.insertOperand();
        mStateMachine.handleEnter(enterInserter);
        return 0;
    }

    public void resetExpression() {
        mExpressionTree.resetTree();
        mNumber = 0;
        mStateMachine = CLEARED_STATE;
        mTextView.setText(EMPTY);
    }
}
