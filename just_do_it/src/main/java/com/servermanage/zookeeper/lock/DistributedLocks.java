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
        this.lock(false, 0);
    }

    public boolean tryLock() {
        return this.lock(true, 0);
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return this.lock(true, time);
    }

    /**
     * 尝试获取锁
     * @param onlyOnce
     *            true 为尝试一次 false为多次尝试
     * @param timeout
     *            ms
     */
    private boolean lock(boolean onlyOnce, long timeout) {
        try {
            this.init();
            while (true) {
                if (this.tryLockOnce()) {
                    return true;
                } else {
                    // 仅尝试一次
                    if (onlyOnce) {
                        return false;
                    }

                    String watcherNodePath = this.lockPath + "/" + this.getPreNode();
                    this.thread = Thread.currentThread();
                    Watcher watcher = new Watcher() {
                        public void process(WatchedEvent event) {
                            LockSupport.unpark(DistributedLocks.this.thread);
                        }
                    };
                    this.zooKeeper.exists(watcherNodePath, watcher);

                    // 再次检查，防止前一节点 在监控前已经删除
                    if (this.tryLockOnce()) {
                        return true;
                    }

                    // 判断阻塞时间
                    if (timeout > 0) {
                        LockSupport.parkNanos(timeout);
                        return false;
                    } else {
                        LockSupport.park();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 尝试获取一次锁
     * @return
     * @throws Exception
     */
    private boolean tryLockOnce() throws Exception {
        if (this.waitNodePath.contains(this.getPreNode())) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前锁的前一节点，没有则返回当前锁节点
     * @return
     */
    private String getPreNode() throws Exception {
        List<String> nodes = this.zooKeeper.getChildren(this.lockPath, null);
        if (!CollectionUtils.isEmpty(nodes)) {
            Collections.sort(nodes);
            for (int i = 0; i < nodes.size(); i++) {
                if (this.waitNodePath.contains(nodes.get(i))) {
                    if (i == 0) { // 下标为0就返回当前节点
                        return nodes.get(i);
                    } else {
                        return nodes.get(i - 1);
                    }
                }
            }
        }
        throw new NullPointerException("zookeeper上无节点存在");
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

    public Condition newCondition() {
        return null;
    }

}
