package algorim.sort;

public class ShellSort {

    public static void main(String[] args) {

        int[] t = new int[]{2,1,5,4,88,54,333,564,12,45,23};
        shellSort(t);
        System.err.println();
    }

    public static void shellSort(int[] table) {
        if (table == null)
            return;
        int len = table.length;
        for (int delta = len / 2; delta > 0; delta /= 2) {
            for (int j = delta; j < len; j++) {
                int temp = table[j];
                int m = j - delta;
                while (m >= 0 && table[m] > temp) {
                    table[m + delta] = table[m];
                    m = m - delta;
                }
                table[m + delta] = temp;
            }
        }
    }

}
