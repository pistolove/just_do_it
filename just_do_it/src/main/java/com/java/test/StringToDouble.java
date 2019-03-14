package com.java.test;

import org.apache.commons.lang3.StringUtils;

public class StringToDouble {
    public static final String s = "1111";

    static {
        // System.out.print(s.);
    }

    public static void main(String[] args) {
        StringToDouble a = new StringToDouble();
        System.out.println(a.parseDouble("-1.22200"));

    }

    public Double parseDouble(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String[] splitString = param.split("\\.");

        String high;
        String low = null;

        if (splitString.length == 1) {
            high = splitString[0];
        } else if (splitString.length == 2) {
            high = splitString[0];
            low = splitString[1];
        } else {
            return null;
        }

        boolean ifHasFuHao = false;
        int a = high.indexOf("+");
        int b = high.indexOf("-");
        if (a > 0 || b > 0) {
            return null;
        }
        if (a == 0) {
            high = high.substring(1, high.length());
        }
        if (b == 0) {
            high = high.substring(1, high.length());
            ifHasFuHao = true;
        }

        double highDouble = 0;
        if (StringUtils.isNotBlank(high) && intCheck(high)) {
            highDouble = strToDouble(high);
        }

        double lowDouble = 0;
        if (StringUtils.isNotBlank(low) && intCheck(low)) {
            lowDouble = strToDouble(low);

            int zeroNum = 0;
            for (char c : low.toCharArray()) {
                if (c != 0) {
                    break;
                }
                zeroNum++;
            }

            while (lowDouble > 1) {
                lowDouble = lowDouble / 10;
            }
            while (zeroNum > 0) {
                lowDouble = lowDouble / 10;
                zeroNum--;
            }
        }

        if (ifHasFuHao) {
            return (highDouble + lowDouble) * -1;
        }
        return highDouble + lowDouble;

    }

    public boolean intCheck(String param) {
        if (StringUtils.isEmpty(param)) {
            return false;
        }
        for (char tmp : param.toCharArray()) {
            if (tmp < 48 || tmp > 57) {
                return false;
            }
        }

        return true;
    }

    public Double strToDouble(String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        double result = 0;
        for (char tmp : param.toCharArray()) {
            result = result * 10 + (tmp - '0');
        }
        return result;
    }
}
