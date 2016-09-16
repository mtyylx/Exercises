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
        String a = "Hey guess what! I got my iPhone7 just now and I just keep coding this!";
        String x = "a";
        String b = reverseString(a);
        System.out.println(b);
        System.out.println(reverseString(x));
    }

    /** 处理字符串类的问题，有几种专门的思路：
     *  1. 使用String类型提供的封装方法。例如substring/trim/split等等，可以极大简化代码逻辑（当然前提是面试时被允许使用）
     *  2. 反转字符串
     *  3. 转化成为char array后直接按照数组方式处理
     *
     *  但是同时要清楚，由于String类型在Java语言中本身就是Immutable的，因此用Java解决字符串问题理论上不可能做到空间复杂度o(1)，即In-place
     *  只是说对于同样的算法如果在C语言中就可以做到真正的原位运算。
     * */

    // 反转字符串解法，优势是真正做到了in-place，time - o(n), space - o(n) 但如果同样的算法逻辑移植到C上就是真的o(1)
    static String reverseString2(String a) {

    }

    private String reverseWord()

    // 双指针解法，同时使用了ArrayList/StringBuilder，time - o(n)，space - o(n)
    // 显然不是最优的解法，处理的比较笨拙，需要单独处理结尾有可能带上空格的情况。
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
