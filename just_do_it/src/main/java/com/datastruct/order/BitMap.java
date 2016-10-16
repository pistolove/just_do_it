package com.datastruct.order;

/**
 * 排序，用于数组特别大，不能一次加载到内存时，转为bit来排序
 * @author chenjian
 */
public class BitMap {

    public static void main(String[] args) {
        int[] a = { 0, 1, 2, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9,
                8, 7, 6, 5, 4, 3, 34, 55, 60, 53, 57 };
        sort(a, 60 / 32 + 1);
    }

    public static void sort(int[] a, int length) {
        int[] bitMap = new int[length];

        for (int temp : a) {
            int index = temp / 32;
            temp = 1 << (temp % 32);
            bitMap[index] = bitMap[index] | temp;
        }

        for (int i = 0; i < length; i++) {
            int temp = bitMap[i];
            for (int j = 0; j < 32; j++) {
                int num = temp & 1;
                if (num != 0) {
                    System.out.print(i * 32 + j + " ");
                }
                temp = temp >> 1;
            }
        }
    }
}
