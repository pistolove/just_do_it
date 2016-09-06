package com.datastruct.search;


/**
 * 二分查找
 * @author chenjian
 */
public class BinarySearch {
    public static void main(String args[]) {
        int b[] = { 1, 2, 3, 4, 7, 8, 9, 21, 24, 53, 66, 88 };
        for (int temp : b) {
            int goal = binarySearch(b, 0, b.length - 1, temp);
            // System.out.println(goal);
        }
        binarySearch2(b, 0, b.length - 1, 88);
        System.out.println(binarySearchLow(b, 0, b.length - 1, 5));
        System.out.println(binarySearchHigh(b, 0, b.length - 1, 7));

    }

    /**
     * 递归二分
     * @param b
     *            从小到大有序序列
     * @param start
     *            起始index
     * @param end
     *            结束index
     * @param goal
     *            目标值
     * @return
     */
    private static int binarySearch(int b[], int start, int end, int goal) {
        if (start <= end) {
            int mid = (start + end) / 2; // 这里会向下取值，例如7 + 8 /2 =7
            if (b[mid] == goal) {
                return mid;
            } else if (b[mid] > goal) {
                // 递归 start-mid mid是向下取值，所以需要放在左序列！
                return binarySearch(b, start, mid - 1, goal);
            } else {
                // 递归 mid+1 - end mid是向下取值，所以右边序列需要 mid+1
                return binarySearch(b, mid + 1, end, goal);
            }
        }
        return -1;
    }

    /**
     * 非递归方式
     * @param b
     *            从小到大有序序列
     * @param start
     *            起始index
     * @param end
     *            结束index
     * @param goal
     *            目标值
     * @return
     */
    private static int binarySearch2(int b[], int start, int end, int goal) {
        while (start <= end) {
            int mid = (start + end) / 2;
            if (b[mid] == goal) {
                return mid;
            } else if (b[mid] > goal) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 查找goal下届
     * @param b
     * @param start
     * @param end
     * @param goal
     * @return 返回表示第一个小于goal的元素index值
     */
    private static int binarySearchLow(int b[], int start, int end, int goal) {
        int mid = (start + end + 1) / 2;
        while (start < end) {
            // 下届意味着必须小于goal，所以b[mid]小于goal时，b[mid]满足条件，start=mid即可
            // 但是b[mid]大于等于goal值时，该值肯定不满下届要求，直接通过index-1忽略（也是防止死循环）
            if (b[mid] < goal) {
                start = mid;
            } else {
                end = mid - 1;
            }
            mid = (start + end + 1) / 2;

        }
        return mid;
    }

    /**
     * 查找goal上届
     * @param b
     * @param start
     * @param end
     * @param goal
     * @return 返回表示第一个大于goal的元素index值
     */
    private static int binarySearchHigh(int b[], int start, int end, int goal) {
        int mid = (start + end) / 2;
        while (start < end) {
            // 上届意味着必须大于goal，所以b[mid]大于goal时，满足要求，end移到mid即可
            // 但当b[mid]小于等于goal时，直接index+1略过
            if (b[mid] > goal) {
                end = mid;
            } else {
                start = mid + 1;//
            }
            mid = (start + end) / 2;
        }
        return mid;
    }
}
