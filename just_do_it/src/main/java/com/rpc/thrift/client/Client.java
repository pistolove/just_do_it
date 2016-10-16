package com.rpc.thrift.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;

import serving.GenericServing;
import serving.GenericServing.AsyncClient.Serve_call;

public class Client {

    public static void main(String[] args) {
        // ServerBlockClient();
        // ServerNonBlockClient();
        ServerNonBlockAsynClient();
    }

    public static void ServerBlockClient() {
        TSocket tSocket = new TSocket("127.0.0.1", 10111, 2000);
        try {
            tSocket.open();
            TProtocol tProtocol = new TBinaryProtocol(tSocket);
            GenericServing.Client client = new GenericServing.Client(tProtocol);
            Map<String, String> map = new HashMap<String, String>();
            map.put("1", "1");
            map.put("2", "2");
            map.put("3", "3");
            System.out.print(client.ServeStr(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tSocket.close();
        }

    }

    public static void ServerNonBlockClient() {
        TSocket tSocket = new TSocket("127.0.0.1", 10111, 2000);
        try {
            tSocket.open();
            TFramedTransport tFramedTransport = new TFramedTransport(tSocket);
            TProtocol tProtocol = new TBinaryProtocol(tFramedTransport);
            GenericServing.Client client = new GenericServing.Client(tProtocol);
            Map<String, String> map = new HashMap<String, String>();
            map.put("1", "1");
            map.put("2", "2");
            map.put("3", "3");
            System.out.print(client.ServeStr(map));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tSocket.close();
        }

    }

    public static void ServerNonBlockAsynClient() {
        TNonblockingTransport transport = null;
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            transport = new TNonblockingSocket("127.0.0.1", 10111, 2000);
            GenericServing.AsyncClient client = new GenericServing.AsyncClient(new TBinaryProtocol.Factory(),
                    clientManager, transport);
            Map<String, String> map = new HashMap<String, String>();
            map.put("1", "1");
            map.put("2", "2");
            map.put("3", "3");
            client.ServeStr(map, new callback());
            Thread.currentThread().sleep(10L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class callback implements AsyncMethodCallback<Serve_call> {

        public void onComplete(Serve_call paramT) {
            System.out.print(paramT.toString());
        }

        public void onError(Exception paramException) {
            System.out.print("22222");
        }
    }

}
