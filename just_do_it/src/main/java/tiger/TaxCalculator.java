package tiger;

import com.google.common.collect.Lists;
import java.util.List;

public class TaxCalculator {

    public static void main(String[] args) {

        int initMonPay = 26500;
        int addMonPay = initMonPay + 3500;
        List<Integer> monPays = Lists.newLinkedList();
        monPays.add(initMonPay);
        monPays.add(initMonPay);
        monPays.add(initMonPay);
        monPays.add(initMonPay);
        monPays.add((int) (initMonPay * 1.5));
        monPays.add(addMonPay);
        monPays.add(addMonPay);
        monPays.add(addMonPay);
        monPays.add(addMonPay);
        monPays.add(addMonPay);
        monPays.add((int) (addMonPay * 1.5));
        monPays.add(addMonPay);
        new TaxCalculator().printMonthTax(monPays, 1500);
    }

    private void printMonthTax(List<Integer> monPay, int specialDeduction) {
        double hisTotalTax = 0;
        double totalPay = 0;
        double totalDeratePay = 0;
        for (int i = 1; i < 13; i++) {
            double deratePay; //每月扣除五险一金后数额
            // 五除一金全额交，按0.222交，上限25401，7月调整上限
            int currentMonPay = monPay.get(i - 1);
            int limit = 25401;
            if (i >= 7) {
                limit = 27400;
            }
            if (currentMonPay < limit) {
                deratePay = currentMonPay * 0.778;
            } else {
                deratePay = currentMonPay - limit * 0.222;
            }
            totalDeratePay = totalDeratePay + (deratePay - 5000 - specialDeduction);
            TaxRate taxRate = this.getTaxRate((int) totalDeratePay);
            double tax = totalDeratePay * taxRate.rate - taxRate.quickDivision - hisTotalTax;
            hisTotalTax = hisTotalTax + tax;

            System.out.println(i + "月份需要交税:" + tax + " ,到手:" + (deratePay - tax));
            totalPay = totalPay + deratePay - tax;
        }
        System.out.println("全年共交税:" + hisTotalTax + ", 共到手:" + totalPay);
    }

    private TaxRate getTaxRate(int deratePay) {
        if (deratePay <= 36000) {
            return new TaxRate(0.03, 0);
        } else if (deratePay <= 144000) {
            return new TaxRate(0.1, 2520);
        } else if (deratePay <= 300000) {
            return new TaxRate(0.2, 16920);
        } else if (deratePay <= 420000) {
            return new TaxRate(0.25, 31920);
        } else if (deratePay <= 660000) {
            return new TaxRate(0.3, 52920);
        } else if (deratePay <= 960000) {
            return new TaxRate(0.35, 85920);
        }
        return new TaxRate(0.45, 181920);
    }

    static class TaxRate {

        double rate;
        int quickDivision;

        public TaxRate(double rate, int quickDivision) {
            this.rate = rate;
            this.quickDivision = quickDivision;
        }
    }
}
