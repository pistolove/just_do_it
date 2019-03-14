package com.distributed.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZooKeeperUtil {

    public static void checkPath(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        // 如果以 / 结尾，去掉 /
        if (path.lastIndexOf("/") == path.length() - 1) {
            path = path.substring(0, path.length() - 1);
        }

        if (zooKeeper.exists(path, null) == null) {
            String tempLockPath = path.substring(1, path.length());
            String[] temp = tempLockPath.split("/");
            String tempPath = "/";
            for (int i = 0; i < temp.length; i++) {
                tempPath = tempPath + temp[i];
                if (zooKeeper.exists(tempPath, null) == null) {
                    zooKeeper.create(tempPath, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                tempPath = tempPath + "/";
            }
        }
    }
}
