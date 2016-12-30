package com.leetcode.string;

/**
 * Created by Michael on 2016/12/30.
 *
 * Given an input string, reverse the string word by word. A word is defined as a sequence of non-space characters.
 * The input string does not contain leading or trailing spaces and the words are always separated by a single space.
 *
 * For example, given s = "the sky is blue", return "blue is sky the".
 * Could you do it in-place without allocating extra space?
 * Related problem: Rotate Array
 *
 * Function Signature:
 * public void reverseWords(char[] a) {...}
 *
 * <系列问题>
 * E189 Rotate Array    : 给定一个数组，和偏移值k，求数组向右平移k次的结果。
 * M151 Reverse Words 1 : 给定一个字符串，按单词顺序翻转整个字符数组。字符串可能含有首部或尾部空格，单词之间可能有多个空格。（非原位）
 * M186 Reverse Words 2 : 给定一个字符数组，按单词顺序翻转整个字符数组。字符数组不含首部或尾部空格，单词之间只有一个空格。（原位）
 *
 * <Tags>
 * - Multiple Reverse: Two Pointers
 *
 */
public class M186_Reverse_Words_2 {
    public static void main(String[] args) {
        char[] a = "Tomorrow is the last day of 2016".toCharArray();
        reverseWords(a);
        System.out.println(new String(a));
        char[] b = "I have learned a lot in this year. Goodbye 2016!".toCharArray();
        reverseWords(b);
        System.out.println(new String(b));
    }

    /** 最佳解法：多次翻转法。先分段翻转，再整体翻转（或者翻过来也一样）。Time - o(n), Space - o(1) */
    // 由于题目已经明确规定数组一定会以字符开头和结尾，而且每个单词之间只有一个空格，因此我们需要处理的边界情况就简单了很多。
    // 扫描整个数组的过程中，只有两种情况需要调用翻转方法，一个是当前字符是数组最后一个字符，一个是当前字符是单词的最后一个字符。
    // 每翻转完一次，就更新下一个单词的起始位置，因为单词之间只有一个空格，因此start = i + 2
    // the sky is blue -> eht yks si eulb -> blue is sky the
    static void reverseWords(char[] a) {
        int start = 0;
        for (int i = 0; i < a.length; i++) {
            if (i + 1 == a.length || a[i + 1] == ' ') {     // 如果i是最后一个字符 或 i是一个单词的最后一个字符，就翻转该单词
                reverse(a, start, i);
                start = i + 2;                              // 缓存下一个单词的起点位置，即使越界也无所谓
            }
        }
        reverse(a, 0, a.length - 1);               // 最后翻转整个数组
    }

    static void reverse(char[] s, int left, int right) {
        while (left < right) {
            char temp = s[left];
            s[left++] = s[right];
            s[right--] = temp;
        }
    }
}
