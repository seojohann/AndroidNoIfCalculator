package com.jsbomb.noifcalculator.test;

import com.example.noifcalculator.expressiontree.node.AddOperator;
import com.example.noifcalculator.expressiontree.node.DivideOperator;
import com.example.noifcalculator.expressiontree.node.MultiplyOperator;
import com.example.noifcalculator.expressiontree.node.Operand;
import com.example.noifcalculator.expressiontree.node.Operator;
import com.example.noifcalculator.expressiontree.node.SubtractOperator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by seojohann on 11/10/16.
 */

public class CalculatorTreeNodeTest {

    @Test
    public void testOperand() {
        Operand operand = new Operand(5);

        assertEquals(5, operand.evaluate(), 0.1);
        operand = new Operand(8.1);
        assertEquals(8.1, operand.evaluate(), 0.1);
    }

    @Test
    public void testAdd() {
        Operand leftOperand = new Operand(7);
        Operand rightOperand = new Operand(2);
        Operator operator = new AddOperator();

        operator.setLeftOperand(leftOperand);
        operator.setRightOperand(rightOperand);

        System.out.println("7 + 2 = " + operator.evaluate());
        assertEquals(7 + 2, operator.evaluate(), 0.1);
    }

    @Test
    public void testSubtract() {
        Operand leftOperand = new Operand(7);
        Operand rightOperand = new Operand(2);
        Operator operator = new SubtractOperator();

        operator.setLeftOperand(leftOperand);
        operator.setRightOperand(rightOperand);

        System.out.println("7 - 2 = " + operator.evaluate());
        assertEquals(7 - 2, operator.evaluate(), 0.1);
    }

    @Test
    public void testMultiply() {
        Operand leftOperand = new Operand(7);
        Operand rightOperand = new Operand(2);
        Operator operator = new MultiplyOperator();

        operator.setLeftOperand(leftOperand);
        operator.setRightOperand(rightOperand);

        System.out.println("7 * 2 = " + operator.evaluate());
        assertEquals(7 * 2, operator.evaluate(), 0.1);
    }

    @Test
    public void testDivide() {
        Operand leftOperand = new Operand(7);
        Operand rightOperand = new Operand(2);
        Operator operator = new DivideOperator();

        operator.setLeftOperand(leftOperand);
        operator.setRightOperand(rightOperand);

        System.out.println("7 / 2 = " + operator.evaluate());
        assertEquals(7 / (double)2, operator.evaluate(), 0.0001);
    }
}
