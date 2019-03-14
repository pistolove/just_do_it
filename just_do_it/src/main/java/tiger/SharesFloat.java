package tiger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.*;
import org.apache.http.entity.StringEntity;

public class SharesFloat {

    public static void main(String[] args) {
        List<String> symbols = getAllSymbols();
        String market = "US";
        Lists.partition(symbols, 100).forEach(subSymbols -> {
            try {
                writeFile(getFloatShares(subSymbols, market), subSymbols);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
        body.put("market", market);
        body.put("items", items);
        //System.out.println(JSONObject.toJSONString(body));

        String url = "http://172.28.48.49:8087/stock_info/float_shares";
        StringEntity entity = new StringEntity(JSONObject.toJSONString(body), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        Content content =
                Request.Post(url).setHeader("Content-Type", "application/json").body(entity).execute().returnContent();
        if (content != null) {
            JSONObject result = JSONObject.parseObject(content.asString());
            if (result.get("data") != null) {
                JSONObject data = (JSONObject) (result.get("data"));
                data.entrySet().forEach(e -> {
                    JSONObject value = (JSONObject) e.getValue();
                    symbolSharesFloat.put(value.getString("symbol"), value.getLongValue("floatShares"));
                });
            }
        }
        return symbolSharesFloat;
    }

    public static List<String> getAllSymbols() {
        BufferedReader bufferedReader = null;
        List<String> result = Lists.newLinkedList();
        try {
            bufferedReader = new BufferedReader(new FileReader("/Users/chenjian/chen/工作资料/allsymbols.txt"));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                result.add(str.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
        //result = result.subList(0, 10);
        return result;
    }

    public static void writeFile(Map<String, Long> symbolFloats, List<String> symbols) {
        if (MapUtils.isNotEmpty(symbolFloats)) {
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter(new FileWriter("/Users/chenjian/Downloads/float.txt", true));
                for (String symbol : symbols) {
                    if (symbolFloats.containsKey(symbol)) {
                        bufferedWriter.write(symbol + "   " + symbolFloats.get(symbol));
                    } else {
                        bufferedWriter.write(symbol);
                    }
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(bufferedWriter);
            }
        }
    }
}
