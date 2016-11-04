package com.example.noifcalculator.expressiontree;

import android.util.Log;

/**
 * Created by seojohann on 11/3/16.
 */

public class ExpressionTree {
    private CalcTreeNode mTreeRoot; //for begining of evaluation and inserting +-
    private CalcTreeNode mLastUsedOp; //for inserting */
    private OperatorState mOperatorState;

    public interface OperatorState {
        public void onMultiplyDivideOpInput(Operator operator);
    }

    private final OperatorState NO_ROOT_OP = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            insertMultDivOperatorToRoot(operator);
            mOperatorState = MULTI_DIV_ROOT;
        }
    };

    private final OperatorState ADD_SUBT_ROOT = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            //multiply or divide doesn't change the root when current root op is add or subtract
            Log.d("jsbomb", "should do nothing");
            insertMultDivOperator(operator);
            mOperatorState = MULTI_DIV_LAST_USED_OP;
        }
    };

    private final OperatorState MULTI_DIV_LAST_USED_OP = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            insertMultDivOperatorInARow(operator);
        }
    };

    private final OperatorState MULTI_DIV_ROOT = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            insertMultDivOperatorToRoot(operator);
        }
    };

    public ExpressionTree() {
        mTreeRoot = new Operand(0);
        mLastUsedOp = mTreeRoot;
        mOperatorState = NO_ROOT_OP;
    }

    public double evaluate() {
        double result = mTreeRoot.getRightOperand().evaluate();
        resetTree();
        mOperatorState = NO_ROOT_OP;
        return result;
    }

    public void resetTree() {
        mTreeRoot = new Operand(0);
        mLastUsedOp = mTreeRoot;
    }

    public void inputNumber(double number) {
        CalcTreeNode operand = new Operand(number);
        getLastUsedOp().setRightOperand(operand);
//        operand.insert(this);
    }

    public void insertNode(CalcTreeNode node) {
        node.insert(this);
    }

    public CalcTreeNode getTreeRoot() {
        return mTreeRoot;
    }

    public CalcTreeNode getLastUsedOp() {
        return mLastUsedOp;
    }

    protected void setLastUsedOp(CalcTreeNode recentlyUsedOp) {
        mLastUsedOp = recentlyUsedOp;
    }

    public void insertAddSubtractOperator(Operator operator) {
        operator.setLeftOperand(mTreeRoot.getRightOperand());
        mTreeRoot.setRightOperand(operator);
        mLastUsedOp = operator;
        mOperatorState = ADD_SUBT_ROOT;
    }

    public void insertMultiplyDivideOperator(Operator operator) {
        mOperatorState.onMultiplyDivideOpInput(operator);
    }

    private void insertMultDivOperator(Operator operator) {
        operator.setLeftOperand(mLastUsedOp.getRightOperand());
        mLastUsedOp.setRightOperand(operator);
        mLastUsedOp = operator;
    }

    private void insertMultDivOperatorInARow(Operator operator) {
        operator.setLeftOperand(mTreeRoot.getRightOperand().getRightOperand());
        mTreeRoot.getRightOperand().setRightOperand(operator);
        mLastUsedOp = operator;
    }

    private void insertMultDivOperatorToRoot(Operator operator) {
        operator.setLeftOperand(mTreeRoot.getRightOperand());
        mLastUsedOp = operator;
        mTreeRoot.setRightOperand(operator);
    }
}
