package com.leetcode.string;

/**
 * Created by LYuan on 2016/9/23.
 * Given a roman numeral, convert it to an integer.
 * Input is guaranteed to be within the range from 1 to 3999.
 * 罗马数字的计数特性：
 * I = 1
 * V = 5
 * X = 10
 * L = 50
 * C = 100
 * D = 500
 * M = 1000
 *
 * IV = 4
 * IX = 9
 * XL = 40
 * XC = 90
 * CD = 400
 * CM = 900
 *
 * Function Signature:
 * public int romanToInteger(String a) {...}
 *
 * */
public class E13_Roman_to_Integer {
    public static void main(String[] args) {
        System.out.println(romanToInteger("VII"));
        System.out.println(romanToInteger("XLI"));
        System.out.println(romanToInteger("MCMLIV"));
    }

    // 逆向扫描解法
    // 技巧1：使用逆向扫描很关键，这是有罗马数组的构成特性决定的，如果顺序扫描，那么将很难断句区分。
    // 技巧2：什么情况下选择加减，仅限于I / X / C 这三个字符，因为这三个字符都可以被用作减数
    // 结合上面两点，举例说明：
    // ....IX -> 减1 9
    // ....IV -> 减1 4
    // ....XC -> 减10 90
    // ....XL -> 减10 40
    // ....CM -> 减100 900
    // ....CD -> 减100 400
    // 需要注意的是：99并不是IC(100 - 1)，而是XCIX(100 - 10 + 10 - 1)，减法不能用在数量级不同的符号上。
    static int romanToInteger(String a) {
        a = a.toUpperCase();
        int sum = 0;
        for (int i = a.length() - 1; i >= 0; i--) {
            switch (a.charAt(i)) {
                case 'I': sum += (sum >= 5 ? -1 : 1); break;
                case 'V': sum += 5; break;
                case 'X': sum += 10 * (sum >= 50 ? -1 : 1); break;
                case 'L': sum += 50; break;
                case 'C': sum += 100 * (sum >= 500 ? -1 : 1); break;
                case 'D': sum += 500; break;
                case 'M': sum += 1000; break;   // 不存在比1000还大的单位，所以不存在需要减掉1000的可能
            }
        }
        return sum;
    }
}
