package com.concurrent.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class test2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Threadd t = new Threadd(Thread.currentThread());
        // t.start();
        String s = "s";
        LockSupport.unpark(Thread.currentThread());

        test2 t = new test2();
        LockSupport.parkNanos(t, TimeUnit.SECONDS.toNanos(2));
        System.out.println("main thread is running");

        LockSupport.parkNanos(s, TimeUnit.SECONDS.toNanos(2));
        LockSupport.unpark(Thread.currentThread());

        System.out.println("main thread is running ..... ");
    }

    public static class Threadd extends Thread {
        Thread t = null;

        public Threadd(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(3000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LockSupport.unpark(this.t);
        }
    }
}
