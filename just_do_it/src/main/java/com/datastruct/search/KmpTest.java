package com.datastruct.search;

public class KmpTest {

    public int ifExist(String mainString, String sonString) {
        int existCount = 0;
        char[] mainList = mainString.toCharArray();
        char[] sonList = sonString.toCharArray();

        int[] next = this.getNextList(sonList);

        int i = 0;
        int j = 0;
        while (i < mainList.length && j < sonList.length) {
            if (mainList[i] == sonList[j]) {
                if (j == sonList.length - 1) {
                    // 位移位置 = 上标 - next值。由于 这里j是数组下标，需要+1 转为 123456这种 而不是012345
                    int weiyi = j + 1 - next[j];
                    i = i + weiyi;
                    j = 0;
                    existCount++;
                } else {
                    i++;
                    j++;
                    continue;
                }
            } else {
                // 位移位置 = 上标 - next值。由于 这里j是数组下标，需要+1 转为 123456这种 而不是012345
                int weiyi = j + 1 - next[j];
                i = i + weiyi;
                j = 0;
            }
        }

        return existCount;

    }

    public static void main(String[] args) {
        System.out.print(new KmpTest().ifExist("cdcsefcsdcdsdfdcd", "cd"));
    }

    public int[] getNextList(char[] string) {
        if (string == null || string.length == 0) {
            return null;
        }

        int[] nextList = new int[string.length];
        // 这里循环 每一位的计算
        for (int i = 0; i < string.length; i++) {
            // 前两位 固定 是0，1 因为 每一位的next值都是看前 i-1位 最大横移匹配幅度+1
            if (i == 0 || i == 1) {
                nextList[i] = i;
            } else {
                int tempNext = 0;
                for (int j = 0; j < i - 1; j++) {

                    boolean tag = true;
                    for (int k = 0; k <= j; k++) {
                        if (string[k] != string[i - 1 - j + k]) {
                            tag = false;
                            break;
                        }
                    }
                    // 如果 全匹配成功了再看要不要替换, j是数组加标 +1转换
                    if (tag) {
                        if (j + 1 > tempNext) {
                            tempNext = j + 1;
                        }
                    }
                }
                nextList[i] = ++tempNext;// 计算出后要加1
            }
        }
        for (int i : nextList) {
            // System.out.print(i);
        }
        return nextList;
    }
}
