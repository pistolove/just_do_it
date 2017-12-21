package com.java.shift;

public class shift2or3test {
    /**
     * 1) << >>通用规律， i << n 最终只会位移 n%32(这里i为int 32位)位数，所以n=32,64时，值不变！！！
     *
     * 2) << 对正负数效果都一样，符号位会被覆盖，低位补0，所以没有<<<这个符号！！！！！
     *
     * 3) >> 对于正数和 >>> 是相同的效果，高位补0 ，但 >> 对负数，符号位会补1
     *
     * 4) >>>是无符号位，高位用0补!!! 没有符号位的概念
     * 
     * @param args
     */
    public static void main(String[] args) {
        int i = 1;
        System.out.println("位移n超位位数时test，例如int32位，位移33位时的计算");
        System.out.println(Integer.toBinaryString(1) + " >> << 操作后的结果");
        System.out.println(Integer.toBinaryString(1 >> 32));// 可得出 i << 32 实际操作是 i << 0的结论，负数一样
        System.out.println(Integer.toBinaryString(1 << 33));// 可得出 i << 33 实际操作是 i << 1的结论，负数一样
        System.out.println();

        System.out.println("负数位移时的高低位变化");
        i = -1 << 31;
        System.out.println(Integer.toBinaryString(i) + " >> <<操作后的结果");
        i = i >> 4;
        System.out.println(Integer.toBinaryString(i));// 高位用1补了
        i = i << 2;
        System.out.println(Integer.toBinaryString(i));
        System.out.println();

        System.out.println("正负数 左移时符号位的变化");
        i = 1;
        System.out.println(Integer.toBinaryString(i << 31));// 说明左移的时候 符号位没有用，直接覆盖
        i = -2;
        System.out.println(Integer.toBinaryString(i));
        i = -2 << 31;
        System.out.println(Integer.toBinaryString(i));
        System.out.println();

        long j = 232313;
        System.out.println(Long.toBinaryString(j));
        System.out.println(Long.toBinaryString(j << 128));
        System.out.println(Long.toBinaryString(j >> 64));
        j = -1231231231231L;
        System.out.println(Long.toBinaryString(j));
        System.out.println(Long.toBinaryString(j << 64));
        System.out.println(Long.toBinaryString(j >> 64));
    }
}
