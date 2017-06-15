package com.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private ByteBuffer readBuffer = ByteBuffer.allocate(8);// 调整缓存的大小可以看到打印输出的变化
    private Map<SocketChannel, byte[]> clientMessage = new ConcurrentHashMap<SocketChannel, byte[]>();

    public Server() throws Exception {
        this.selector = SelectorProvider.provider().openSelector();
        this.listenChannel = ServerSocketChannel.open();
        this.listenChannel.configureBlocking(false);
        this.listenChannel.bind(new InetSocketAddress("localhost", 8001));
        this.listenChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("server started...");
        new Server().start();
    }

    public void start() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            this.selector.select();
            System.out.println("server select handler one time");
            Set<SelectionKey> keys = this.selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    this.accept(key);
                } else if (key.isReadable()) {
                    this.read(key);
                }
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Clear out our read buffer so it's ready for new data
        this.readBuffer.clear();

        // Attempt to read off the channel
        int numRead;
        try {
            numRead = socketChannel.read(this.readBuffer);
        } catch (IOException e) {
            // The remote forcibly closed the connection, cancel
            // the selection key and close the channel.
            key.cancel();
            socketChannel.close();
            this.clientMessage.remove(socketChannel);
            return;
        }

        byte[] bytes = this.clientMessage.get(socketChannel);
        if (bytes == null) {
            bytes = new byte[0];
        }
        if (numRead > 0) {
            byte[] newBytes = new byte[bytes.length + numRead];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            System.arraycopy(this.readBuffer.array(), 0, newBytes, bytes.length, numRead);
            this.clientMessage.put(socketChannel, newBytes);
            System.out.println(new String(newBytes));
        } else {
            String message = new String(bytes);
            System.out.println(message);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = ssc.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(this.selector, SelectionKey.OP_READ);
        System.out.println("a new client connected");
    }
}
