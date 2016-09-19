package com.leetcode.string;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/9/18.
 * Convert a string to an integer.
 * 这里需要搞清楚的是，题目并不是要求你把字符串的ASCII值转化成Integer，因为没有任何含义。
 * 题目要求的是你的函数有能力把一个字符串格式的数字转换成为int类型的数值。
 * 但是这个题目的特殊之处在于，他同时隐式的要求你的函数有能力处理变态情况的输入字符串。
 *
 * Hint: Carefully consider all possible input cases.
 *
 * Notes: It is intended for this problem to be specified vaguely (ie, no given input specs).
 * You are responsible to gather all the input requirements up front.
 *
 * Requirements for atoi:
 * 1. The function first discards as many whitespace characters as necessary until the first non-whitespace character is found.
 * Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible,
 * and interprets them as a numerical value.
 *
 * 2. The string can contain additional characters after those that form the integral number,
 * which are ignored and have no effect on the behavior of this function.
 *
 * 3. If the first sequence of non-whitespace characters in str is not a valid integral number,
 * or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 *
 * 4. If no valid conversion could be performed, a zero value is returned.
 *
 * 5. If the correct value is out of the range of representable values, INT_MAX (2147483647) or INT_MIN (-2147483648) is returned.
 */
public class E8_String_to_Integer {
    public static void main(String[] args) {
        String a = "1";
        System.out.println(atoi(a));
        int x = Integer.MAX_VALUE + Integer.MAX_VALUE + 2;

        a = "2147483647";
        System.out.println(StringToInteger(a));
        a = "2147483648";
        System.out.println(StringToInteger(a));
        a = "-2147483648";
        System.out.println(StringToInteger(a));
        a = "-2147483649";
        System.out.println(StringToInteger(a));
    }

    // 需要学习下检测overflow的机制
    // 有哪些可能的非法输入：
    // 1. null string & empty string
    // 3. 全是空格 或者 第一个非空格字符不是数字或正负号之中的任何一个
    // 4. 如果第一个非空格字符是正负号，那么后面紧接着必须要有至少一个数字才是有效的
    // 5. 数字位数超过int范围，即大于2147483647或小于-2147483648
    static int atoi(String a) {
        if (a == null || a.length() == 0) return 0;
        int l = a.length();
        int i = 0;

        while (i < l && (a.charAt(i) == ' ' || a.charAt(i) != '+' && a.charAt(i) != '-' && a.charAt(i) < '0' && a.charAt(i) > '9')) i++;
        if (i == l) return 0;

        // 处理第一个非空格字符是符号或数字的情况，统一成无符号字符串，符号单独存，将有效数字区间存入新字符串。
        int sign = 1;
        if      (a.charAt(i) == '+') i++;
        else if (a.charAt(i) == '-') {i++; sign = -1;}
        int j = i;
        while (j < l && a.charAt(j) >= '0' && a.charAt(j) <= '9') j++;
        if (j == i) return 0;
        String digit = a.substring(i, j);

        // 提前判断overflow：如果字符串长度超过10或者第0个字符串大于2，那么一定是overflow的
        if (digit.length() > 10 || digit.length() == 10 && digit.charAt(0) > '2') {
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        // 由于极大值是2147483647而极小值是-2147483648，所以如果overflow或underflow了，符号一定会变。
        int sum = 0;
        for (int idx = digit.length() - 1; idx >= 0; idx--) {
            if (sign < 0)   sum -= (digit.charAt(idx) - '0') * (int)Math.pow(10, digit.length() - 1 - idx);
            else            sum += (digit.charAt(idx) - '0') * (int)Math.pow(10, digit.length() - 1 - idx);
        }
        if      (sign < 0 && sum > 0) return Integer.MIN_VALUE;
        else if (sign > 0 && sum < 0) return Integer.MAX_VALUE;
        return sum;
    }

    // 标准的字符串转换整型的算法，考虑溢出和符号
    // 处理整形溢出的方式1：赋值给长整型，无需特殊处理长度为10的字符串
    public static int StringToInteger(String a) {
        // 处理null字符串、空字符串、全是空格的字符串
        if (a == null || a.length() == 0 || a.trim().length() == 0) return 0;

        // 去掉正负号，单独存储正负号
        int sign = 1, start = 0;
        if (a.charAt(0) == '+' || a.charAt(0) == '-') {
            if (a.charAt(0) == '-') sign = -1;
            start++;
        }

        // 乘十叠加法，只要发现超过极大值就根据符号情况返回极大极小值本身，因为long叠加到第十次不会立即溢出
        // 注意必须使用极大值作为比较对象，因为极大值是2147483647，而极小值是-2147483648
        // 只要绝对值大于极大值，那么即使这个数是负数也可以正确判断为极小值，绝对值小于等于极大值，一定不会出现溢出。
        // 但是反过来，如果值小于极小值的绝对值，那么对于正数也依然可能会溢出。
        long sum = 0;
        for (int i = start; i < a.length(); i++) {
            int x = a.charAt(i) - '0';
            sum = 10 * sum + x;
            if (sum > Integer.MAX_VALUE) return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        return (int) sum * sign;
    }

    // 处理整形溢出的方式2：依然使用整型，但是检查当前sum是否已经超过了极大极小值/10的，或者等于但即将加上的位数大于极大极小值的最低位
    public static int StringToInteger2(String a) {
        // 处理null字符串、空字符串、全是空格的字符串
        if (a == null || a.length() == 0 || a.trim().length() == 0) return 0;

        // 去掉正负号，单独存储正负号
        int sign = 1, start = 0;
        if (a.charAt(0) == '+' || a.charAt(0) == '-') {
            if (a.charAt(0) == '-') sign = -1;
            start++;
        }

        // 如果sum开始比极大值/10还大，就说明sum * 10一定会溢出
        // 如果sum等于极大值/10，但是如果马上叠加上的x比极大值的最低位(7)还要大，那么一定会溢出
        int sum = 0;
        for (int i = start; i < a.length(); i++) {
            int x = a.charAt(i) - '0';
            if (sum > Integer.MAX_VALUE / 10 || sum == Integer.MAX_VALUE / 10 && x > 7)
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            sum = sum * 10 + x;
        }
        return sum * sign;
    }
}
