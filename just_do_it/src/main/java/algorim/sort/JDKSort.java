package algorim.sort;

import java.util.Arrays;

public class JDKSort {
    public static void main(String[] args) {
        int[] arr_10000 = getArr(10000);
        jdkSort(arr_10000);

        int[] arr_100000 = getArr(100000);
        jdkSort(arr_100000);

        int[] arr_1000000 = getArr(1000000);
        jdkSort(arr_1000000);

        int[] arr_10000000 = getArr(10000000);
        jdkSort(arr_10000000);

        int[] arr_100000000 = getArr(100000000);
        jdkSort(arr_100000000);

        int[] arr_1000000000 = getArr(200000000);
        jdkSort(arr_1000000000);
    }

    public static int[] getArr(int len) {
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * len);
        }
        return nums;
    }

    public static int[] jdkSort(int[] nums) {
        Long t1 = System.currentTimeMillis();
        Arrays.sort(nums);
        System.out.println("JDKSort_" + nums.length + "个数排序时间：" + (System.currentTimeMillis() - t1));
        return nums;
    }
}
