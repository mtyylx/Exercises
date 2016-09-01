package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by LYuan on 2016/9/1.
 * Given a string, we can "shift" each of its letter to its successive letter,
 * For example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:
 * "abc" -> "bcd" -> ... -> "xyz"
 * Given a list of strings which contains only lowercase alphabets,
 * group all strings that belong to the same shifting sequence.
 *
 * For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
 * Return:
 * [
 *  ["abc","bcd","xyz"],
 *  ["az","ba"],
 *  ["acef"],
 *  ["a","z"]
 * ]
 *
 * Note:
 * For the return value, each inner list's elements must follow the lexicographic order.
 *
 * Function Signature:
 * public List<List<String>> groupString(String[] a) {...}
 * */
public class E249_Group_Shifted_String {
    public static void main(String[] args) {
        String[] a = {"rst", "opq", "abc", "bcd", "acef", "xyz", "az", "ba", "a", "z", "yza"};
        List<List<String>> result = groupString(a);
    }

    // 哈希表解法，o(n^2)
    // 难点一：如何定义哈希表的键值含义
    // 难点二：如何处理a至z的循环偏移关系
    // 难点三：如何保证每个子list都按字母顺序排序

    // 难点一：哈希表中应该存的键是每一种特殊组合的字符串的模板，以a打头，撑死了有26种变体。
    // 然后哈希表中应该存的值则是每种独特模板的ArrayList索引值
    // 判断一个字符串属于那种模板，本质上是比较这个字符串相邻两个元素之间ASCII值之差构成的数组是否与存在哈希表中的模板数组匹配。
    // 这里有一个坑，就是你不能将数组作为哈希表的键。
    // 因为你实际存的将会是一个数组的内存指针，而不是数组内容，所以即使两个数组一样，但是由于他们的内存地址不同，因此也无法使用哈希表匹配到。
    // 所以你必须把这个特征数组转化成为字符串再存成哈希表的键。
    // 但是还有一个坑，因为数字可以有多位，因此数组转换成为字符串会出现{12, 3} 和 {1, 23}相同的问题
    // 因此你不能直接把数组转化成为字符串，而是要把数组每个元素变成ASCII字符才行，这样才能确保每个元素都只占一位。

    // 难点二：相邻元素求差值会有正有负，例如'yza'其实和'xyz'一样，但是直接减就变成了[0, 1, -25] vs [0, 1, 1]
    // 需要使用加26求余的方法，因为最大差值不会超过26，所以加上26可以确保值恒正，再求余就可以回到0~25范围。
    // 于是就有了下面的代码，可以把任何一个字符串转化成为一个以'a'打头的基础特征。
    // base[i] = (char)((a.charAt(i) - a.charAt(i - 1) + 26) % 26 + base[i - 1]);

    // 难点三：就是针对List<>接口的内容排序，需要使用Collections类提供的sort，而不是Arrays的。
    static List<List<String>> groupString(String[] a) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Integer> pattern = new HashMap<>();
        int pattern_count = 0;
        for (String s : a) {
            String temp = extractPattern(s);
            if (pattern.containsKey(temp))
                result.get(pattern.get(temp)).add(s);
            else {
                pattern.put(temp, pattern_count);
                result.add(new ArrayList<>());
                result.get(pattern_count).add(s);
                pattern_count++;
            }
        }
        // Sort each array
        for (List<String> list : result) {
            Collections.sort(list);
        }

        return result;
    }

    private static String extractPattern(String a) {
        char[] base = new char[a.length()];
        base[0] = 'a';
        for (int i = 1; i < base.length; i++) {
            base[i] = (char)((a.charAt(i) - a.charAt(i - 1) + 26) % 26 + base[i - 1]);
        }
        return Arrays.toString(base);
    }
}
