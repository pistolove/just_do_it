package com.java.collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put(null, null);
        // System.out.println(Integer.highestOneBit(1 << 2));

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put("1", "1");
        linkedHashMap.put("2", "1");
        linkedHashMap.put("3", "1");
        linkedHashMap.put("4", "1");
        linkedHashMap.put("5", "1");
        linkedHashMap.put("6", "1");
        linkedHashMap.put("7", "1");
        linkedHashMap.put("8", "1");
        linkedHashMap.put("9", "1");
        Iterator<String> it = linkedHashMap.keySet().iterator();
        // while (it.hasNext()) {
        // System.out.println(it.next());
        // }

        /**
         * Integer s = 1111111;
         * char[] temp = s.toString().toCharArray();
         * Byte[] nums = new Byte[temp.length];
         * for (int i = 0; i < temp.length; i++) {
         * nums[i] = Byte.valueOf(String.valueOf(temp[i]));
         * }
         * Byte[][] results = new Byte[nums.length][10];
         * for (int i = 0; i < nums.length; i++) {
         * for (int j = 0; j <= nums[i]; j++) {
         * results[i][j] = (byte) j;
         * }
         * }
         * for (int i = 0; i < results.length; i++) {
         * for (int j = 0; j < 10; j++) {
         * if (j == 0 && results[i][j] == 0) {
         * continue;
         * }
         * byte first = results[i][j];
         * System.out.println(first);
         * for (int k = i + 1; k < results.length; k++) {
         * }
         * }
         * }
         **/
        output(23);
    }

    public static void output(Integer num) {
        List<Integer> list = new LinkedList<Integer>();
        for (Integer i = num; i > 0; i--) {
            list.add(i);
        }
        Collections.sort(list, new Comparator<Integer>() {
            public int compare(Integer int1, Integer int2) {
                char[] o1 = int1.toString().toCharArray();
                char[] o2 = int2.toString().toCharArray();
                int size = o1.length < o2.length ? o1.length : o2.length;
                for (int i = 0; i < size; i++) {
                    if (o1[i] == o2[i]) {
                        continue;
                    }
                    if (o1[i] < o2[i]) {
                        return -1;
                    }
                    if (o1[i] > o2[i]) {
                        return 1;
                    }
                }
                return o1.length - o2.length;
            }
        });

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }

}
