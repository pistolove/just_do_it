package com.distributed.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

public class RegisterServerClient {

    public static void main(String[] args) throws Exception {
        new Server("1111111111", 10 * 1000).start();
        new Server("2222222222", 20 * 1000).start();
        new Server("3333333333", 30 * 1000).start();
    }

    public static class Server extends Thread {

        private static String path = "/root/zktest";
        private static String port = "1070";
        private static String zkhosts = "10.182.192.45:2181,10.100.54.154:2181,10.100.54.155:2181";

        private static Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("回调watcher实例： 路径" + event.getPath() + " 类型：" + event.getType());
            }
        };
        private String ip;
        private long time;

        public Server(String ip, long time) {
            this.ip = ip;
            this.time = time;
        }

        @Override
        public void run() {
            try {
                ZooKeeper zk = new ZooKeeper(zkhosts, 300000, null);

                ZooKeeperUtil.checkPath(zk, path);

                String addIp = this.ip + ":" + port + ";";
                String result = zk.create(path + "/", addIp.getBytes(), Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
                System.out.println("创建服务:" + result);

                Thread.currentThread().sleep(this.time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
