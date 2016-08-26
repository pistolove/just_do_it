package algorim.sort;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr_10000 = getArr(10000);
        quickSort(arr_10000);

        int[] arr_100000 = getArr(100000);
        quickSort(arr_100000);

        int[] arr_1000000 = getArr(1000000);
        quickSort(arr_1000000);

         int[] arr_10000000 = getArr(10000000);
         quickSort(arr_10000000);

         int[] arr_100000000 = getArr(100000000);
         quickSort(arr_100000000);
         
         int[] arr_200000000 = getArr(200000000);
         quickSort(arr_200000000);
    }

    public static int[] getArr(int len) {
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * len);
        }
        return nums;
    }

    public static int[] quickSort(int[] nums) {
        Long t1 = System.currentTimeMillis();
        quickSort(nums, 0, nums.length - 1);
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
