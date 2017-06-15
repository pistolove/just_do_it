package com.java.socket;

import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8888);
        try {
            Scanner sc = new Scanner(System.in);
            String mes = sc.nextLine();
            socket.getOutputStream().write(mes.getBytes());
        } finally {
            socket.close();
        }

    }

}
