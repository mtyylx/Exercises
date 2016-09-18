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
public class M151_Reverse_Words_in_String {
    public static void main(String[] args) {
        String a = "Hey guess what! I got my iPhone7 just now and I just keep coding this!";
        String x = "a";
        String b = reverseString2(a);
        System.out.println(b);
        System.out.println(reverseString(x));

    }

    /** 处理字符串类的问题，有几种专门的思路：
     *  1. 使用String类型提供的封装方法。例如substring/trim/split等等，可以极大简化代码逻辑（当然前提是面试时被允许使用）
     *  2. 反转字符串
     *  3. 转为char array后直接按数组方式处理，然后再转回String（因为String不可以被原位修改，必须借助于数组才可以）
     *
     *  但是同时要清楚，由于String类型在Java语言中本身就是Immutable的，因此用Java解决字符串问题理论上不可能做到空间复杂度o(1)，即In-place
     *  只是说对于同样的算法如果在C语言中就可以做到真正的原位运算。
     * */

    // 反转字符串解法，time - o(n), space - o(n)，优势在于逻辑上是in-place的
    // 不过由于Java的字符串不可以被原位修改，所以空间复杂度依然是o(n)。但如果同样的算法逻辑移植到C上就是真的o(1)
    // 基本思路：首先把字符串转为字符数组，然后反转整个数组，再挨个反转每个单词，最后返回字符串
    // 难点1：如何处理首尾多个空格的问题
    // 难点2：如何处理单词与单词之间的多个空格的问题
    static String reverseString2(String a) {
        char[] old = a.toCharArray();
        // 1. 反转整个字符串
        reverseWord2(old, 0, old.length - 1);
        // 2. 依次反转每个单词
        int i = 0;
        int j = 0;
        int l = old.length;
        while (j < l) {
            while (j < l && old[j] == ' ') j++;
            i = j;
            while (j < l && old[j] != ' ') j++;
            reverseWord2(old, i, j - 1);
        }
        j = 0;
        StringBuffer sb = new StringBuffer();
        while (j < l) {
            while (j < l && old[j] == ' ') j++;
            while (j < l && old[j] != ' ') sb.append(old[j++]);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    // 数组反转的更好写法：使用while循环（无需关心索引）
    private static void reverseWord2(char[] word, int start, int stop) {
        while (start < stop) {
            char temp = word[start];
            word[start++] = word[stop];
            word[stop--] = temp;
        }
    }

    // 数组反转的写法：使用for循环（需要折腾索引值）
    private static void reverseWord(char[] word, int start, int stop) {
        for (int i = start; i <= (start + stop) / 2; i++) {
            char temp = word[i];
            word[i] = word[stop - i];
            word[stop - i] = temp;
        }
    }

    // 双指针解法，同时使用了ArrayList / StringBuilder / String.trim()，
    // time - o(n)，space - o(n)
    static String reverseString(String a) {
        List<String> list = new ArrayList<>();
        int stop = 0;
        int start = 0;
        int l = a.length();
        while (stop < l) {
            while (stop < l && a.charAt(stop) == ' ') stop++;   // 先跳过任何连续的空格，直到遇到字符，说明遇到了一个单词
            start = stop;                                       // 更新单词起始点指针
            while (stop < l && a.charAt(stop) != ' ') stop++;   // 确定单词结束的位置
            list.add(a.substring(start, stop));                 // 将单词存入arraylist
        }
        StringBuilder sb = new StringBuilder();                 // 逆序将单词输出
        for (int i = list.size() - 1; i >= 0; i--)
            sb.append(list.get(i) + ' ');
        return sb.toString().trim();                            // 记得使用trim方法来移除新字符串的首尾空格
    }
}
