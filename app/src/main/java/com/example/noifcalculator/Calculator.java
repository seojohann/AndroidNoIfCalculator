package com.example.noifcalculator;

import android.widget.TextView;

import com.example.noifcalculator.expressiontree.node.AddOperator;
import com.example.noifcalculator.expressiontree.node.DivideOperator;
import com.example.noifcalculator.expressiontree.ExpressionTree;
import com.example.noifcalculator.expressiontree.node.MultiplyOperator;
import com.example.noifcalculator.expressiontree.node.Operator;
import com.example.noifcalculator.expressiontree.OperatorInserter;
import com.example.noifcalculator.expressiontree.node.SubtractOperator;

/**
 * Created by seojohann on 11/3/16.
 * Calculator class. it has expression tree that keeps record of user inputs. return the result as
 * user presses "Enter" button. it also has StateMachine to filter out wrong inputs and to correctly
 * arrange the expression into the tree.
 */
public class Calculator {
    private static final String EMPTY = "";

    private ExpressionTree mExpressionTree;
    private CalculatorState mStateMachine;

    private double mNumber;

    private TextView mTextView;
    private StringBuffer mExpressionString;

    /**
     * calculator state - there are times when certain keys shouldn't be handled depending on
     * the state of the calculator
     */
    abstract class CalculatorState {
        /**
         * can calculator handle operand in current state?
         * @param number
         */
        void handleOperand(double number) { }

        /**
         * can calculator handle and insert operand to expression tree in current state?
         */
        void insertOperand() { }

        /**
         * can calculator handle enter action and evaluate the expression tree in current state?
         * @param inserter
         */
        void handleEnter(OperatorInserter inserter) { }

        /**
         * can calculator handle operator and insert it to expression tree
         * @param inserter - use inserter to distinguish which operator is being inserted
         */
        void handleOperator(OperatorInserter inserter) { }

        /**
         * can calculate evaluate the expression tree in current state?
         */
        void evaluate() { }
    }

    /**
     * initial state of calculator. calculator also returns to this state after evaluating
     * expression. can only accept operand in this state and go to FIRST_OPERATOR state after
     * accepting the first input. state reaches here in case of divide by 0 as well.
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
        void insertOperand() {
            mExpressionString.append((int)mNumber);
            mExpressionTree.inputNumber(mNumber);
        }

        @Override
        public void handleEnter(OperatorInserter inserter) {
            inserter.insertOperator();
        }

        @Override
        public void evaluate() {
            //should be able to evaluate
            mExpressionString.append("\n");
            double result = mExpressionTree.evaluate();
            mExpressionString.append(result);
            mTextView.setText(mExpressionString.toString());
            mExpressionString = new StringBuffer();
            mStateMachine = CLEARED_STATE;
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
        mExpressionTree.setZeroDivisorErrorHandler(new ExpressionTree.DivisorErrorHandler() {
            @Override
            public void onError() {
                resetExpression();
                mTextView.setText(R.string.zero_divisor_error_msg);
            }
        });
        mStateMachine = CLEARED_STATE;
        mNumber = 0;
        mTextView = textView;
        mExpressionString = new StringBuffer();
    }

    /**
     * number button is pressed
     * @param number -
     */
    private void inputNumber(double number) {
        mNumber = mNumber * 10 + number;
        String expression = mExpressionString.toString() + (int)mNumber;
        mTextView.setText(expression);
    }

    /**
     * handled operand after cheking current calculator state
     * @param number
     */
    public void inputOperand(double number) {
        mStateMachine.handleOperand(number);
    }

    /**
     * insert Add Operator to tree
     */
    private OperatorInserter addInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            Operator op = new AddOperator();
            mExpressionString.append(op.getOperatorSign());
            mTextView.setText(mExpressionString);
            mExpressionTree.insertAddSubtractOperator(op);
            mNumber = 0;
        }
    };

    /**
     * insert Subtract Operator to tree
     */
    private OperatorInserter subtractInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            Operator op = new SubtractOperator();
            mExpressionString.append(op.getOperatorSign());
            mTextView.setText(mExpressionString);
            mExpressionTree.insertAddSubtractOperator(op);
            mNumber = 0;
        }
    };

    /**
     * insert Multiply Operator to tree
     */
    private OperatorInserter multiplyInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            Operator op = new MultiplyOperator();
            mExpressionString.append(op.getOperatorSign());
            mTextView.setText(mExpressionString);
            mExpressionTree.insertMultiplyDivideOperator(op);
            mNumber = 0;
        }
    };

    /**
     * insert Divide Operator to tree
     */
    private OperatorInserter divideInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            Operator op = new DivideOperator();
            mExpressionString.append(op.getOperatorSign());
            mTextView.setText(mExpressionString);
            mExpressionTree.insertMultiplyDivideOperator(op);
            mNumber = 0;
        }
    };

    /**
     * begin evaluating the expression tree
     */
    private OperatorInserter enterInserter = new OperatorInserter() {
        @Override
        public void insertOperator() {
            mNumber = 0;
            //and now evaluate the expression tree
            mStateMachine.evaluate();
        }
    };

    /*
     * inputOp methods...
     * try to input left operand and operator after checking the calculator's state machine
     */
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

    /**
     * reset expression after error occurrence
     */
    public void resetExpression() {
        mExpressionTree.resetTree();
        mNumber = 0;
        mStateMachine = CLEARED_STATE;
        mExpressionString = new StringBuffer();
        mTextView.setText(EMPTY);
    }
}
