package com.servermanage.zookeeper.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.util.CollectionUtils;

import com.servermanage.zookeeper.ZooKeeperUtil;

public class DistributedLocks implements Lock {
    private ZooKeeper zooKeeper;
    private String lockPath;// /letv/lock 形式，无最后一个/
    private String zkServer;// zk地址

    private Thread thread;// 等待锁的当前线程，暂不考虑并发，默认单线程
    private String waitNodePath;// 锁创建的node path

    public DistributedLocks(String lockPath, String zkServer) {
        this.lockPath = lockPath;
        this.zkServer = zkServer;
    }

    private void init() throws Exception {
        final CountDownLatch connectedSemaphore = new CountDownLatch(1);
        this.zooKeeper = new ZooKeeper(this.zkServer, 300000, new Watcher() {
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

        // 如果以 / 结尾，去掉 /
        if (this.lockPath.lastIndexOf("/") == this.lockPath.length() - 1) {
            this.lockPath = this.lockPath.substring(0, this.lockPath.length() - 1);
        }
        ZooKeeperUtil.checkPath(this.zooKeeper, this.lockPath);
        this.waitNodePath = this.zooKeeper.create(this.lockPath + "/", "".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public void lock() {
        try {
            this.init();

            if (this.tryGetLock()) {
                System.out.println(Thread.currentThread() + "第一次直接拿到锁了");
                return;
            } else {
                Integer nextOrder = Integer.valueOf(this.waitNodePath.substring(this.waitNodePath.lastIndexOf("/") + 1,
                        this.waitNodePath.length())) - 1;// 需要监控的节点号
                StringBuilder nextOrderStr = new StringBuilder();
                // 序号是0000000001 这种 ，所以补充为10个即可
                for (int i = 0; i < 10 - String.valueOf(nextOrder).length(); i++) {
                    nextOrderStr.append("0");
                }
                nextOrderStr.append(nextOrder);
                // 需要监控的前一节点的path
                String watcherNodePath = this.lockPath + "/" + nextOrderStr.toString();

                this.thread = Thread.currentThread();
                Watcher watcher = new Watcher() {
                    public void process(WatchedEvent event) {
                        LockSupport.unpark(DistributedLocks.this.thread);
                    }
                };
                this.zooKeeper.exists(watcherNodePath, watcher);

                // 再次检查，防止前一节点 在监控前已经删除
                if (this.tryGetLock()) {
                    return;
                }

                LockSupport.park(1000 * 30L);
                System.out.println(Thread.currentThread() + "被唤醒了，拿到锁，执行业务");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean tryGetLock() throws Exception {
        List<String> nodes = this.zooKeeper.getChildren(this.lockPath, null);
        if (!CollectionUtils.isEmpty(nodes)) {
            Collections.sort(nodes);
            // 判断当前等待节点是否为最小节点，是即拿到锁
            if (this.waitNodePath.contains(nodes.get(0))) {
                System.out.println(Thread.currentThread() + "可以拿到锁了");
                return true;
            }
        }
        return false;

    }

    public void unlock() {
        try {
            this.zooKeeper.delete(this.waitNodePath, -1);
            this.zooKeeper.close();
            this.zooKeeper = null;
            this.waitNodePath = null;
            this.thread = null;
            System.out.println(Thread.currentThread() + "释放锁");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void lockInterruptibly() throws InterruptedException {
    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public Condition newCondition() {
        return null;
    }

}
