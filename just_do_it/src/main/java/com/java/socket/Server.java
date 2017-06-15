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
                byte[] mes = new byte[1000000];
                socket.getInputStream().read(mes);
                System.out.println(new String(mes));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        serverSocket.close();
    }

}
