package algorim.sort;

import java.util.Arrays;

import org.junit.Test;

public class RandomArr {

    public static void main(String[] args) {
        int[] arr_10000 = getArr(10000);
        JDKSort(arr_10000);
        bubbleSort(arr_10000);
        quickSort(arr_10000);
        
        int[] arr_100000 = getArr(100000);
        JDKSort(arr_100000);
        bubbleSort(arr_100000);
        quickSort(arr_100000);

        int[] arr_1000000 = getArr(1000000);
        JDKSort(arr_1000000);
        bubbleSort(arr_1000000);
        quickSort(arr_1000000);

//        int[] arr_10000000 = getArr(10000000);
//        JDKSort(arr_10000000);
//        bubbleSort(arr_10000000);
//        quickSort(arr_10000000);

//        int[] arr_100000000 = getArr(100000000);
//        JDKSort(arr_100000000);
//        bubbleSort(arr_100000000);

    }

    public static int[] getArr(int len) {
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * len);
        }
        return nums;
    }

    public static int[] JDKSort(int[] nums) {
        Long t1 = System.currentTimeMillis();
        Arrays.sort(nums);
        System.out.println("JDKSort_" + nums.length + "个数排序时间：" + (System.currentTimeMillis() - t1));
        return nums;
    }

    public static int[] bubbleSort(int[] nums) {
        int len = nums.length;
        Long t1 = System.currentTimeMillis();
        for (int i = 1; i < len - 1; i++) {
            for (int j = i - 1; j < len - 1; j++) {
                if (nums[i] >= nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        System.out.println("bubbleSort_" + nums.length + "个数排序时间：" + (System.currentTimeMillis() - t1));
        return nums;
    }

    @Test
    public void testQuickSort() {
        int[] nums = new int[] { 5, 2, 8, 2, 11, 9, 88, 45, 66, 111, 2323 };
        int[] quickSort = quickSort(nums);
        System.err.println(Arrays.asList(quickSort));
    }

    public static int[] quickSort(int[] nums) {
        Long t1 = System.currentTimeMillis();
        quickSort(nums, 0, nums.length-1);
        System.out.println("quickSort_" + nums.length + "个数排序时间：" + (System.currentTimeMillis() - t1));
        return nums;
    }

    private static void quickSort(int[] nums, int start, int end) {
        if (start < end) {
            int i = start;
            int j = end;
            int mark = nums[i];
            while (i != j) {
                while (i < j && nums[j] >= mark) {
                    j--;
                }

                if (i < j) {
                    nums[i] = nums[j];
                    i++;
                }

                while (i < j && nums[i] <= mark) {
                    i++;
                }
                if (i < j) {
                    nums[j] = nums[i];
                    j--;
                }
            }
            nums[i] = mark;
            quickSort(nums, start, j - 1);
            quickSort(nums, i + 1, end);
        }
    }
}
