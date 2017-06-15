package com.http.asyn;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.AsyncRestTemplate;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;

public class AsyncRestTemplateTest {

    public static void main(String[] args) throws Exception {
        AsyncRestTemplate asycTemp = new AsyncRestTemplate();
        // String url =
        // "http://api.itv.letv.com/iptv/api//golive/getMovieList.json?langcode=zh_cn";

        String url = "https://lebuy-scloud.cp21.ott.cibntv.net/api/v3/product/info?product_ids=20454&type=id&_sign=acc421f7677ec418ba566054cc3b5c48";

        HttpMethod method = HttpMethod.GET;
        Class<String> responseType = String.class;
        // create request entity using HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>("params", headers);
        Future<ResponseEntity<String>> future = asycTemp.exchange(url, method, requestEntity, responseType);
        ListenableFuture<ResponseEntity<String>> result = JdkFutureAdapters.listenInPoolThread(future);

        System.out.println(TimeUnit.MINUTES.toSeconds(1));

        Futures.addCallback(result, new FutureCallback<ResponseEntity<String>>() {

            public void onSuccess(ResponseEntity<String> result) {
                System.out.println("success result: ");
            }

            public void onFailure(Throwable t) {
                System.out.println("failure: " + t);
            }

        });
    }
}
