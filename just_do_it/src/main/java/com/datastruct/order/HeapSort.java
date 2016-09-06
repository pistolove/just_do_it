package com.datastruct.order;

/**
 * 堆排序
 * @author chenjian
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] h = { 3, 1, 5, 7, 2, 4, 9, 6, 10, 8 };
        heapSort(h, 10);

        Element[] e = { new Element(2, new Object()), new Element(5, new Object()), new Element(10, new Object()),
                new Element(3, new Object()), new Element(4, new Object()), };
        heapSort(e, e.length);
    }

    /*
     * 1.建初始堆
     * 2.每次建立大顶堆并交换位置
     */
    private static void heapSort(int h[], int length) {
        // 初始堆
        buildHeap(h, length);
        // 堆排序
        for (int i = length - 1; i > 0; i--) {
            // 交换堆顶元素H[0]和堆中最后一个元素
            int temp = h[i];
            h[i] = h[0];
            h[0] = temp;
            // 每次交换堆顶元素和堆中最后一个元素之后，都要对堆进行调整
            heapAdjust(h, 0, i);
        }
    }

    private static void heapSort(Element h[], int length) {
        // 初始堆
        buildHeap(h, length);
        // 堆排序
        for (int i = length - 1; i > 0; --i) {
            // 交换堆顶元素H[0]和堆中最后一个元素
            Element temp = h[i];
            h[i] = h[0];
            h[0] = temp;
            // 每次交换堆顶元素和堆中最后一个元素之后，都要对堆进行调整
            heapAdjust(h, 0, i);
        }
    }

    /**
     * 初始堆进行调整
     * 将H[0..length-1]建成堆
     * 调整完之后第一个元素是序列的最大的元素
     */
    private static void buildHeap(int h[], int length) {
        // 最后一个有孩子的节点的位置 i= (length -1) / 2
        for (int i = (length - 1) / 2; i >= 0; i--) {
            heapAdjust(h, i, length);
        }
    }

    private static void buildHeap(Element h[], int length) {
        // 最后一个有孩子的节点的位置 i= (length -1) / 2
        for (int i = (length - 1) / 2; i >= 0; i--) {
            heapAdjust(h, i, length);
        }
    }

    /**
     * h[] 原始数组
     * rootNodeIndex 需要调整的树根结点
     * length 需要处理的数组长度
     * 原理：
     * 已知H[rootNodeIndex…m]除了H[rootNodeIndex] 外均满足堆的定义
     * 调整H[rootNodeIndex],使其成为大顶堆.即将对第rootNodeIndex个结点为根的子树筛选,
     */
    private static void heapAdjust(int h[], int rootNodeIndex, int length) {
        int rootValue = h[rootNodeIndex];
        int childNodeIndex = 2 * rootNodeIndex + 1; // 左子元素index
        while (childNodeIndex < length) {
            // 挑选值较大的子节点
            if (childNodeIndex + 1 < length && h[childNodeIndex] < h[childNodeIndex + 1]) {
                childNodeIndex++;
            }
            if (rootValue < h[childNodeIndex]) {
                // 将子节点上移
                h[rootNodeIndex] = h[childNodeIndex];
                // 修改以新的孩子节点 作为 根节点
                rootNodeIndex = childNodeIndex;
                childNodeIndex = 2 * rootNodeIndex + 1;
            } else {
                break;
            }
        }
        h[rootNodeIndex] = rootValue;// 将最初的根节点值赋于所在位置
        sys(h);
    }

    private static void heapAdjust(Element h[], int rootNodeIndex, int length) {
        Element rootValue = h[rootNodeIndex];
        int childNodeIndex = 2 * rootNodeIndex + 1; // 左子元素index
        while (childNodeIndex < length) {
            // 挑选值较大的子节点
            if (childNodeIndex + 1 < length && h[childNodeIndex].key < h[childNodeIndex + 1].key) {
                childNodeIndex++;
            }
            if (rootValue.key < h[childNodeIndex].key) {
                // 将子节点上移
                h[rootNodeIndex] = h[childNodeIndex];
                // 修改以新的孩子节点 作为 根节点
                rootNodeIndex = childNodeIndex;
                childNodeIndex = 2 * rootNodeIndex + 1;
            } else {
                break;
            }
        }
        h[rootNodeIndex] = rootValue;// 将最初的根节点值赋于所在位置
        sys(h);
    }

    private static void sys(Element h[]) {
        for (Element i : h) {
            System.out.print(i.key + "=" + i.value + "  ");
        }
        System.out.println();
    }

    static class Element {
        int key;
        Object value;

        public Element(int key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private static void sys(int h[]) {
        for (int i : h) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }

}
