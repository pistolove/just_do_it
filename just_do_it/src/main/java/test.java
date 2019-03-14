import java.util.*;

public class test {

    public static void main(String args[]) throws Exception {

        System.out.println(0.1f * 3 == 0.3);

        int testNum = 100000;
        List<warrant> lists = new ArrayList<warrant>(testNum);
        for (int i = 0; i < testNum; i++) {
            warrant w = new warrant();
            Random random = new Random(i);
            w.type = random.nextInt(4);
            w.volumn = random.nextInt(100000000);
            w.num = random.nextInt(6);
            w.change = random.nextInt(3);
            w.inOutPrice = random.nextBoolean();
            w.price = random.nextInt(300);
            w.time = random.nextInt(30000);
            w.level = random.nextInt(6);
            lists.add(w);
        }

        List<warrant> results = new ArrayList<warrant>(testNum);
        long start = System.currentTimeMillis();
        for (warrant w : lists) {
            if (filter(w)) {
                results.add(w);
            }
        }

        Collections.sort(results, new Comparator<warrant>() {
            @Override public int compare(warrant o1, warrant o2) {
                if (o1.price > o2.price) {
                    return 0;
                }
                return 1;
            }
        });

        results.subList(10, 30);

        System.out.println(System.currentTimeMillis() - start + "    " + results.size());
    }

    public static boolean filter(warrant w) {
        if (w != null) {
            //if (w.type != 1) {
            // return false;
            //}
            if (w.volumn == 0) {
                return false;
            }
            if (w.num != 1) {
                return false;
            }
            if (w.change != 1) {
                return false;
            }
            if (w.inOutPrice) {
                return false;
            }
            // if (w.price < 50 || w.price > 230) {
            //  return false;
            // }
            // if (w.time < 10000 || w.time > 24000) {
            //  return false;
            // }
            if (w.level != 3) {
                return false;
            }
            return true;
        }
        return false;
    }

    static class warrant {

        int type; //0,1,2,3

        int volumn; //成交量

        int num;//每股手数  0 1 2 3 4 5

        int change;//换股比例 0 1,2

        boolean inOutPrice;// 价内价外 1,2

        int price;//行权价    1-100

        int time;//行权时间1-1000
        int level;//有效杠杆
    }
}
