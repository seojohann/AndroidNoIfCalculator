package com.example.noifcalculator.expressiontree;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seojohann on 11/3/16.
 * Two nodes are there to keep track of 1. tree root, which is needed for evaluating the expression
 * 2. last used operator, to insert operand. both nodes are used for inserting operators as well.
 */
public class ExpressionTree {
    //need reference to the root of the tree, use root node's right operand.
    private CalcTreeNode mTreeRoot; //for begining of evaluation and inserting +-
    private CalcTreeNode mLastUsedOp; //for inserting */
    private OperatorState mOperatorState;

    //for divide by 0 error check
    private Map<Boolean, DivisorCheck> mDivisorCheckMap;

    /**
     * to handle divide by 0 error. use onError() method to do what's needed
     */
    public interface DivisorErrorHandler {
        void onError();
    }

    /**
     * Used as State Machine for inserting multiply and divide operator. because the precedence for
     * multiply and divide is higher than add and subtract, they need to be inserted to a different
     * location in the tree.
     */
    private interface OperatorState {
        void onMultiplyDivideOpInput(Operator operator);
    }

    /**
     * checker for divide by 0 error
     */
    interface DivisorCheck {
        void postCheck(CalcTreeNode operand);
    }

    /**
     * handler for divide by 0 error to link to Calculator
     */
    private DivisorErrorHandler mDivisorErrorHandler;

    /**
     * initial state where no operator has bee in serted. insert multiply or divide operator as a
     * root. consequent multiply or divide operator will use
     */
    private final OperatorState NO_ROOT_OP = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            insertMultDivOperatorToRoot(operator);
            mOperatorState = MULTI_DIV_ROOT;
        }
    };

    /**
     * Add or Subtract Operator is the root in current state. multiply or divide operator should
     * use last used operator instead of root
     */
    private final OperatorState ADD_SUBT_ROOT = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            //multiply or divide doesn't change the root when current root op is add or subtract
            insertMultDivOperator(operator);
            mOperatorState = MULTI_DIV_LAST_USED_OP;
        }
    };

    /**
     * inserting consequent multiply or divide operator, but Add or Subtract is already the root.
     */
    private final OperatorState MULTI_DIV_LAST_USED_OP = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            insertMultDivOperatorInARow(operator);
        }
    };

    /**
     * multiply or divide operator is the root and inserting another multiply or divide operator.
     */
    private final OperatorState MULTI_DIV_ROOT = new OperatorState() {
        @Override
        public void onMultiplyDivideOpInput(Operator operator) {
            insertMultDivOperatorToRoot(operator);
        }
    };

    /**
     * check if user is dividing by 0. if so, call on Error handler to stop further and alert the
     * user of the error
     */
    private final DivisorCheck ZERO_DIVISOR = new DivisorCheck() {
        @Override
        public void postCheck(CalcTreeNode operand) {
            mDivisorErrorHandler.onError();
        }
    } ;

    /**
     * user is not dividing by 0. continue by inserting operand to available operator
     */
    private final DivisorCheck NONZERO_DIVISOR = new DivisorCheck() {
        @Override
        public void postCheck(CalcTreeNode operand) {
            getLastUsedOp().setRightOperand(operand);
        }
    };

    public ExpressionTree() {
        resetTree();

        mDivisorCheckMap = new HashMap<>();
        mDivisorCheckMap.put(true, ZERO_DIVISOR);
        mDivisorCheckMap.put(false, NONZERO_DIVISOR);
    }

    public ExpressionTree(DivisorErrorHandler handler) {
        this();
        mDivisorErrorHandler = handler;
    }

    public double evaluate() {
        Log.d("jsbomb", mTreeRoot.getRightOperand().printToString());
        double result = mTreeRoot.getRightOperand().evaluate();
        resetTree();
        mOperatorState = NO_ROOT_OP;
        return result;
    }

    /**
     * reset tree due to initialization, evaluation, or error
     */
    public void resetTree() {
        mTreeRoot = new Operand(0);
        mLastUsedOp = mTreeRoot;
        mOperatorState = NO_ROOT_OP;
    }

    /**
     * inserting operand to a tree. checks for divide by 0 operand error
     * @param number
     */
    public void inputNumber(double number) {
        CalcTreeNode operand = new Operand(number);
        //is the last used op divide? and current number is 0?
        boolean isDivisor = (mLastUsedOp instanceof DivideOperator) && number == 0;
        //get the correct checker
        DivisorCheck checker = mDivisorCheckMap.get(isDivisor);
        checker.postCheck(operand);
    }

    /**
     * inserting add or subtract operator to a tree. current root becomes operator's left operand
     * and the operator becomes the new root
     * @param operator
     */
    public void insertAddSubtractOperator(Operator operator) {
        operator.setLeftOperand(mTreeRoot.getRightOperand());
        mTreeRoot.setRightOperand(operator);
        mLastUsedOp = operator;
        mOperatorState = ADD_SUBT_ROOT;
    }

    /**
     * inserting multiply or divide operator to a tree. depending on current state, operator needs
     * to be inserted in different location. if no root, or another multiply/divide operator is the
     * root, then new operator will become the new root and take old root as its left operand.
     * if add/subtract is the root, then 1) multiply/divide should take root's right operand as its
     * left operand and 2) become root's right operand
     * @param operator
     */
    public void insertMultiplyDivideOperator(Operator operator) {
        mOperatorState.onMultiplyDivideOpInput(operator);
    }

    /**
     * add/subtract is already taken as the root. become the right operand and set whatever was
     * there as its left operand
     * @param operator
     */
    private void insertMultDivOperator(Operator operator) {
        operator.setLeftOperand(mLastUsedOp.getRightOperand());
        mLastUsedOp.setRightOperand(operator);
        mLastUsedOp = operator;
    }

    /**
     * add/subtract is the root, and multiply/divide is its right operand already. take existing
     * multiply/divide as the new multiply/divide operator's left operand and become a new right
     * operand for the root
     * @param operator
     */
    private void insertMultDivOperatorInARow(Operator operator) {
        operator.setLeftOperand(mTreeRoot.getRightOperand().getRightOperand());
        mTreeRoot.getRightOperand().setRightOperand(operator);
        mLastUsedOp = operator;
    }

    /**
     * become the root since no other operator has taken the position. or another multiply/divide
     * operator is the root. replace by setting current multiply/divide as new one's left operand
     * @param operator
     */
    private void insertMultDivOperatorToRoot(Operator operator) {
        operator.setLeftOperand(mTreeRoot.getRightOperand());
        mLastUsedOp = operator;
        mTreeRoot.setRightOperand(operator); //new tree root set
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

    public void setZeroDivisorErrorHandler(DivisorErrorHandler handler) {
        mDivisorErrorHandler = handler;
    }
}
