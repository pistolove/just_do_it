package com.servermanage.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class RegisterServerClient {

    public static void main(String[] args) throws Exception {
        new Server("10.182.192.45", 10 * 1000).start();
        new Server("10.154.156.205", 20 * 1000).start();
        new Server("10.111.222.111", 30 * 1000).start();
        // zk.create("/root/zktest",
        // InetAddress.getLocalHost().getHostAddress().getBytes(),
        // Ids.OPEN_ACL_UNSAFE,
        // CreateMode.PERSISTENT);

        // System.out.print(new String(zk.getData("/root", watcher, null)));
        // 创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
        /*zk.exists("/root", true);
        zk.create("/root", "mydata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("---------------------");

        // 在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
        zk.exists("/root/childone", true);
        zk.create("/root/childone", "childone".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("---------------------");

        // 删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
        zk.exists("/root/childone", true);
        zk.delete("/root/childone", -1);
        System.out.println("---------------------");
        zk.exists("/root", true);
        zk.delete("/root", -1);
        System.out.println("---------------------");

        // 关闭session
        zk.close();*/

    }

    public static class Server extends Thread {
        private static String path = "/root/zktest";
        private static String port = "1070";
        private static String hosts = "10.182.192.45:2181,10.100.54.154:2181,10.100.54.155:2181";

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
                ZooKeeper zk = new ZooKeeper(hosts, 300000, null);

                if (zk.exists(path, null) == null) {
                    zk.create(path, "".getBytes(), null, CreateMode.PERSISTENT);
                }
                String addIp = this.ip + ":" + port + ";";
                zk.create(path + "/" + addIp, addIp.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

                Thread.currentThread().sleep(this.time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
