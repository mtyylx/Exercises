package com.leetcode.string;

/**
 * Created by LYuan on 2016/9/18.
 * Write a function that takes a string as input and returns the string reversed.
 *
 * Example:
 * Given s = "hello", return "olleh".
 *
 * Function Signature:
 * public String reverse(String a) {...}
 */
public class E344_Reverse_String {
    public static void main(String[] args) {
        String a = "1234567890";
        System.out.println(reverse(a));
        System.out.println(reverse2(a));
    }

    // 解法2：转化为数组反转问题
    static String reverse2(String a) {
        if (a == null || a.length() == 0) return a;
        char[] c = a.toCharArray();
        int i = 0;
        int j = c.length - 1;
        while (i < j) {
            char temp = c[i];
            c[i++] = c[j];
            c[j--] = temp;
        }
        return new String(c);
    }

    // 解法1：使用StringBuilder反向扫描追加字符串
    static String reverse(String a) {
        if (a == null || a.length() == 0) return a;
        StringBuilder sb = new StringBuilder(a.length());
        for (int i = a.length() - 1; i >= 0; i--)
            sb.append(a.charAt(i));
        return sb.toString();
    }

    // 解法0：使用StringBuilder原生reverse方法
    static String reverse0(String a) {
        return new StringBuilder(a).reverse().toString();
    }
}
