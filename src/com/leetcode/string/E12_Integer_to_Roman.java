package com.leetcode.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/9/23.
 * Given an integer, convert it to a roman numeral.
 * Input is guaranteed to be within the range from 1 to 3999.
 */
public class E12_Integer_to_Roman {
    public static void main(String[] args) {
        String x = Integer.toString(1999);
        for (int i = 0; i < 100; i++) {
            System.out.println(integerToRoman2(i));
        }
    }

    // 因为需要都是连续的没必要用哈希表，直接用数组的索引即可。
    // 这里需要使用提取某一个数的某一位的算法：
    // 对于一个数num，如果要获得从右侧开始数第i位的数值，只需(num / (10 * i)) % 10即可。最高位可以省略%10这个操作。
    // 本质上就是首先用除法（除10的幂）把这一位变成个位数，再用求余法（%10）提取出这一位本身。
    // 例如214<7>483547中，如果要获得7这个数(从右数第7位即10的7次方)，只需要把2147483647 / 1000000 = 2147，然后2147 % 10 = 7
    static String integerToRoman2(int x) {
        String[] I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] M = {"", "M", "MM", "MMM"};
        return M[x / 1000 % 10] + C[x / 100 % 10] + X[x / 10 % 10] + I[x % 10];
    }

    // 由于罗马数字的构成完全是十进制数字按每一位连接在一起的，因此最简单的方法就是把1-3999的构成表列出来排列组合。
    // 比如1976 = 1000 + 900 + 70 + 6 = M + CM + LXX + VI = MCMLXXVI
    // 然后由除10取余法将int转换为字符串
    static String integerToRoman(int x) {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "");
        map.put(1, "I");
        map.put(2, "II");
        map.put(3, "III");
        map.put(4, "IV");
        map.put(5, "V");
        map.put(6, "VI");
        map.put(7, "VII");
        map.put(8, "VIII");
        map.put(9, "IX");
        map.put(10, "X");
        map.put(20, "XX");
        map.put(30, "XXX");
        map.put(40, "XL");
        map.put(50, "L");
        map.put(60, "LX");
        map.put(70, "LXX");
        map.put(80, "LXXX");
        map.put(90, "XC");
        map.put(100, "C");
        map.put(200, "CC");
        map.put(300, "CCC");
        map.put(400, "CD");
        map.put(500, "D");
        map.put(600, "DC");
        map.put(700, "DCC");
        map.put(800, "DCCC");
        map.put(900, "CM");
        map.put(1000, "M");
        map.put(2000, "MM");
        map.put(3000, "MMM");

        StringBuilder sb = new StringBuilder();
        int temp = 1;
        while (x > 0) {
            sb.insert(0, map.get(x % 10 * temp));
            x /= 10;
            temp *= 10;
        }
        String str = sb.toString();
        return sb.toString();
    }
}
