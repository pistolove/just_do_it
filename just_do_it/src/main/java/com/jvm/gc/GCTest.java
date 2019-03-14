package com.jvm.gc;

import com.google.common.collect.Lists;
import java.util.List;

public class GCTest {

    public static void main(String[] args) throws InterruptedException {

        List<Byte[]> list = Lists.newLinkedList();
        for (int i = 0; i < 10000; i++) {
            Byte[] tmp = new Byte[1 * 1024 * 1024];
            list.add(tmp);
        }
        Thread.sleep(1000L * 1000);
    }
}
