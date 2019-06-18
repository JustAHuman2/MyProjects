package com.tsystems.javaschool.tasks.calculator;

import java.util.Stack;

public class Calculator {

    public String evaluate(String statement) {
        try {
            double result = Calculator.notationToResult(Calculator.inputToNotation(statement));
            if (Double.isInfinite(result))
                return null;
            if (result == Math.round(result))
                return "" + (int) result;
            return "" + result;
        } catch (Exception e) {
            return null;
        }
    }

    private static String inputToNotation(String input) throws Exception {
        String current = "";
        Stack<Character> stack = new Stack<>();
        int priority;
        int unpairBrace = 0;
        for (int i = 0; i < input.length(); i++) {
            priority = getPriority(input.charAt(i));
            if (priority == 0)
                current += input.charAt(i);
            if (priority == 1) {
                stack.push(input.charAt(i));
                unpairBrace++;
            }
            if (priority > 1) {
                current += ' ';
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority)
                        current += stack.pop();
                    else
                        break;
                }
                stack.push(input.charAt(i));
            }
            if (priority == -1) {
                current += ' ';
                unpairBrace--;
                while (getPriority(stack.peek()) != 1)
                    current += stack.pop();
                stack.pop();
            }
        }
        while (!stack.empty())
            current += stack.pop();
        if (unpairBrace == 0) {
            return current;
        }
        return null;
    }

    private static double notationToResult(String notation) {
        String operand = new String();
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < notation.length(); i++) {
            if (notation.charAt(i) == ' ')
                continue;
            if (getPriority(notation.charAt(i)) == 0) {
                while (notation.charAt(i) != ' ' && getPriority(notation.charAt(i)) == 0) {
                    operand += notation.charAt(i++);
                    if (i == notation.length())
                        break;
                }
                stack.push(Double.parseDouble(operand));
                operand = new String();
            }
            if (getPriority(notation.charAt(i)) > 1) {
                double a = stack.pop(), b = stack.pop();
                if (notation.charAt(i) == '+')
                    stack.push(b + a);
                if (notation.charAt(i) == '-')
                    stack.push(b - a);
                if (notation.charAt(i) == '*')
                    stack.push(b * a);
                if (notation.charAt(i) == '/')
                    stack.push(b / a);
            }
        }
        return stack.pop();
    }

    private static int getPriority(char token) {
        if (token == '*' || token == '/')
            return 3;
        if ((token == '+' || token == '-'))
            return 2;
        if (token == '(')
            return 1;
        if (token == ')')
            return -1;
        return 0;
    }
}