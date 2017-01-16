package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by LYuan on 2016/9/1.
 *
 * Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd".
 * We can keep "shifting" which forms the sequence: "abc" -> "bcd" -> ... -> "xyz"
 * Given a list of strings which contains only lowercase alphabets,
 * group all strings that belong to the same shifting sequence.
 *
 * For example, given: ["abc", "bcd", "abcd", "xyz", "az", "ba", "a", "z"],
 * Return:
 * [
 *  ["abc", "bcd", "xyz"],
 *  ["az", "ba"],
 *  ["abcd"],
 *  ["a", "z"]
 * ]
 *
 * Function Signature:
 * public List<List<String>> groupShiftString(String[] a) {...}
 *
 * <Tags>
 * - HashMap: Key → Pattern, Value → BucketID。哈希表用作桶归类的辅助。(与M49所采用的方法完全一致)
 * - 进制变换：加进制数再求余，可将任何值映射到[0, 进制数 - 1]的区间内。(c[i] - c[0] + N) % N
 *
 */
public class E249_Group_Shifted_String {
    public static void main(String[] args) {
        String[] a = {"rst", "opq", "abc", "bcd", "abcd", "xyz", "az", "ba", "a", "z", "yza"};
        List<List<String>> result = groupShiftString(a);
    }

    /** 解法1：HashMap桶归类 + 进制变换。Time - o(n). Space - o(c). */
    /** 关键点1：Hash Map桶归类，与M49思路完全一样。*/
    // 每一组属于同一模板的字符串都视为一个桶，然后使用HashMap实现桶的分类，模板本身作为Key，桶的编号作为Value。
    // 需要小心的是，数组引用不能作为HashMap的Key。
    // 这是因为数组的hashcode并不是通过数组内容计算出来的，而是从数组引用计算得到，因此即使对于两个内容完全相等的数组，其hashcode依然是不同的
    // 相比之下，String或者List<>都可以做HashMap的Key，因为它们的HashCode都是按照其具体内容计算出来的，因此如果内容相同，就一定指向同一个Key
    // 这就是为什么这里的getPattern方法最后返回的是String类型。

    /** 关键点2：处理a-to-z的循环关系 - 加进制数后求余。*/
    // 首先能想到的肯定是求余。但是求余之前需要归一化。
    // 于是需要把整个字符串都归一化到同一起点再求余：比如全都减掉第一个字符，就可以将任何字符串都归一化为以a打头的pattern
    // abc  →  0  1  2   (?-a)→   0   1   2   +26→   26 27 28   %26→  0 1 2
    // yza  →  24 25 0   (?-y)→   0   1 -24   +26→   26 27 2    %26→  0 1 2
    // zab  →  25 0  1   (?-z)→   0 -25 -24   +26→   26 1  2    %26→  0 1 2
    // 然后我们就发现，光减掉首字符还不行，因为减完之后可能会出现负值，求余之后值不对。
    // 这时候我们发现求余运算是周期折叠的，也就是说2%26和(26+2)%26是一样的，因此为了避免负数，可以全都加上进制数26之后，再求余，最后的值一定在[0,25]范围之内
    // 这样我们就得到了进制变换的公式：(c[i] - c[0] + 26) % 26
    // 可以推广大到任何进制：(c[i] - c[0] + N) % N

    static List<List<String>> groupShiftString(String[] s) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (String x : s) {
            String p = getPattern2(x);
            if (!map.containsKey(p)) {
                result.add(new ArrayList<>());
                map.put(p, result.size() - 1);
            }
            result.get(map.get(p)).add(x);
        }
        return result;
    }

    /** 新方法：将所有字符都用首字符归一化，简洁清晰。*/
    // 关键在于 (a.charAt(i) - a.charAt(0) + 26) % 26
    static String getPattern2(String a) {
        if (a == null || a.length() == 0) return "";                        // 访问a.charAt(0)之前做好保护工作
        char[] c = new char[a.length()];
        for (int i = 0; i < c.length; i++)
            c[i] = (char) ((a.charAt(i) - a.charAt(0) + 26) % 26 + 'a');    // 加上'a'是为了让得到的pattern也是可读的字符串
        return new String(c);
    }

    /** 早期解法：采用<相邻字符依次求差>的方式归一化整个字符串，比较长。*/
    // 关键在于 (a.charAt(i) - a.charAt(i - 1) + 26) % 26
    static String getPattern(String a) {
        char[] base = new char[a.length()];
        base[0] = 'a';
        for (int i = 1; i < base.length; i++)
            base[i] = (char)((a.charAt(i) - a.charAt(i - 1) + 26) % 26 + base[i - 1]);
        return Arrays.toString(base);
    }
}
