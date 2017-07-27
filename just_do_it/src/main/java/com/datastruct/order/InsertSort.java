package com.datastruct.order;

/**
 * 插入排序
 * @author chenjian
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] test = { 15, 4, 5, 2, 34, 7, 4, 12 };
        sort(test, 8);
        for (int a : test) {
            System.out.print(a + " ");
        }
    }

    private static void sort2(int a[]) {
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j = i;
            while (j - 1 >= 0 && a[j - 1] > temp) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = temp;
        }
    }

    private static void sort(int a[], int n) {
        for (int i = 1; i < n; i++) {
            int temp = a[i];// 复制为哨兵，即存储待排序元素
            int j = i;
            while (j - 1 >= 0 && a[j - 1] > temp) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = temp;
        }
    }
}
