package com.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolRunExceptionTest {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, queue);
        task t1 = new task("t1");

        threadPoolExecutor.submit(t1);
        System.out.println("执行正常任务后线程数:" + threadPoolExecutor.getActiveCount());

        task2 t2 = new task2();
        threadPoolExecutor.submit(t2);

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("执行任务抛出异常后线程数:" + threadPoolExecutor.getActiveCount());

        threadPoolExecutor.shutdown();
    }

    static class task implements Runnable {
        private String s;

        public task(String s) {
            this.s = s;
        }

        public void run() {
            System.out.print(this.s);
        }

    }

    static class task2 implements Runnable {

        public void run() {
            System.out.println("失败任务执行完毕");
            throw new NullPointerException("123");
        }
    }
}
