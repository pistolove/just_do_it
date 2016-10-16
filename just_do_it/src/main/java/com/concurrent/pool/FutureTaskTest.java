package com.concurrent.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class FutureTaskTest {
    public static void main(String[] args) throws Exception {
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
        TSocket t = new TSocket("10.110.123.90", 1070, 2000);
        System.out.println(t.getSocket().getInetAddress());
        t.open();
        System.out.println(t.getSocket().getInetAddress());
        TTransport t2 = new TSocket("10.110.123.90", 1070, 2000);
        t2.open();

        // System.out.println(t.hashCode());
        // System.out.println(t2.hashCode());
        // System.out.println(t.equals(t2));
        t.close();
        t2.close();

        String s = t.hashCode() + "123";
        String s2 = t.hashCode() + "123";
        System.out.print(s == s2);

    }

    static class Task implements Callable<String> {
        public String call() throws Exception {
            System.out.println("test");
            // Thread.currentThread().sleep(5100L);
            return "futuretask test";
        }
    }

}
