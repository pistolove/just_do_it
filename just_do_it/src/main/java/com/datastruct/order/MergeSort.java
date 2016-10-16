package com.datastruct.order;

/**
 * 归并排序
 * @author chenjian
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] m = { 2, 8, 5, 7, 3, 4, 9, 6, 10, 1 };
        mergeSort(m);

    }

    /*
     * 从gap=1 开始合并
     */
    private static void mergeSort(int m[]) {
        for (int gap = 1; gap < m.length; gap = 2 * gap) {
            mergePass(m, gap, m.length);
        }
    }

    /*
     * gap 要合并的序列长度
     * length 数组长度
     * 作用：1.将同一层的序列进行合并 2.将单独剩的gap单独排序，若不足一个gap，则忽略
     *
     */
    private static void mergePass(int m[], int gap, int length) {
        int i = 0;
        // 两两gap进行合并
        for (i = 0; i + 2 * gap - 1 < length; i = i + 2 * gap) {
            merge(m, i, i + gap - 1, i + gap * 2 - 1);
        }
        // 不够两gap的特殊处理，少于一个gap的不用处理，因为之前的递归已经排好了
        if (i + gap - 1 < length) {
            merge(m, i, i + gap - 1, length - 1);
        }
    }

    /*
     * low 为第一序列 首index
     * mid 为第一序列 末index
     * high为第二序列 末index
     * 将low-mid   mid+1-high合为一段有序序列
     */
    private static void merge(int m[], int low, int mid, int high) {
        int i = low;
        int j = mid + 1;
        int k = 0;// 临时数组index
        int[] temp = new int[high - low + 1];
        // 比较两个序列每个元素并复制到临时数组
        while (i < mid + 1 && j < high + 1) {
            if (m[i] < m[j]) {
                temp[k++] = m[i++];
            } else {
                temp[k++] = m[j++];
            }
        }
        // 将两个序列中剩下的元素复制过去
        while (i < mid + 1) {
            temp[k++] = m[i++];
        }
        while (j < high + 1) {
            temp[k++] = m[j++];
        }
        // 临时数组复制回初始数组
        for (i = low, k = 0; i < high + 1; i++, k++) {
            m[i] = temp[k];
        }
        sys(m);
    }

    private static void sys(int q[]) {
        for (int i : q) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }
}
