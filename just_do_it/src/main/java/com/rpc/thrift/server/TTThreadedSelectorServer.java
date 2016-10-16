package com.rpc.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class TTThreadedSelectorServer extends AbstractServer {

    public TTThreadedSelectorServer(TProcessor tProcessor, int port) throws Exception {
        super(tProcessor, port);
    }

    @Override
    protected void createServer(TProcessor tProcessor, int port) throws Exception {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);
        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(socket);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.processorFactory(new TProcessorFactory(tProcessor));
        // args.executorService(executorService)
        // args.selectorThreads(i) 读写IO线程数
        this.server = new TThreadedSelectorServer(args);

    }

}
