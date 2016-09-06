package com.datastruct.order;

/**
 * 希尔排序
 * @author chenjian
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] m = { 2, 8, 5, 7, 3, 4, 9, 6, 10, 1 };
        shellSort(m, 10);
    }

    /*
     * 循环所有间隔 ，一般以n/2  n/4  n/8 直到1为止（为1则为直接排序）
     */
    private static void shellSort(int s[], int length) {
        for (int i = length / 2; i > 0; i = i / 2) {
            insertSort(s, length, i);
        }
    }

    /*
     * dk 间隔
     * 作用：在这一行，将所有间隔为dk的序列排列好
     */
    private static void insertSort(int s[], int length, int dk) {
        for (int i = 0; i < dk; i++) {
            for (int j = i; j < length; j = j + dk) {
                int temp = s[j];
                while (j - dk >= 0 && s[j - dk] > temp) {
                    s[j] = s[j - dk];
                    j = j - dk;
                }
                s[j] = temp;
            }
        }
        sys(s);
    }

    private static void sys(int q[]) {
        for (int i : q) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }
}
