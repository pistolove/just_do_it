package com.concurrent.pool;

public class UnsafeTest {

    private int num = 1;
    private String aaa;
    @SuppressWarnings("restriction")
    public static sun.misc.Unsafe UNSAFE;
    static {
        try {
            java.lang.reflect.Field theUnsafe = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (sun.misc.Unsafe) theUnsafe.get(null);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws NoSuchFieldException, SecurityException {
        UnsafeTest dd = new UnsafeTest();
        // 获取字段偏移量
        System.out.println(UNSAFE.objectFieldOffset(UnsafeTest.class.getDeclaredField("aaa")));
        // 分配内存
        long address = UNSAFE.allocateMemory(100L);
        UNSAFE.putAddress(address, 123123123123123123L);
        System.out.println(UNSAFE.getLong(address));
        System.out.println(UNSAFE.getAddress(address));
        System.out.println(UNSAFE.getInt(address));

        System.out.println(UNSAFE.arrayIndexScale(long[].class));
        System.out.println(UNSAFE.arrayBaseOffset(long[].class));
        System.out.println(UNSAFE.addressSize());

        System.out.println(UNSAFE.staticFieldOffset(UnsafeTest.class.getDeclaredField("UNSAFE")));
    }
}
