package com.datastruct.order;

/**
 * 快速排序
 * @author chenjian
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] q = { 2, 8, 5, 7, 3, 4, 9, 6, 10, 1 };
        quickSort(q, 0, q.length - 1);
    }

    /*
     * q[] 原始数组
     * low 序列的起始位置
     * high 序列的结束位置
     * 作用：迭代使从low-high序列有序
     */
    private static void quickSort(int q[], int low, int high) {
        if (low < high) {
            int index = partition(q, low, high);
            quickSort(q, low, index - 1);
            quickSort(q, index + 1, high);
        }
    }

    /*
     * q[] 原始数组
     * low 序列的起始位置
     * high 序列的结束位置
     * 作用：1.确定 low 元素的位置 2.返回 其index
     */
    private static int partition(int q[], int low, int high) {
        int firstValue = q[low];// 将low 作为目标元素
        while (low < high) {
            // 高位遍历
            while (firstValue > q[high] && low < high) {
                high--;
            }
            q[low] = q[high];
            // 低位遍历
            while (q[low] > firstValue && low < high) {
                low++;
            }
            q[high] = q[low];
        }
        q[low] = firstValue; // 确定位置后 赋值
        sys(q);
        return low;
    }

    private static void sys(int q[]) {
        for (int i : q) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }

}
