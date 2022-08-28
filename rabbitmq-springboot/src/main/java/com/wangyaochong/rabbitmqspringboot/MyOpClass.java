package com.wangyaochong.rabbitmqspringboot;

public class MyOpClass {

    public static int myMethod(String type, int op1, int op2) {
        int result;
        if (type.equals("mul")) {
            result = op1 * op2;
        } else {
            result = op1 + op2;
        }
        return result;
    }
}
