package com.concurrent.pool;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ExpensiveCache implements Computable<String, BigInteger> {

    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }

}

interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}

//
class Service1<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public Service1(Computable<A, V> c) {
        this.c = c;
    }

    public synchronized V compute(A arg) throws InterruptedException {
        V result = this.cache.get(arg);
        if (result == null) {
            result = this.c.compute(arg);
            this.cache.put(arg, result);
        }
        return result;
    }
}

class Service2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Service2(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(A arg) throws InterruptedException {
        V result = this.cache.get(arg);
        if (result == null) {
            result = this.c.compute(arg);
            this.cache.put(arg, result);
        }
        return result;
    }

}

class Service3<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Service3(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        Future<V> result = this.cache.get(arg);
        if (result == null) {
            Callable<V> call = new Callable<V>() {
                public V call() throws Exception {
                    return Service3.this.c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(call);
            result = ft;
            this.cache.put(arg, result);
            ft.run();
        }
        try {
            return result.get();
        } catch (ExecutionException e) {
            return null;
        }
    }
}

class perfact<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public perfact(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> result = this.cache.get(arg);
            if (result == null) {
                Callable<V> call = new Callable<V>() {
                    public V call() throws Exception {
                        return perfact.this.c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(call);
                result = this.cache.putIfAbsent(arg, ft);
                if (result == null) {
                    result = ft;
                    ft.run();
                }
            }
            try {
                return result.get();
            } catch (CancellationException e) {// 如果被取消，需要从cache中取消
                this.cache.remove(arg, result);
            } catch (ExecutionException e) {
                return null;
            }
        }

    }

}