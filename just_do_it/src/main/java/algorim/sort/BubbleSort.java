package algorim.sort;

import org.junit.Test;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr_10000 = getArr(10000);
        bubbleSort(arr_10000);

        int[] arr_100000 = getArr(100000);
        bubbleSort(arr_100000);

//        int[] arr_1000000 = getArr(1000000);
//        bubbleSort(arr_1000000);
//
//        int[] arr_10000000 = getArr(10000000);
//        bubbleSort(arr_10000000);
//
//        int[] arr_100000000 = getArr(100000000);
//        bubbleSort(arr_100000000);
//
//        int[] arr_200000000 = getArr(200000000);
//        bubbleSort(arr_200000000);
    }

    public static int[] getArr(int len) {
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * len);
        }
        return nums;
    }

    public static int[] bubbleSort(int[] nums) {
        int len = nums.length;
        Long t1 = System.currentTimeMillis();
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < len - 1; j++) {
                if (nums[j] >= nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        System.out.println("bubbleSort_" + nums.length + "个数排序时间：" + (System.currentTimeMillis() - t1));
        return nums;
    }

    @Test
    public void testBubbleSort() {
        int[] bubbleSort = bubbleSort(new int[] { 3, 2, 5, 1, 99, 34, 56, 7, 12, 56, 24 });
        System.err.println(bubbleSort);
    }

}
