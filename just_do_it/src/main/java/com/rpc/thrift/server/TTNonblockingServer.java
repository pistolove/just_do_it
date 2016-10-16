package com.rpc.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class TTNonblockingServer extends AbstractServer {

    public TTNonblockingServer(TProcessor tProcessor, int port) throws Exception {
        super(tProcessor, port);
    }

    @Override
    protected void createServer(TProcessor tProcessor, int port) throws Exception {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);
        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.processorFactory(new TProcessorFactory(tProcessor));
        this.server = new TNonblockingServer(args);
    }

}
