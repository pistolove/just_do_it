package com.datastruct.order;

import com.java.test.StringToDouble;

public class test2 {
    public static String a = "11";
    public static final String s = "11" + a;

    public String sss = "1111";

    int i1 = 1;
    int i2 = 1;

    public static void main(String[] args) {
        // System.out.println(test1.s);
        // System.out.println(test1.s == test2.s);
        // System.out.println(test1.s == test2.s.intern());
        System.out.println(b(new StringToDouble().s));
        System.out.println(StringToDouble.s == new test2().sss);
        String s = new String("Ljava/lang/String");
        System.out.println(s == s.intern());

    }

    public static boolean b(String s) {
        return s == "1111";
    }

    public int b2(String s) {
        int i = 1;
        int i2 = 1;
        try {
            i = i + i2;
            return i;
        } catch (Exception e) {
        } finally {
            i = 2;
        }
        return i;
    }

}
