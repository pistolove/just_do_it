package tiger;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.collections.*;
import org.joda.time.DateTime;

public class DailyDate {

    @Data
    static class Fin {

        int id;
        Date date;
        int value;

        Fin(int id, int value, Date date) {
            this.id = id;
            this.date = date;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        List<Fin> list = Lists.newLinkedList();
        list.add(new Fin(1, 1, new DateTime().minusMonths(7).toDate()));
        list.add(new Fin(1, 3, new DateTime().minusMonths(4).toDate()));
        list.add(new Fin(1, 4, new Date()));
        list.add(new Fin(1, 2, new DateTime().plusMonths(1).toDate()));

        Map<Integer, List<Fin>> map = groupByDataItemId(list);
        Map<Integer, Fin> result = getLatestCiqFinancialValueData(map, new DateTime().plusMonths(2).toDate());
        System.out.println(JSONObject.toJSONString(result));
    }

    // 以dataitem分组，并且内部值按 periodEndDate由大到小排序
    public static Map<Integer, List<Fin>> groupByDataItemId(List<Fin> dataList) {
        Map<Integer, List<Fin>> result = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(dataList)) {
            result = dataList.stream().collect(Collectors.groupingBy(Fin::getId));
            result.entrySet().forEach(e -> e.getValue().sort(
                    Comparator.comparing(Fin::getDate).reversed()));
        }
        return result;
    }

    // 遍历所有dataitem，取出第一个 fillingdate 小于 date的数据
    public static Map<Integer, Fin> getLatestCiqFinancialValueData(
            Map<Integer, List<Fin>> dataList, Date date) {
        Map<Integer, Fin> result = Maps.newHashMap();
        if (MapUtils.isNotEmpty(dataList)) {
            dataList.entrySet().forEach(e -> {
                Fin valueData = e.getValue()
                        .stream()
                        .filter(data -> data.getDate().getTime() < date.getTime())
                        .findFirst()
                        .orElse(null);
                if (valueData != null) {
                    result.put(e.getKey(), valueData);
                }
            });
        }
        return result;
    }
}
