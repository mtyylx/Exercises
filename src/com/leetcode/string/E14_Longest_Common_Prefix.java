package com.leetcode.string;

/**
 * Created by Michael on 2016/9/20.
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * Function Signature:
 * public String longestPrefix(String[] str) {...}
 */
public class E14_Longest_Common_Prefix {
    public static void main(String[] args) {
        String[] str = {"abc", "abcde", ""};
        System.out.println(longestPrefix2(str));
    }

    // 利用String.indexOf()这个API来简化代码
    // 假设第一个字符串就是prefix，依次遍历其他字符串，
    // 只要当前字符串中不包含第一个字符串或者包含第一个字符串但是不是打头的，就把prefix缩小一位
    // 需要注意indexOf(String)的用法：如果不存在String，那么返回-1，如果存在多个，那么返回起始Index最小值。
    static String longestPrefix2(String[] str) {
        if (str == null || str.length == 0) return "";
        String prefix = str[0];
        for (int i = 1; i < str.length; i++) {
            while (str[i].indexOf(prefix) != 0)
                prefix = prefix.substring(0, prefix.length() - 1);
        }
        return prefix;
    }

    // 常规解法，依次扫描字符串数组每个字符串的第i个元素，直到有的字符串与其他字符串不一样或长度耗尽就停止。
    static String longestPrefix(String[] str) {
        if (str == null || str.length == 0) return "";
        int count = 0;
        char temp = 0;
        while (true) {
            if (count < str[0].length()) temp = str[0].charAt(count);
            else return str[0].substring(0, count);
            for (int i = 0; i < str.length; i++) {
                if (count < str[i].length() && str[i].charAt(count) == temp);
                else return str[i].substring(0, count);
            }
            count++;
        }
    }
}
