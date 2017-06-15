package com.java.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class IoTest {

    public static void test() {
        InputStream fileInput = null;
        FileOutputStream fileOutput = null;

        InputStream fileInput2 = null;
        FileOutputStream fileOutput2 = null;
        BufferedInputStream bufferInput = null;
        BufferedOutputStream bufferOutput = null;
        byte[] data = new byte[1024];
        try {
            fileInput = new FileInputStream("/Users/chenjian/myfile/work/server-confs/测试-重构后/jdbc2.properties");
            fileOutput = new FileOutputStream("/Users/chenjian/myfile/work/server-confs/测试-重构后/jdbc3.properties");
            long start = System.currentTimeMillis();
            while (fileInput.read(data) != -1) {
                fileOutput.write(data);
            }
            fileOutput.flush();
            long end = System.currentTimeMillis();
            System.out.print("file花费 :" + (end - start));

            fileInput2 = new FileInputStream("/Users/chenjian/myfile/work/server-confs/测试-重构后/jdbc2.properties");
            fileOutput2 = new FileOutputStream("/Users/chenjian/myfile/work/server-confs/测试-重构后/jdbc3.properties");
            bufferInput = new BufferedInputStream(fileInput2);
            bufferOutput = new BufferedOutputStream(fileOutput2);
            start = System.currentTimeMillis();
            while (bufferInput.read(data) != -1) {
                bufferOutput.write(data);
            }
            end = System.currentTimeMillis();
            System.out.println("   buffer花费 :" + (end - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInput.close();
            } catch (Exception e) {
            }
            try {
                fileOutput.close();
            } catch (Exception e) {
            }
            try {
                fileInput2.close();
            } catch (Exception e) {
            }
            try {
                fileOutput2.close();
            } catch (Exception e) {
            }
            try {
                bufferInput.close();
            } catch (Exception e) {
            }
            try {
                bufferOutput.close();
            } catch (Exception e) {
            }

        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            test();
        }
    }
}
