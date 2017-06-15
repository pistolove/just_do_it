package com.rpc.thrift.server;

import java.util.Iterator;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.util.CollectionUtils;

import rec.recommend.RecommendationRequest;
import rec.recommend.RecommendationResponse;
import serving.GenericServing;
import serving.GenericServing.Iface;
import serving.GenericServingRequest;
import serving.GenericServingResponse;

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

    public void server() throws Exception {
        TProcessor tProcessor = new GenericServing.Processor(new SeviceImpl());
        int port = 10111;
        TNonblockingServerSocket socket = new TNonblockingServerSocket(port);
        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(socket);
        args.protocolFactory(new TBinaryProtocol.Factory());
        args.processorFactory(new TProcessorFactory(tProcessor));
        TServer tServer = new TThreadedSelectorServer(args);
        tServer.serve();
    }

    private static class SeviceImpl implements Iface {

        public GenericServingResponse Serve(GenericServingRequest paramGenericServingRequest) throws TException {
            return null;
        }

        public RecommendationResponse Recommend(RecommendationRequest paramRecommendationRequest) throws TException {
            return null;
        }

        public String ServeStr(Map<String, String> paramMap) throws TException {
            if (!CollectionUtils.isEmpty(paramMap)) {
                StringBuilder sb = new StringBuilder();
                Iterator<String> it = paramMap.keySet().iterator();
                while (it.hasNext()) {
                    sb.append(paramMap.get(it.next()));
                }
                return sb.toString();
            }
            return "your param is null";
        }
    }

}
