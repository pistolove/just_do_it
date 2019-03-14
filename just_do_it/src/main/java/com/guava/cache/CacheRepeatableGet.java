package com.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;

public class CacheRepeatableGet {

  private static LoadingCache<String, String> loadingCache =
      CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
        @Override public String load(String key) throws Exception {
          Thread.sleep(1000 * 5);
          System.out.println(Thread.currentThread().getName() + " load " + key);
          return "222";
        }
      });

  public static void main(String[] args) {
    new GetThread(loadingCache).start();
    new GetThread(loadingCache).start();
    try {
      Thread.sleep(1000 * 60);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class GetThread extends Thread {

    private LoadingCache loadingCache;

    public GetThread(LoadingCache loadingCache) {
      this.loadingCache = loadingCache;
    }

    public void run() {

      try {
        System.out.println(Thread.currentThread().getName() + " get " + this.loadingCache.get("1"));
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
  }
}
