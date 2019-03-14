package tiger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.client.fluent.*;
import org.apache.http.entity.StringEntity;

import static tiger.SharesFloat.getAllSymbols;

public class Eps {

    public static void main(String[] args) {
        List<String> symbols = getAllSymbols();
        String market = "US";
        for (int i = 0; i < 100000; i++) {
            Lists.partition(symbols, 50).forEach(subSymbols -> {
                try {
                    getFloatShares(subSymbols, market);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static Map<String, Long> getFloatShares(List<String> symbols, String market) throws Exception {
        Map<String, Long> symbolSharesFloat = Maps.newTreeMap(String::compareTo);
        JSONObject body = new JSONObject();
        List<JSONObject> items = Lists.newLinkedList();
        symbols.forEach(e -> {
            JSONObject symbol = new JSONObject();
            symbol.put("symbol", e);
            items.add(symbol);
        });
        JSONObject symbol = new JSONObject();
        symbol.put("symbol", "aaaaaaaaaaaaaaa");
        items.add(symbol);
        body.put("market", market);
        body.put("items", items);

        //String url = "https://ca.tigerfintech.com";
        String url = "http://172.28.48.49:8087";
        url = url + "/stock_info/eps";
        StringEntity entity = new StringEntity(JSONObject.toJSONString(body), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        Content content =
                Request.Post(url).setHeader("Content-Type", "application/json").body(entity).execute().returnContent();
        System.out.println(content.asString());
        return symbolSharesFloat;
    }
}
