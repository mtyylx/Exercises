package com.leetcode.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/9/16.
 * Given an input string, reverse the string word by word.
 *
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 *
 * Clarification:
 * 1. What constitutes a word?
 *      A sequence of non-space characters constitutes a word.
 * 2. Could the input string contain leading or trailing spaces?
 *      Yes. However, your reversed string should not contain leading or trailing spaces.
 * 3. How about multiple spaces between two words?
 *      Reduce them to a single space in the reversed string.
 *
 * Function Signature:
 * public String reverseString(String a) {...}
 **/
public class E151_Reverse_Words_in_String {
    public static void main(String[] args) {
        String a = "Hey guess what! I got my iPhone7 today and I just keep coding this!";
        String x = "a";
        String b = reverseString(a);
        System.out.println(b);
        System.out.println(reverseString(x));
    }

    // 双指针解法，同时使用了ArrayList/StringBuilder，time - o(n)，space - o(n)
    // 处理的比较笨拙，需要单独处理结尾有可能带上空格的情况。
    static String reverseString(String a) {
        List<String> list = new ArrayList<>();
        int stop = 0;
        int start = 0;
        while (stop < a.length()) {
            while (stop < a.length() && a.charAt(stop) == ' ') {
                stop++;
                start = stop;
            }
            while (stop < a.length() && a.charAt(stop) != ' ') {
                stop++;
            }
            if (start < stop) list.add(a.substring(start, stop));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
            sb.append(' ');
        }
        String result = sb.toString();
        if (result.length() > 1 && result.charAt(result.length() - 1) == ' ')
            return result.substring(0, result.length() - 1);
        else return sb.toString();
    }
}
