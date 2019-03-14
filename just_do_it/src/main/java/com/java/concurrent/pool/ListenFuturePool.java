package com.java.concurrent.pool;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import java.util.List;
import java.util.concurrent.Executors;
import org.apache.commons.collections.CollectionUtils;

public class ListenFuturePool {

    protected ListeningExecutorService synService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

    public void syn(Runnable runnable) {

        List<ListenableFuture> futureList = Lists.newLinkedList();
        futureList.add(synService.submit(() -> runnable.run()));
        this.addFutures(futureList);
    }

    protected void addFutures(List<ListenableFuture> futureList) {
        if (CollectionUtils.isNotEmpty(futureList)) {
            final ListenableFuture allFutures =
                    Futures.successfulAsList(futureList.toArray(new ListenableFuture[futureList.size()]));
            Futures.addCallback(allFutures, new FutureCallback() {
                @Override
                public void onSuccess(Object result) {
                    System.out.println("Success syn task: ");
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("fail syn task: ");
                }
            });
        }
    }

    public static void main(String[] args) {
        ListenFuturePool pool = new ListenFuturePool();
        for (int i = 0; i < 5; i++) {
            pool.syn(new Runnable() {
                @Override public void run() {
                    try {
                        Thread.sleep(3 * 1000l);
                    } catch (Exception e) {

                    }
                }
            });
        }
    }
}
