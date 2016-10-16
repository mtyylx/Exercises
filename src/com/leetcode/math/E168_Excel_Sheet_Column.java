package com.leetcode.math;

/**
 * Created by Michael on 2016/10/16.
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.
 *
 * For example:
 * 1 -> A
 * 2 -> B
 * 3 -> C
 * ...
 * 26 -> Z
 * 27 -> AA
 * 28 -> AB
 *
 * Function Signature:
 * public String columnToTitle(int n) {...}
 *
 * 本质是进制转换问题。核心在于<先偏移求模再补偿>。
 * 相同类型题目汇总：
 * E258 - 9进制 (1-9)
 * E168 - 26进制 (1-26)
 *
 */
public class E168_Excel_Sheet_Column {
    public static void main(String[] args) {
        for (int i = 1; i < 1000; i++) {
            System.out.println(i + ": " + columnToTitle2(i));
        }
    }

    /** 进制转换题目，偏移取模解法 */
    // 特别是对于不包含0的进制，最优的解法是先偏移1位再求模，最后根据情况补偿回来。
    // 对于这道题由于直接对应26个字符本身，因此不需要补偿回来。但是记得每次取模和除进制的时候都需要减一。
    // 1 2 3 ... 25 26 映射到：
    // 0 1 2 ... 24 25 映射到：
    // A B C ... Y  Z
    static String columnToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            char digit = (char) ((n - 1) % 26 + 'A');
            sb.append(digit);
            n = (n - 1) / 26;
        }
        return sb.reverse().toString();
    }

    /** 递归写法 */
    // 递归终止条件：n < 1
    // 正序递归，递归的过程求解当前位的字符。
    static String columnToTitle2(int n) {
        return (n < 1) ? "" : columnToTitle2((n - 1) / 26) + (char) ((n - 1) % 26 + 'A');
    }
}
