package com.uade.mys.pvi.services;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class FunctionResolver {

    public Double solveFunction(String expresion, Double argument, Double argument2){
        Argument t = new Argument("t = "+argument);
        Argument x = new Argument("x = "+((argument2==null)? 0:argument2));
        Expression expression = new Expression(expresion,t,x);

        return expression.calculate();
    }
}
