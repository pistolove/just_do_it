package com.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.MILLISECONDS, queue);
        task t1 = new task("t1");
        task t2 = new task("t2");
        task t3 = new task("t3");
        task t4 = new task("t4");
        threadPoolExecutor.submit(t1);
        threadPoolExecutor.submit(t2);
        threadPoolExecutor.submit(t3);
        threadPoolExecutor.submit(t4);// 抛出异常
        threadPoolExecutor.shutdown();
    }

}

class task implements Runnable {
    private String s;

    public task(String s) {
        this.s = s;
    }

    public void run() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(this.s);
    }

}