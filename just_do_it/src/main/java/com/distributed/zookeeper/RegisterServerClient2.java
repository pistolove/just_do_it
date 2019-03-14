package com.distributed.zookeeper;

public class RegisterServerClient2 {

    public static void main(String[] args) throws Exception {
        new RegisterServerClient.Server("10.132.443.123", 10 * 1000).start();
    }
}
