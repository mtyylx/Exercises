package com.leetcode.string;

/**
 * Created by LYuan on 2016/9/21.
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ',
 * return the length of last word in the string.
 * If the last word does not exist, return 0.
 *
 * Note: A word is defined as a character sequence consists of non-space characters only.
 * For example, Given s = "Hello World", return 5.
 *
 * Function Signature:
 * public int lengthLastWord(String a) {...}
 */
public class E58_Length_of_Last_Word {
    public static void main(String[] args) {
        String a = "";
        System.out.println(lengthLastWord2(a));
    }

    // 使用String.split()快速解决问题
    static int lengthLastWord2(String a) {
        if (a == null) return 0;
        String[] list = a.split(" ");
        return list.length == 0 ? 0 : list[list.length - 1].length();
    }

    // 什么情况last word会不存在：字符串长度为0或为null或只有空格
    static int lengthLastWord(String a) {
        if (a == null || a.length() == 0) return 0;
        int result = 0;
        int i = a.length() - 1;
        while (i >= 0 && a.charAt(i) == ' ') i--;
        for (int j = i; j >= 0; j--) {
            if (a.charAt(j) != ' ') result++;
            else return result;
        }
        return result;
    }
}
