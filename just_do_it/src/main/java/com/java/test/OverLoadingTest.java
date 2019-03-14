package com.java.test;

/**
 * 方法重载测试
 */
public class OverLoadingTest {

    public static void main(String[] args) {
        A a = new B();
        B b = new B();

        C c = new C();
        c.call(a);
        c.call(b);
    }

    static class C {

        public void call(A a) {
            System.out.println("a");
        }

        public void call(B b) {
            System.out.println("b");
        }
    }

    static class A {

    }

    static class B extends A {

    }
}
