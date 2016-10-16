package com.concurrent.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Threadd t = new Threadd(Thread.currentThread());
        t.start();

        Thread.currentThread().sleep(3000L);
        System.out.println("t has unparked main thread");
        LockSupport.park();
        LockSupport.park();
        System.out.println("main thread is running");
    }

    public static class Threadd extends Thread {
        Thread t = null;

        public Threadd(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            LockSupport.unpark(this.t);

            for (long i = 0; i < 10; i++) {
                System.out.println("a" + i);
                try {
                    Thread.currentThread().sleep(1000L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            LockSupport.unpark(this.t);
        }
    }

}
