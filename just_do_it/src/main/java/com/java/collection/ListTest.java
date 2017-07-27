package com.java.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTest {
    public static void main(String[] args) throws Exception {

        List<String> list = new ArrayList<String>();// LinkedList
        list.add("s1");
        list.add("s2");
        list.add("bb");
        list.add("bb");
        list.add("s5");
        list.add("s6");
        list.add("s7");
        list.add("s8");
        list.add("s9");
        list.add("s10");
        list.add("s11");

        // new ListThread(list).start();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.equals("bb")) {
                list.remove(s);// 下一个bb会删不掉，忽略
            }
        }

        list.add("s111");

        Iterator<String> i = list.iterator();
        while (i.hasNext()) {
            String s = i.next();
            if (s.equals("s11") || s.equals("s10")) {
                i.remove();
                // remove
            }
        }

    }

    static class ListThread extends Thread {
        List<String> list = null;

        public ListThread(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            this.list.remove(3);
        }
    }

}
