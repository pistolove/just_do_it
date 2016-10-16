package com.rpc.thrift.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;

public abstract class AbstractServer {

    protected TServer server;

    public AbstractServer(TProcessor tProcessor, int port) throws Exception {
        this.createServer(tProcessor, port);
    }

    protected abstract void createServer(TProcessor tProcessor, int port) throws Exception;

    public void start() {
        this.server.serve();
    }

    public void end() {
        this.server.stop();
    }

}
