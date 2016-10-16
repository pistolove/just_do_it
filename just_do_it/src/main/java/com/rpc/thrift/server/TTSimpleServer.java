package com.rpc.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class TTSimpleServer extends AbstractServer {

    public TTSimpleServer(TProcessor tProcessor, int port) throws Exception {
        super(tProcessor, port);
    }

    @Override
    protected void createServer(TProcessor tProcessor, int port) throws TTransportException {
        TServerTransport serverTransport = new TServerSocket(port);
        TServer.Args args = new TServer.Args(serverTransport);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.processorFactory(new TProcessorFactory(tProcessor));
        this.server = new TSimpleServer(args);
    }

}
