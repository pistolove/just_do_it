package com.util;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class PrintHelp {

    public static void printList(List list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (Object o : list) {
                System.out.println(o);
            }
        }
    }
}
