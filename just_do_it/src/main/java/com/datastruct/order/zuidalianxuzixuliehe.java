package com.datastruct.order;

public class zuidalianxuzixuliehe {

    public static void main(String[] args) {
        int[] a = { 1, -20, 0, 1, 1, 1, -4, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 20 };
        sort(a);
    }

    public static void sort(int[] a) {
        int max = 0;
        int temp = 0;
        for (int i = 0; i < a.length; i++) {
            if ((temp = temp + a[i]) < 0) {
                temp = 0;
            }
            if (max < temp) {
                max = temp;
            }
        }
        System.out.println(max);
    }

}
