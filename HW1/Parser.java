/**
 * Created by Михаил on 20.10.2016.
 */


import java.util.*;

public class Parser {
    private int k = 0;
    private final char[] stringExpression;
    private int varCount;
    Expression result;
    Stack<Integer> exprDepth;
    HashMap<String, Integer> variables;
    StringBuilder temp;

    public Parser(String stringExpression) {
        this.stringExpression = stringExpression.toCharArray();
        k = 0;
        varCount = 0;
        variables = new HashMap<>();
        exprDepth = new Stack<>();
    }

    private void avoidSpaces() {
        while ((k < stringExpression.length) &&
                ((stringExpression[k] == ' ') ||
                        (stringExpression[k] == (char) 13) ||
                        (stringExpression[k] == '\t'))) {
            k++;
        }
    }

    private Operations getOperation(boolean flag) {
        Operations res = null;
        char oper = stringExpression[k];
        int operationLenght = 1;
        switch (oper) {
            case '&': {
                res = Operations.AND;
                break;
            }
            case '|': {
                res = Operations.OR;
                break;
            }
            case '!': {
                res = Operations.NEG;
                break;
            }
            case '-': {
                if (stringExpression[k + 1] == '>') {
                    res = Operations.IMPL;
                    operationLenght = 2;
                    break;
                } else {
                    break;
                }
            }

        }
        if (res != null && flag) {
            k += operationLenght;
        }
        return res;
    }

    private Expression getPropVar() {
        String newVar;
        int varNumber;
        temp = new StringBuilder();
        while (k < stringExpression.length && (Character.isLetter(stringExpression[k]) || Character.isDigit(stringExpression[k]))) {
            temp.append(stringExpression[k++]);
        }
        newVar = temp.toString();
        if (variables.containsKey(newVar)) {
            varNumber = variables.get(newVar);
        } else {
            varNumber = varCount++;
            variables.put(newVar, varNumber);
        }
//        System.err.println("must return propVar there");
//        System.err.println(varNumber + " " + newVar);
        return new PropVar(varNumber, newVar);
    }

    public Expression parsedExpr() {
        return parse(0, 3);
    }

    private Expression parse(int depth, int priority) {
        avoidSpaces();
        if (priority != 0) {
            Expression res = parse(depth, priority - 1);
            avoidSpaces();
            while ((k < stringExpression.length) &&
                    (getOperation(false) != null) &&
                    (Operations.getPriority(getOperation(false)) == priority)) {
                avoidSpaces();
                Operations curOper = getOperation(true);
                avoidSpaces();
                Expression nextExpression = null;
                if (Operations.getPart(curOper) == Operations.Parts.RIGHT) {
                    exprDepth.add(depth);
                    nextExpression = parse(depth + 1, 3);
                } else {
                    nextExpression = parse(depth, priority - 1);
                }
                res = new BinaryExpression(res, nextExpression, curOper);
                if (k < stringExpression.length && !exprDepth.empty() && exprDepth.peek() == depth) {
                    exprDepth.pop();
                    return res;
                }
            }
            return res;
        } else {
            if (Character.isLetter(stringExpression[k]) || Character.isDigit(stringExpression[k])) {
                return getPropVar();
            } else if (stringExpression[k] == '(') {
                k++;
                Expression fag = parse(depth + 1, 3);
                k++;
                return fag;
            } else if (getOperation(false) == Operations.NEG) {
                k++;
                return new SingleArg(parse(depth + 1, priority), Operations.NEG);
            }
            return null;
        }
    }
}
