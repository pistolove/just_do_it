package com.datastruct.order;

/**
 * 冒泡排序
 * @author chenjian
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] test = { 15, 4, 5, 2, 34, 7, 4, 12 };
        sort(test, 8);
        for (int a : test) {
            System.out.print(a + " ");
        }
    }

    private static void sort(int a[], int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

}
