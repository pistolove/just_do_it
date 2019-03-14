package com.java.test;

public class TryFinallyTest {

    public static void main(String[] args) {
        System.out.println(test1());    //3
        System.out.println(test2());    //3
        System.out.println(test3());    //2
        System.out.println(test4());    //2
    }

    public static int test1() {
        int i = 1;
        try {
            i = 2;
            return i;
        } finally {
            i++;
            return i;
        }
    }

    public static int test2() {
        int i = 1;
        try {
            i = 2;
            return i;
        } finally {
            i = 3;
            return i++;
        }
    }

    public static int test3() {
        int i = 1;
        try {
            i = 2;
            return i;
        } finally {
            i++;
        }
    }

    public static int test4() {
        int i = 1;
        try {
            i = 2;
            return i;
        } finally {
            i = 3;
        }
    }
}
