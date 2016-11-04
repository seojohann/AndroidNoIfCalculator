package com.example.noifcalculator.expressiontree;

/**
 * Created by seojohann on 11/2/16.
 * operators evaluates its left and right operands
 */

public abstract class Operator extends CalcTreeNode {



    @Override
    public CalcTreeNode insert(CalcTreeNode parent) {
        this.setLeftOperand(parent);
        parent = this;
        return parent;
    }


}
