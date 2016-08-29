package algorim.search;

public class BinarySearch {

    public static void main(String[] args) {
        System.err.println(binarySearch(new int[] { 1, 2, 3, 4, 5, 6, 7, 22, 33, 77, 99, 100 }, 22));
    }

    public static boolean binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0)
            return false;
        int high = arr.length - 1;
        int low = 0;
        int mid;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] > target) {
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            }
        }
        return false;
    }
}
