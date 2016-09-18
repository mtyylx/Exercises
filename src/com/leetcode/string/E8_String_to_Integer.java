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
        String a = "  -2147483649alkjfjj  ";
        System.out.println(atoi(a));
    }

    // 需要学习下检测overflow的机制
    // 有哪些可能的非法输入：
    // 1. null string
    // 2. empty string
    // 3. string that does not have 0~9, -, +,
    // 4. string that has additional char after a valid digit
    // 5. value overflow
    static int atoi(String a) {
        if (a == null || a.length() == 0) return 0;
        int l = a.length();
        int i = 0;
        boolean valid = false;
        while (i < l) {
            if      (a.charAt(i) == ' ') i++;
            else if (a.charAt(i) != '+' && a.charAt(i) != '-' && a.charAt(i) < '0' && a.charAt(i) > '9') i++;
            else {
                valid = true;
                break;
            }
        }
        if (!valid) return 0;

        int j = i;
        valid = false;
        // start with +/-
        if (a.charAt(j) == '+' || a.charAt(j) == '-') j++;
        // should only contain digits
        while (j < l && a.charAt(j) >= '0' && a.charAt(j) <= '9') {
            valid = true;
            j++;
        }
        if (!valid) return 0;

        String digit;
        boolean neg = false;
        if (a.charAt(i) == '+') digit = a.substring(i + 1, j);
        else if (a.charAt(i) == '-') {
            digit = a.substring(i + 1, j);
            neg = true;
        }
        else digit = a.substring(i, j);

        if (digit.length() > 10 || digit.length() == 10 && digit.charAt(0) > '2') {
            if (neg) return Integer.MIN_VALUE;
            else return Integer.MAX_VALUE;
        }

        int sum = 0;
        for (int idx = digit.length() - 1; idx >= 0; idx--) {
            if (neg) sum -= (digit.charAt(idx) - '0') * (int)Math.pow(10, digit.length() - 1 - idx);
            else sum += (digit.charAt(idx) - '0') * (int)Math.pow(10, digit.length() - 1 - idx);
        }
        if      (neg && sum > 0) return Integer.MIN_VALUE;
        else if (!neg && sum < 0) return Integer.MAX_VALUE;
        return sum;
    }
}
