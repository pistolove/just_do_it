package com.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomcIntegerTest {

    private AtomicInteger num = new AtomicInteger();
    private int num2 = 0;

    public static void main(String[] args) {
        new AtomcIntegerTest().test();
    }

    private void test() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000000; i++) {
            pool.submit(new runner());
        }

        try {
            Thread.currentThread().sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(this.num + "   " + this.num2);
        pool.shutdown();
    }

    private class runner implements Runnable {

        public void run() {
            AtomcIntegerTest.this.num.getAndIncrement();
            AtomcIntegerTest.this.num2++;
        }

    }
}
