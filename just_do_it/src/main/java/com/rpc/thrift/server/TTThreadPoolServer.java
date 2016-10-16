package com.rpc.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class TTThreadPoolServer extends AbstractServer {

    public TTThreadPoolServer(TProcessor tProcessor, int port) throws Exception {
        super(tProcessor, port);
    }

    @Override
    protected void createServer(TProcessor tProcessor, int port) throws Exception {
        TServerTransport serverTransport = new TServerSocket(port);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.processorFactory(new TProcessorFactory(tProcessor));
        // args.executorService(executorService)
        this.server = new TThreadPoolServer(args);
    }
}
