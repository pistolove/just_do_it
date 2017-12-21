package com.reflect;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;
import com.util.PrintHelp;

public class ListInterAddstring {

    public static void main(String[] args) {
        List<Integer> list = Lists.newLinkedList();
        list.add(123);

        try {
            Method method2 = list.getClass().getMethod("add", Object.class);
            method2.invoke(list, "i add a string in Integer list");
            // PrintHelp.printList(list);

            for (Method method : list.getClass().getMethods()) {
                if (method.getName().equals("add") && method.getParameterTypes()[0].equals(Object.class)) {
                    method.invoke(list, "i add a string in Integer list");
                }
            }

            PrintHelp.printList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
