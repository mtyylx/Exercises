package com.leetcode.string;

/**
 * Created by Michael on 2016/9/25.
 * Given a string S, find the longest palindromic substring in S.
 * You may assume that the maximum length of S is 1000,
 * and there exists <one unique> longest palindromic substring.
 *
 * Function Signature:
 * public static String longestPalindromeSubString(String a) {...}
 * */
public class M5_Longest_Palindrome_Substring {
    public static void main(String[] args) {
        System.out.println(longestPalindromeSubstring2("ab"));
    }

    // 寻找回文的两种方式：
    // 1. 缩小逼近方式：首尾指针不断缩小，直到检测到字符相等，认定有可能是回文，接着缩小，如果指针相遇则是回文，如果未相遇就发现不同则不是回文
    // 2. 中点扩张方式：奇偶情况分开讨论，从一个点或两个点开始同步增长左右指针，直到遇到字符不同，就是围绕这个中点的最大回文。
    // 可以看到，第二种方法比第一种好实现的多，因为中点扩张方式只需要找不等的时候退出，而缩小逼近则需要先判断相等，再判断不等或重叠，比较麻烦

    // 中点扩张方式（Expand Around Center）
    private static int maxStart = 0;
    private static int maxLen = 1;
    static String longestPalindromeSubstring2(String a) {
        for (int i = 0; i < a.length() - 1; i++) {
            expandAroundCenter(a, i, i);       //奇数中点情况
            expandAroundCenter(a, i, i + 1);   //偶数中点情况
        }
        return a.substring(maxStart, maxStart + maxLen);
    }

    // while只管增减左右指针，退出时left和right各分别向左右多走了一步，因此实际回文长度应该减2，同时由于个数等于差值加1，
    // 所以实际是right - left - 2 + 1 = right - left - 1
    private static void expandAroundCenter(String a, int left, int right) {
        while (left >= 0 && right < a.length() && a.charAt(left) == a.charAt(right)) { left--; right++; }
        if (right - left - 1 > maxLen) {
            maxLen = right - left - 1;
            maxStart = left + 1;
        }
    }

    // 缩小逼近方式
    // 由于最大回文可能出现在字符串的任何区间，因此双指针的首尾都需要动态扫描，遇到了首尾指针一样的情况，需要辨认是否这个子字符串内部是回文
    // 所以双层for循环加上额外的回文判断循环，时间复杂度是o(n^3)
    static String longestPalindromeSubstring(String a) {
        String result = a.substring(0, 1);
        for (int i = 0; i < a.length(); i++) {
            for (int j = a.length() - 1; j > i; j--) {
                if (a.charAt(j) == a.charAt(i) && isPalindrome(a.substring(i, j + 1)) && result.length() < j - i + 1)
                    result = a.substring(i, j + 1);
            }
        }
        return result;
    }

    private static boolean isPalindrome(String a) {
        int i = 0;
        int j = a.length() - 1;
        while (i < j)
            if (a.charAt(i++) != a.charAt(j--)) return false;
        return true;
    }
}
