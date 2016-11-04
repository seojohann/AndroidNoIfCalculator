package com.example.noifcalculator;

import com.example.noifcalculator.expressiontree.AddOperator;
import com.example.noifcalculator.expressiontree.CalcTreeNode;
import com.example.noifcalculator.expressiontree.DivideOperator;
import com.example.noifcalculator.expressiontree.ExpressionTree;
import com.example.noifcalculator.expressiontree.MultiplyOperator;
import com.example.noifcalculator.expressiontree.Operator;
import com.example.noifcalculator.expressiontree.OperatorInserter;
import com.example.noifcalculator.expressiontree.SubtractOperator;

/**
 * Created by seojohann on 11/3/16.
 */

public class Calculator {
    private ExpressionTree mExpressionTree;
    private CalculatorState mStateMachine;

    private double mNumber;

    private final CalculatorState CLEARED_STATE = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
            mStateMachine = FIRST_OPERATOR;
        }

        @Override
        public void handleOperator(Operator operator) {
            //do nothing since no pre operand is there
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            //do nothing since no pre operand is there
        }

        @Override
        public double handleEnter() {
            //do nothing since nothing is there to evaluate
            return 0;
        }
    };

    private final CalculatorState FIRST_OPERATOR = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
        }

        @Override
        public void handleOperator(Operator operator) {
            inputOperator(operator);
            mStateMachine = POST_OPERATOR;
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            inserter.insertOperator();
            mStateMachine = POST_OPERATOR;
        }

        @Override
        public double handleEnter() {
            double answer = evaluate();
            resetExpression();
            return answer;
        }
    };

    private final CalculatorState PRE_OPERATOR = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
        }

        @Override
        public void handleOperator(Operator operator) {
            inputOperator(operator);
            mStateMachine = POST_OPERATOR;
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            inserter.insertOperator();
            mStateMachine = POST_OPERATOR;
        }

        @Override
        public double handleEnter() {
            double answer = evaluate();
            resetExpression();
            return answer;
        }
    };

    private final CalculatorState POST_OPERATOR = new CalculatorState() {
        @Override
        public void handleOperand(double number) {
            inputNumber(number);
            mStateMachine = PRE_OPERATOR;
        }

        @Override
        public void handleOperator(Operator operator) {
            //do nothing since it's waiting for operand, not another operator
        }

        @Override
        public void handleOperator(OperatorInserter inserter) {
            //do nothing since it's waiting for operand, not another operator
        }

        @Override
        public double handleEnter() {
            //do nothing since expression is not complete
            return 0;
        }
    };

    public Calculator() {
        mExpressionTree = new ExpressionTree();
        mStateMachine = CLEARED_STATE;
        mNumber = 0;
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
//        mExpressionTree.inputNumber(mNumber);
//        mExpressionTree.insertAddSubtractOperator(new AddOperator());
//        mNumber = 0;
    }

    public void inputSubtractOp() {
        mStateMachine.handleOperator(subtractInserter);
//        mExpressionTree.inputNumber(mNumber);
//        mExpressionTree.insertAddSubtractOperator(new SubtractOperator());
//        mNumber = 0;
    }

    public void inputMultiplyOp() {
        mStateMachine.handleOperator(multiplyInserter);
//        mExpressionTree.inputNumber(mNumber);
//        mExpressionTree.insertMultiplyDivideOperator(new MultiplyOperator());
//        mNumber = 0;
    }

    public void inputDivideOp() {
        mStateMachine.handleOperator(divideInserter);
//        mExpressionTree.inputNumber(mNumber);
//        mExpressionTree.insertMultiplyDivideOperator(new DivideOperator());
//        mNumber = 0;
    }

    public void inputOperator(Operator operator) {
        mExpressionTree.inputNumber(mNumber);
        //TODO input operator
        mExpressionTree.insertNode(operator);
        mNumber = 0;
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
    }
}
