package com.http.asyn;

import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

public class AsyncRestTemplateTest {

    public static void main(String[] args) throws Exception {
        AsyncRestTemplate asycTemp = new AsyncRestTemplate();
        String url = "http://api.itv.letv.com/iptv/api//golive/getMovieList.json?langcode=zh_cn";
        HttpMethod method = HttpMethod.GET;
        Class<String> responseType = String.class;
        // create request entity using HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> requestEntity = new HttpEntity<String>("params", headers);
        ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(url, method, requestEntity, responseType);

        try {
            // waits for the result
            ResponseEntity<String> entity = future.get();
            // prints body source code for the given URL
            System.out.println(entity.getBody());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Thread.currentThread().sleep(3000L);
        // 异步调用后的回调函数
        future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            // 调用失败
            public void onFailure(Throwable ex) {
                System.out.println("=====rest response faliure======");
            }

            // 调用成功
            public void onSuccess(ResponseEntity<String> result) {
                System.out.println("--->async rest response success----, result = " + result.getBody());
            }
        });
    }

}
