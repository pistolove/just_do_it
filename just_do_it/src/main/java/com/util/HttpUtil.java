package com.util;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.*;
import org.apache.http.client.fluent.*;
import org.apache.http.entity.ContentType;

public class HttpUtil {

    public static String get(String url, Map<String, String> requestProperties, Map<String, Object> params) {
        try {
            StringBuilder urlSB = new StringBuilder(url);

            if (params != null) {
                int i = 0;
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (i == 0) {
                        urlSB.append(url.indexOf("?") >= 0 ? '&' : '?')
                                .append(param.getKey())
                                .append('=')
                                .append(param.getValue());
                    } else {
                        urlSB.append('&').append(param.getKey()).append('=').append(param.getValue());
                    }
                    ++i;
                }
            }
            Request request = Request.Get(urlSB.toString());
            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Content content = request.connectTimeout(20000).socketTimeout(20000).execute().returnContent();
            return IOUtils.toString(content.asStream(), "UTF-8");
        } catch (Exception e) {
        }
        return null;
    }

    public static String get(String url, Map<String, String> requestProperties) {
        return get(url, requestProperties, 20000);
    }

    public static String get(String url, Map<String, String> requestProperties, int timeout) {
        try {
            Request request = Request.Get(url);
            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Content content = request.connectTimeout(timeout).socketTimeout(timeout).execute().returnContent();
            return IOUtils.toString(content.asStream(), "UTF-8");
        } catch (Exception e) {
        }
        return null;
    }

    public static String get(String url, Map<String, String> requestProperties, String charset) {
        try {
            Request request = Request.Get(url);
            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Content content = request.connectTimeout(20000).socketTimeout(20000).execute().returnContent();
            return IOUtils.toString(content.asStream(), charset);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getByProxy(String url, Map<String, String> requestProperties) {
        try {
            Request request = Request.Get(url);
            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpHost host = new HttpHost("us.stunnel.net", 110, "https");
            Content content = request.viaProxy(host).connectTimeout(20000).
                    socketTimeout(20000).execute().returnContent();
            return IOUtils.toString(content.asStream(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getByProxy(String url, Map<String, String> requestProperties, Map<String, Object> params) {
        try {
            StringBuilder urlSB = new StringBuilder(url);
            if (params != null) {
                int i = 0;
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (i == 0) {
                        urlSB.append(url.indexOf("?") >= 0 ? '&' : '?')
                                .append(param.getKey())
                                .append('=')
                                .append(param.getValue());
                    } else {
                        urlSB.append('&').append(param.getKey()).append('=').append(param.getValue());
                    }
                    ++i;
                }
            }
            return getByProxy(urlSB.toString(), requestProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String post(String url, Map<String, String> params)
            throws IOException {
        if (params == null) {
            return get(url, null);
        }

        List<NameValuePair> pairs;
        Form form = Form.form();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            form.add(entry.getKey(), entry.getValue());
        }
        pairs = form.build();

        Content content = Request.Post(url)
                .bodyForm(pairs, Charset.forName("UTF-8")).connectTimeout(20000)
                .socketTimeout(20000).execute().returnContent();

        return IOUtils.toString(content.asStream(), "UTF-8");
    }

    public static String post(String url, List<Pair<String, String>> params, Map<String, String> requestProperties)
            throws IOException {
        if (params == null) {
            return get(url, null);
        }
        List<NameValuePair> pairs;
        Form form = Form.form();

        for (Pair<String, String> values : params) {
            form.add(values.getKey(), values.getValue());
        }
        pairs = form.build();
        Request request = Request.Post(url);
        if (requestProperties != null) {
            for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Content content = request
                .bodyForm(pairs, Charset.forName("UTF-8")).connectTimeout(20000)
                .socketTimeout(20000).execute().returnContent();

        return IOUtils.toString(content.asStream(), "UTF-8");
    }

    public static String post(String url, String accessToken, List<Pair<String, String>> params,
            Map<String, String> requestProperties) throws IOException {
        if (params == null) {
            return get(url, null);
        }
        List<NameValuePair> pairs;
        Form form = Form.form();

        for (Pair<String, String> values : params) {
            form.add(values.getKey(), values.getValue());
        }
        pairs = form.build();
        Request request = Request.Post(url);
        if (requestProperties != null) {
            for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Content content = request.setHeader("Authorization", "Bearer " + accessToken)
                .bodyForm(pairs, Charset.forName("UTF-8")).connectTimeout(20000)
                .socketTimeout(20000).execute().returnContent();

        return IOUtils.toString(content.asStream(), "UTF-8");
    }

    public static String post(String url, String accessToken, JSONObject json) throws IOException {
        return post(url, accessToken, json, 20000);
    }

    public static String post(String url, String accessToken, JSONObject json, int timeout) throws IOException {
        if (json == null) {
            return get(url, null, timeout);
        }

        Content content = Request.Post(url).setHeader("Authorization", "Bearer " + accessToken)
                .bodyString(json.toString(), ContentType.APPLICATION_JSON).connectTimeout(timeout)
                .socketTimeout(timeout).execute().returnContent();

        return IOUtils.toString(content.asStream(), "UTF-8");
    }

    public static String post(String url, JSONObject json) throws IOException {
        return post(url, json, 20000);
    }

    public static String post(String url, JSONObject json, int timeout) throws IOException {
        if (json == null) {
            return get(url, null, timeout);
        }

        Content content = Request.Post(url).bodyString(json.toString(), ContentType.APPLICATION_JSON)
                .connectTimeout(timeout).socketTimeout(timeout).execute().returnContent();

        return IOUtils.toString(content.asStream(), "UTF-8");
    }

    public static String get(String url, String accessToken, Map<String, Object> params) {
        try {
            StringBuilder urlSB = new StringBuilder(url);

            if (params != null) {
                int i = 0;
                for (Map.Entry<String, Object> param : params.entrySet()) {
                    if (i == 0) {
                        urlSB.append(url.indexOf("?") >= 0 ? '&' : '?')
                                .append(param.getKey())
                                .append('=')
                                .append(param.getValue());
                    } else {
                        urlSB.append('&').append(param.getKey()).append('=').append(param.getValue());
                    }
                    ++i;
                }
            }

            Content content = Request.Get(urlSB.toString()).setHeader("Authorization", "Bearer " + accessToken)
                    .connectTimeout(10000)
                    .socketTimeout(10000).execute().returnContent();

            return IOUtils.toString(content.asStream(), "UTF-8");
        } catch (Exception e) {
        }

        return null;
    }

    public static Map<String, String> getRequestProperties() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
        result.put("X-Forwarded-For", getRandomIp());
        return result;
    }

    public static Map<String, String> getEmptyReqPropertied() {
        return new HashMap<>();
    }

    public static String getRandomIp() {
        //ip范围
        int[][] range = {
                //36.56.0.0-36.63.255.255
                {607649792, 608174079},
                //61.232.0.0-61.237.255.255
                {1038614528, 1039007743},
                //106.80.0.0-106.95.255.255
                {1783627776, 1784676351},
                //121.76.0.0-121.77.255.255
                {2035023872, 2035154943},
                //123.232.0.0-123.235.255.255
                {2078801920, 2079064063},
                //139.196.0.0-139.215.255.255
                {-1950089216, -1948778497},
                //171.8.0.0-171.15.255.255
                {-1425539072, -1425014785},
                //182.80.0.0-182.92.255.255
                {-1236271104, -1235419137},
                //210.25.0.0-210.47.255.255
                {-770113536, -768606209},
                //222.16.0.0-222.95.255.255
                {-569376768, -564133889}
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2])
                + "." + Integer.toString(b[3]);

        return x;
    }

    public static String getFromHkex(String url) {
        long queryTime = System.currentTimeMillis();
        String callback = "jQuery" + queryTime + "_" + queryTime;
        url = url + "&qid=" + queryTime + "&callback=" + callback;
        String content = get(url, getRequestProperties(), "UTF-8", 10000, 10000);
        if (content != null) {
            int callbackIndex = content.indexOf(callback);
            if (callbackIndex != -1) {
                content = content.substring(callbackIndex + callback.length() + 1, content.length() - 1);
            }
        }
        return content;
    }

    public static String get(String url, Map<String, String> requestProperties, String charset,
            int connectTimeout, int socketTimeout) {
        try {
            Request request = Request.Get(url);
            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Content content =
                    request.connectTimeout(connectTimeout).socketTimeout(socketTimeout).execute().returnContent();
            return IOUtils.toString(content.asStream(), charset);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getCookie(String url, String host) {
        String cookieStr = "";
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
            conn.setRequestProperty("Host", host);
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Upgrade-Insecure-Request", "1");
            InputStream stream = conn.getInputStream();
            List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
            StringBuffer buf = new StringBuffer();
            for (int i = cookies.size() - 1; i >= 0; --i) {
                buf.append(cookies.get(i).toString() + "; ");
            }
            cookieStr = buf.toString();
            cookieStr = cookieStr.substring(0, cookieStr.length() - 1);
            stream.close();
            conn.disconnect();
        } catch (Exception e) {
        }
        return cookieStr;
    }

    public static Map<String, String> getCookieProperties(String url, String host) {
        Map<String, String> result = new HashMap<>();
        result.put("Content-Type", "application/json");
        result.put("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
        result.put("Cookie", getCookie(url, host));
        result.put("X-Forwarded-For", getRandomIp());
        return result;
    }
}
