package com.servermanage.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class test {

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("10.182.192.45:2181,10.100.54.154:2181,10.100.54.155:2181", 300000, null);
        zookeeper.create("/letv/chenjian", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zookeeper.close();
    }
}
