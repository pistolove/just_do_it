package com.java.concurrent.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String[] args) {
        new CountDownLatchTest().test();
    }

    public void test() {
        ExecutorService es = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 4; i++) {
            es.submit(new runner(countDownLatch, 1000 * (i + 3)));
        }
        es.submit(new runner(countDownLatch, 0));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print("main thread has finished!");
        es.shutdown();
    }

    class runner implements Runnable {
        private CountDownLatch countDownLatch;
        private long sleepTime;

        public runner(CountDownLatch countDownLatch, long sleepTime) {
            this.countDownLatch = countDownLatch;
            this.sleepTime = sleepTime;
        }

        public void run() {
            CountDownLatchTest.print("is running");
            try {
                Thread.currentThread().sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.countDownLatch.countDown();
            CountDownLatchTest.print("is finish");
        }

    }

    public static void print(String str) {
        SimpleDateFormat dfdate = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + dfdate.format(new Date()) + "]" + Thread.currentThread().getName() + str);
    }

}
