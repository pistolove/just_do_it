package com.java.socket;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) throws Exception {
    Socket socket = new Socket("localhost", 8888);
    OutputStream outputStream = socket.getOutputStream();
    while (true) {
      try {
        Scanner sc = new Scanner(System.in);
        String mes = sc.nextLine();
        byte[] reqByte = mes.getBytes();
        byte[] dataSize = new byte[4];
        dataSize[0] = (byte) (reqByte.length >>> 24);
        dataSize[1] = (byte) (reqByte.length >>> 16);
        dataSize[2] = (byte) (reqByte.length >>> 8);
        dataSize[3] = (byte) (reqByte.length);

        outputStream.write(dataSize);
        outputStream.write(reqByte);
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
    socket.close();
  }
}
