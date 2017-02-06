package com.example.noifcalculator.expressiontree.node;

/**
 * Created by seojohann on 11/3/16.
 * operators evaluates its left and right operands
 */
public abstract class Operator extends CalcTreeNode {
    public abstract String getOperatorSign();
}
