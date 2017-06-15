package com.rpc.thrift.server;

import java.util.Iterator;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.springframework.util.CollectionUtils;

import rec.recommend.RecommendationRequest;
import rec.recommend.RecommendationResponse;
import serving.GenericServing;
import serving.GenericServing.Iface;
import serving.GenericServingRequest;
import serving.GenericServingResponse;

public class ServerStarter {
    @SuppressWarnings("unchecked")
    private static TProcessor tProcessor = new GenericServing.Processor(new SeviceImpl());
    private static int port = 10111;

    public static void main(String[] argss) {

        TTSimpleServer();
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

    public static void TTSimpleServer() {
        AbstractServer t = null;
        try {
            t = new TTSimpleServer(tProcessor, port);
            t.start();
        } catch (Exception e) {
        } finally {
            if (t != null) {
                t.end();
            }
        }
    }

    public static void TTThreadPoolServer() {
        AbstractServer t = null;
        try {
            t = new TTThreadPoolServer(tProcessor, port);
            t.start();
        } catch (Exception e) {
        } finally {
            if (t != null) {
                t.end();
            }
        }
    }

    public static void TTNonblockingServer() {
        AbstractServer t = null;
        try {
            t = new TTNonblockingServer(tProcessor, port);
            t.start();
        } catch (Exception e) {
        } finally {
            if (t != null) {
                t.end();
            }
        }
    }

    public static void TTHsHaServer() {
        AbstractServer t = null;
        try {
            t = new TTHsHaServer(tProcessor, port);
            t.start();
        } catch (Exception e) {
        } finally {
            if (t != null) {
                t.end();
            }
        }
    }

    public static void TTThreadedSelectorServer() {
        AbstractServer t = null;
        try {
            t = new TTThreadedSelectorServer(tProcessor, port);
            t.start();
        } catch (Exception e) {
        } finally {
            if (t != null) {
                t.end();
            }
        }
    }

}
