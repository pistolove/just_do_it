package com.java.concurrent;

public class ThreadTest {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new ThreadT().start();
        }
    }

    static class ThreadT extends Thread {

        public void run() {
            System.out.println("hello world");
        }
    }
}
