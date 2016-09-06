package com.datastruct.order;

/**
 * 给一组数，求输出非重复数字
 * 思路：无法一次加载到内存中时，将其挨个读入并转为bit数组，然后遍历bit数组即可，也可以将数组改为多个文件，每个中保存一个int数字
 * @author chenjian
 */
public class TwoBitMap {

    public static void main(String[] args) {
        int[] a = { 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6,
                5, 4, 3, 2, 1, 0, 34, 55, 33, 53, 57, 57, 31 };
        sort(a, 57 / 16 + 1);

    }

    public static void sort(int[] a, int length) {
        int[] bitMap = new int[length];

        for (int temp : a) {
            int bitMapIndex = temp / 16;// 两bit表示一个数
            int bitMapValue = bitMap[bitMapIndex];

            int residue = temp % 16;// 余数
            bitMapValue = (bitMapValue >> (residue * 2)) & 3;// 将其他们置 0

            // 没出现过 或出现 一次
            if (bitMapValue == 0 || bitMapValue == 1) {
                int numT = 1 << (residue * 2);
                bitMap[bitMapIndex] = bitMap[bitMapIndex] + numT;
            }
        }

        for (int i = 0; i < length; i++) {
            int temp = bitMap[i];
            for (int j = 0; j < 16; j++) {
                int num = temp & 3;// 出现次数
                if (num == 1) {
                    System.out.print(i * 16 + j + " ");
                }
                temp = temp >> 2;
            }
        }
    }
}
