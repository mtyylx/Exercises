package com.leetcode.string;

/**
 * Created by Michael on 2016/9/26.
 * Implement strStr().
 * Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
 * 这里只是比喻，needle指的是两个字符串中比较短的那个，haystack则是指比较长的那个，
 * 需要实现的功能是判断长字符串是否包含短字符串，如果包含就返回第一次出现的起始索引，如果不包含就返回-1。
 * 本质上来讲，这就是String类型提供的indexOf()的功能，只不过让你自己动手实现罢了。
 *
 * Function Signature:
 * public int strStr(String a, String b) {...}
 */
public class E28_Implement_IndexOf {
    public static void main(String[] args) {
        System.out.println(indexOf("dfg", "ghkjhlkjasdfdxfg;lkj;jjpaidfdg"));
    }

    // Another Shorter Version of Brute Force Solution
    // 该写法巧妙的把判断条件也写进了for循环的循环条件里，这样可以确保，当
    static int indexOf2(String needle, String haystack) {
        if (needle.length() == 0) return 0;
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            for (int j = 0; j < needle.length() && needle.charAt(j) == haystack.charAt(i + j); j++)
                if (j == needle.length()) return i;
        }
        return -1;
    }

    // Brute Force: time - o(n^2), space - o(1)
    // 手动实现substring和equals，以短字符串的长度作为滑动窗的长度
    static int indexOf(String needle, String haystack) {
        int idx = -1;
        int len = needle.length();
        for (int i = 0; i <= haystack.length() - len; i++) {
            idx = i;
            for (int j = 0; j < len; j++) {
                if (needle.charAt(j) != haystack.charAt(i + j)) {
                    idx = -1;
                    break;
                }
            }
            if (idx != -1) return idx;
        }
        return idx;
    }
}
