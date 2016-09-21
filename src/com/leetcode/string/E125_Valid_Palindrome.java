package com.leetcode.string;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/9/21.
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 *
 * For example,
 * "A man, a plan, a canal: Panama" is a palindrome.
 * "race a car" is not a palindrome.
 *
 * Note:
 * 1. Have you consider that the string might be empty? This is a good question to ask during an interview.
 * 2. For the purpose of this problem, we define empty string as valid palindrome.
 *
 * Function Signature:
 * public boolean isValidPalindrome(String a) {...}
 */
public class E125_Valid_Palindrome {
    public static void main(String[] args) {
        String a = "45ab, cd efghijklm**())nmlk  jih,.,.,gfedcba4";
        System.out.println(isValidPalindrome(a));
    }

    // 如果只是简单的干净字符串判断回文，其实用反转字符串+比较就行了。

    // 双指针解法
    // 只检查alphanumeric，忽略一切其他字符
    // String.toLowerCase()只对字母有效果，对于其他非null字符都没有效果
    // 判断一个字符是否属于字母或者是否是数字可以用Character.isLetterOrDigit()快速完成
    static boolean isValidPalindrome(String a) {
        if (a == null || a.length() == 0) return true;
        int i = 0;
        int j = a.length() - 1;
        a = a.toLowerCase();
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(a.charAt(i))) i++;
            while (i < j && !Character.isLetterOrDigit(a.charAt(j))) j--;
//            while (i < j && !(a.charAt(i) >= 'a' && a.charAt(i) <= 'z') && !(a.charAt(i) >= '0' && a.charAt(i) <= '9')) i++;
//            while (i < j && !(a.charAt(j) >= 'a' && a.charAt(j) <= 'z') && !(a.charAt(j) >= '0' && a.charAt(j) <= '9')) j--;
            if (i < j && a.charAt(i) != a.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
