package com.rpc.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class TTHsHaServer extends AbstractServer {

    public TTHsHaServer(TProcessor tProcessor, int port) throws Exception {
        super(tProcessor, port);
    }

    @Override
    protected void createServer(TProcessor tProcessor, int port) throws Exception {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);
        THsHaServer.Args args = new THsHaServer.Args(socket);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.processorFactory(new TProcessorFactory(tProcessor));
        // args.executorService(executorService)
        this.server = new THsHaServer(args);
    }

}
