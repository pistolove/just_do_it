package com.servermanage.zookeeper;

import java.util.Collections;
import java.util.List;

import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.util.CollectionUtils;

public class UserClient {
    private static String path = "/root/zktest";

    private static Watcher watcher = new Watcher() {
        public void process(WatchedEvent event) {
            System.out.println("回调watcher实例： 路径" + event.getPath() + " 类型：" + event.getType());
        }
    };

    public static void main(String[] args) throws Exception {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

        // CuratorFramework curatorFramework = builder
        // .connectString("10.182.192.45:2181,10.100.54.154:2181,10.100.54.155:2181").sessionTimeoutMs(300000)
        // .connectionTimeoutMs(30000).canBeReadOnly(true).namespace(path)
        // .retryPolicy(new ExponentialBackoffRetry(1000,
        // Integer.MAX_VALUE)).defaultData(null).build();

        ZooKeeper zk = new ZooKeeper("10.182.192.45:2181,10.100.54.154:2181,10.100.54.155:2181", 300000, watcher);

        while (true) {
            try {
                List<String> childPath = zk.getChildren(path, null);
                Collections.sort(childPath);
                if (!CollectionUtils.isEmpty(childPath)) {
                    for (int i = 0; i < childPath.size(); i++) {
                        String nodeData = new String(zk.getData(path + "/" + childPath.get(i), watcher, null));
                        System.out.print(childPath.get(i) + " 节点值 " + nodeData + "      ");
                    }
                    System.out.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }
        }

    }
}
