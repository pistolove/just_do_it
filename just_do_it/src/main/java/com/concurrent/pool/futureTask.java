package com.concurrent.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class futureTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();
        // Task task = new Task();
        // Future<String> result = es.submit(task);
        System.out.println(-1 << 29);
        System.out.println(1 << 29);
        Task task2 = new Task();
        FutureTask<String> ft = new FutureTask<String>(task2);
        es.submit(ft);
        System.out.println(ft.get());
        System.out.println("ft end");
        es.shutdown();

    }

    static class Task implements Callable<String> {
        public String call() throws Exception {
            System.out.println("test");
            Thread.currentThread().sleep(5100L);
            return "futuretask test";
        }
    }

}
