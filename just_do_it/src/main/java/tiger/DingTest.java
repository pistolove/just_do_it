package tiger;

import com.alibaba.fastjson.*;
import com.google.common.collect.*;
import com.util.HttpUtil;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

public class DingTest {

    public static void main(String[] args) {
        try {
            String token = getToken();
            List<String> userIds = Lists.newLinkedList();
            for (int offset = 0, size = 50; offset < 10; offset++) {
                List<String> subUserIds = getUserIds(token, offset, size);
                if (CollectionUtils.isEmpty(subUserIds)) {
                    break;
                } else {
                    userIds.addAll(subUserIds);
                }
            }
            List<User> userList = Lists.newLinkedList();
            if (CollectionUtils.isNotEmpty(userIds)) {
                userIds.forEach(e -> {
                    User user = getUser(token, e);
                    if (user != null) {
                        userList.add(user);
                    }
                });
            }
            System.out.println(JSONObject.toJSONString(userList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String token, String userid) {
        String url = "https://oapi.dingtalk.com/user/get";
        Map<String, Object> params = Maps.newHashMap();
        params.put("access_token", token);
        params.put("userid", userid);
        String resultStr = HttpUtil.get(url, Maps.newHashMap(), params);
        if (resultStr != null) {
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            User user = new User();
            user.userid = userid;
            user.name = jsonObject.getString("name");
            user.email = jsonObject.getString("email");
            return user;
        }
        return null;
    }

    public static List<String> getUserIds(String token, int offset, int size) throws Exception {
        String url = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob?access_token=" + token;
        Map<String, String> params = Maps.newHashMap();
        params.put("status_list", "3");
        params.put("offset", String.valueOf(offset));
        params.put("size", String.valueOf(size));
        String resultStr = HttpUtil.post(url, params);
        List<String> list = Lists.newLinkedList();
        if (resultStr != null) {
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            if (jsonObject.getJSONObject("result") != null) {
                JSONArray dataList = jsonObject.getJSONObject("result").getJSONArray("data_list");
                if (dataList != null) {
                    list.addAll(dataList.stream().map(e -> (String) e).collect(Collectors.toList()));
                }
            }
        }
        return list;
    }

    public static String getToken() throws Exception {
        String url = "https://oapi.dingtalk.com/gettoken";
        Map<String, Object> params = Maps.newHashMap();
        params.put("appkey", "dingece2y7ipacs4izoa");
        params.put("appsecret", "vc5UZlBX8v3ShU1XL8raUwQ4WaPPO20ABtFnktOa5LC-Ow7nmJKnQqDPEBHA6vLq");
        String result = HttpUtil.get(url, Maps.newHashMap(), params);
        if (result != null) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject.getString("access_token");
        }
        throw new Exception("can't get token");
    }

    static class User {

        String userid;
        String email;
        String name;
    }
}
