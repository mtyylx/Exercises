package com.leetcode.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
 * public String reverseString1(String a) {...}
 *
 * <系列问题>
 * E189 Rotate Array    : 给定一个数组，和偏移值k，求数组向右平移k次的结果。
 * M151 Reverse Words 1 : 给定一个字符串，按单词顺序翻转整个字符数组。字符串可能含有首部或尾部空格，单词之间可能有多个空格。（非原位）
 * M186 Reverse Words 2 : 给定一个字符数组，按单词顺序翻转整个字符数组。字符数组不含首部或尾部空格，单词之间只有一个空格。（原位）
 *
 * <Tags>
 * - Multiple Reverse: Two Pointers
 * - String Format: Trim leading and trailing space, duplicate space, etc.
 *
 */
public class M151_Reverse_Words {
    public static void main(String[] args) {
        System.out.println(reverseString1(" Hey  guess what! I got my iPhone7 just now and I   just keep coding this!  "));
        System.out.println(reverseString2(" Hey  guess what! I got my iPhone7 just now and I   just keep coding this!  "));
        System.out.println(reverseString3(" Hey  guess what! I got my iPhone7 just now and I   just keep coding this!  "));
        System.out.println(reverseStringX(" Hey  guess what! I got my iPhone7 just now and I   just keep coding this!  "));
    }

    /** 处理字符串类的问题，有几种专门的思路：
     *  1. 使用String类型提供的封装方法。例如substring / trim / split等等，可以极大简化代码行数（当然前提是面试时被允许使用）
     *  2. 反转字符串，经常会有很神奇的效果。
     *  3. 转为char[]，转化为数组问题。使用数组方式处理，最后转回String（因为String不可以被原位修改，必须借助于数组才可以）
     *
     *  要清楚，Java语言中的String是Immutable的，
     *  因此想要在Java中解决字符串问题理论上是不可能做到空间复杂度o(1)的。即：Java中不可能原位修改String，一定需要依赖额外空间。
     *  只有将同样的算法用在C语言中，才可以做到真正的原位运算。
     * */

    /** 更快速的解法：多次翻转 + 实时平移。*/
    // 相比于解法2，省略了对字符串格式化处理的过程，因为这个解法中，翻转单词和字符串格式化清理是同时进行的。
    // 核心提升：在翻转单词前，先把单词拷贝至合适的位置（即刨除了首尾中间空格，没有水分的位置）
    static String reverseString3(String a) {
        char[] c = a.toCharArray();
        int len = c.length;
        reverse(c, 0, len - 1);
        int start = 0;  // 单词起点指针
        int end = 0;    // 单词终止指针
        for (int i = 0; i < len; i++) {
            if (c[i] != ' ') c[end++] = c[i];       // 如果单词未结束，则直接拷贝至合适位置
            else if (i > 0 && c[i - 1] != ' ') {    // 如果单词已经结束，且前一个字符也不是空格，就翻转这个单词，然后填上一个空格，并更新新单词的起始位置。
                reverse(c, start, end - 1);
                c[end++] = ' ';
                start = end;
            }
            // 如果是空格，但是前一个单词也是空格，就跳过
        }
        reverse(c, start, end - 1);                // 处理最后一个单词的移动
        return new String(c).substring(0, end).trim();  // 截取有效部分返回，注意去掉尾部空格
    }


    /** 解法2：多次翻转法。Time - o(n), Space - o(1). */
    // 这种解法在逻辑上是in-place的，不过由于Java的字符串不可以被原位修改，所以空间复杂度依然是o(n)。但如果同样的算法逻辑移植到C上就是真的o(1)
    // 基本思路：首先把字符串转为字符数组，然后反转整个数组，再挨个反转每个单词，最后返回字符串
    // 区别M186的地方在于这里需要花精力处理各种corner case：
    // 难点1：如何处理首尾多个空格的问题
    // 难点2：如何处理单词与单词之间的多个空格的问题
    static String reverseString2(String a) {
        char[] c = a.toCharArray();
        reverse(c, 0, c.length - 1);
        int start = 0;
        int i = 0;
        int len = a.length();
        while (i < len) {
            while (i < len && c[i] == ' ') i++;     // 确定单词起点
            start = i;                              // 即使这是i已经越界没关系，因为下一个while会自动跳过，且reverse在指针交错时会直接退出
            while (i < len && c[i] != ' ') i++;     // 确定单词终点
            reverse(c, start, i - 1);          // 翻转单词（如果起点和终点合法）
        }
        // 字符串格式化：清理多余的首尾和中间空格
        i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < len) {
            while (i < len && c[i] == ' ') i++;                 // 找到第一个字符
            while (i < len && c[i] != ' ') sb.append(c[i++]);   // 拷贝单词直至遇到空格
            sb.append(' ');
        }
        return sb.toString().trim();    // 去掉尾部空格
    }

    // 数组区间反转1：双指针交错
    private static void reverse(char[] a, int left, int right) {
        while (left < right) {
            char temp = a[left];
            a[left++] = a[right];
            a[right--] = temp;
        }
    }

    // 数组区间翻转2：中点左右交换
    private static void reverse2(char[] a, int left, int right) {
        for (int i = left; i <= (left + right) / 2; i++) {
            char temp = a[i];
            a[i] = a[right - i];
            a[right - i] = temp;
        }
    }

    /** 解法1：双指针解 + 缓存ArrayList。Time - o(n), Space - o(n) */
    // 需要使用额外的空间存储所有识别的单词，最后逆序输出。
    static String reverseString1(String a) {
        List<String> list = new ArrayList<>();
        int stop = 0;
        int start = 0;
        int l = a.length();
        while (stop < l) {
            while (stop < l && a.charAt(stop) == ' ') stop++;   // 先跳过任何连续的空格，直到遇到字符，说明遇到了一个单词
            start = stop;                                       // 更新单词起始点指针
            while (stop < l && a.charAt(stop) != ' ') stop++;   // 确定单词结束的位置
            list.add(a.substring(start, stop));                 // 将单词缓存
        }
        StringBuilder sb = new StringBuilder();                 // 逆序将单词输出
        for (int i = list.size() - 1; i >= 0; i--)
            sb.append(list.get(i) + ' ');
        return sb.toString().trim();                            // 记得使用trim方法来移除新字符串的首尾空格
    }

    /** 解法x：纯用Java类库完成，三行就完事。 */
    static String reverseStringX(String a) {
        String[] s = a.split(" +");         // 正则表达式按至少一个空格作为delimiter
        Collections.reverse(Arrays.asList(s));    // 为了用Collections提供的翻转API，不惜把数组转成List
        return String.join(" ", s);               // 用单个空格作为delimiter合并单词
    }
}
