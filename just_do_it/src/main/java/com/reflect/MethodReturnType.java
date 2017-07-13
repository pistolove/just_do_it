package com.reflect;

import java.lang.reflect.Method;

import org.apache.thrift.protocol.TCompactProtocol;

public class MethodReturnType {

    public static void main(String[] args) {
        s(new MethodReturnType().new Test());
        System.out.print(~0x7F);
        TCompactProtocol tc = new TCompactProtocol(null);
        try {
            tc.writeI32(1 << 9);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void s(Object o) {
        if (o != null) {
            Method[] methods = o.getClass().getMethods();
            if (methods != null && methods.length > 0) {
                for (Method method : methods) {
                    System.out.println(method.getName() + " 返回值是： " + method.getReturnType());
                }
            }
        }
    }

    class Test {

        public int a() {
            return 1;
        }

        public Integer b() {
            return 1;
        }

        public void c() {

        }

    }
}
