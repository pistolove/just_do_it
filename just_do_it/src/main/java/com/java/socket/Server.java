package com.java.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(8888);
    while (true) {
      try {
        Socket socket = serverSocket.accept();
        new IOThread(socket).start();
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
    serverSocket.close();
  }

  static class IOThread extends Thread {

    private Socket socket;

    public IOThread(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      while (true) {
        try {
          byte[] size = new byte[4];
          socket.getInputStream().read(size);
          int dataLength = (size[0] & 255) << 24 | (size[1] & 255) << 16 | (size[2] & 255) << 8 | size[3] & 255;
          byte[] data = new byte[dataLength];
          socket.getInputStream().read(data);
          System.out.println(new String(data));
        } catch (Exception e) {
          e.printStackTrace();
          break;
        }
      }
      try {
        this.socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
