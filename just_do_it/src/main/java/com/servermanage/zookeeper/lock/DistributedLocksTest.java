package com.servermanage.zookeeper.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.util.StringUtils;

import com.servermanage.zookeeper.ZooKeeperUtil;

public class DistributedLocksTest {

    private static final String zkServer = "10.182.192.45:2181,10.100.54.154:2181,10.100.54.155:2181";
    private static final String lockPath = "/letv/chenjian/lock";// /letv/chenjian/lock
    private static final String lockCalPath = "/letv/chenjian/cal";// /letv/chenjian/lock

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10000);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MILLISECONDS, queue);
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(new DistributedLocksThread(new DistributedLocks(lockPath, zkServer)));
        }

        threadPoolExecutor.shutdown();

    }

    static class DistributedLocksThread extends Thread {

        private DistributedLocks distributedLocks;

        public DistributedLocksThread(DistributedLocks distributedLocks) {
            this.distributedLocks = distributedLocks;
        }

        @Override
        public void run() {
            this.distributedLocks.lock();
            try {
                final CountDownLatch connectedSemaphore = new CountDownLatch(1);
                ZooKeeper zookeeper = new ZooKeeper(zkServer, 300000, new Watcher() {
                    public void process(WatchedEvent event) {
                        // 获取事件的状态
                        KeeperState keeperState = event.getState();
                        // 获取事件的类型
                        EventType eventType = event.getType();
                        // 如果是建立连接（SyncConnected表示连接成功的状态）
                        if (KeeperState.SyncConnected == keeperState) {
                            if (EventType.None == eventType) {
                                // 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                                connectedSemaphore.countDown();
                            }
                        }
                    }
                });
                connectedSemaphore.await();

                ZooKeeperUtil.checkPath(zookeeper, lockCalPath);

                String data = new String(zookeeper.getData(lockCalPath, null, null));
                if (StringUtils.isEmpty(data)) {
                    data = "1";
                } else {
                    data = String.valueOf(Integer.valueOf(data) + 1);
                }
                zookeeper.setData(lockCalPath, data.getBytes(), -1);
                zookeeper.close();

                System.out.println(Thread.currentThread() + " 执行业务完成后data值为:" + data);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.distributedLocks.unlock();
            }

        }
    }
}
