package com.datastruct.order;

public class SelectSort {
    public static void main(String[] args) {
        int[] test = { 15, 4, 5, 2, 34, 7, 4, 12 };
        sort(test, 8);
        for (int a : test) {
            System.out.print(a + " ");
        }
    }

    private static void sort(int a[], int n) {
        for (int i = 0; i < n; i++) {
            int min = a[i];
            int minIndex = i;
            for (int j = i; j < n; j++) {
                if (a[j] < min) {
                    min = a[j];
                    minIndex = j;
                }
            }
            a[minIndex] = a[i];
            a[i] = min;
        }
    }

}
