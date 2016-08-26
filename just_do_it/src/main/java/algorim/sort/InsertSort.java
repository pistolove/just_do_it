package algorim.sort;

import org.junit.Test;

public class InsertSort {
    public static void main(String[] args) {
        int[] arr_10000 = getArr(10000);
        insertSort(arr_10000);

        int[] arr_100000 = getArr(100000);
        insertSort(arr_100000);

        int[] arr_1000000 = getArr(1000000);
        insertSort(arr_1000000);

        int[] arr_10000000 = getArr(10000000);
        insertSort(arr_10000000);

        int[] arr_100000000 = getArr(100000000);
        insertSort(arr_100000000);

        int[] arr_200000000 = getArr(200000000);
        insertSort(arr_200000000);
    }

    public static int[] getArr(int len) {
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * len);
        }
        return nums;
    }

    public static int[] insertSort(int[] nums) {
        int len = nums.length;
        Long t1 = System.currentTimeMillis();
        for (int i = 1; i < len; i++) {
            int vot = nums[i], j;
            for (j = i - 1; j > -1 && nums[j] > vot; j--) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = vot;
        }

        System.out.println("insertSort_" + nums.length + "个数排序时间：" + (System.currentTimeMillis() - t1));
        return nums;
    }

    @Test
    public void testInsertSort() {
        int[] bubbleSort = insertSort(new int[] { 3, 2, 5, 1, 99, 34, 56, 7, 12, 56, 24 });
        System.err.println(bubbleSort);
    }
}
