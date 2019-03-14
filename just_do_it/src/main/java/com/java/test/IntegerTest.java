package com.java.test;

public class IntegerTest {

    public static void main(String[] args) {
        int a = 1;
        Integer b = 1;
        Integer c = new Integer(1);

        Integer d = 1;

        Integer e = 1000;
        Integer f = 1000;

        System.out.println(a == b);
        System.out.println(b == c);

        System.out.println(b == d);
        
        System.out.println(e == f);
    }
}
