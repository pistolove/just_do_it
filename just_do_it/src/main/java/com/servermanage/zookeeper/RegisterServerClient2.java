package com.servermanage.zookeeper;

import com.servermanage.zookeeper.RegisterServerClient.Server;

public class RegisterServerClient2 {

    public static void main(String[] args) throws Exception {
        new Server("10.132.443.123", 10 * 1000).start();
    }
}
