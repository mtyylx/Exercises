package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2016/8/29.
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 * All occurrences of a character must be replaced with another character while preserving the order of characters.
 * No two characters may map to the same character but a character may map to itself.
 *
 * For example,
 * Given "egg", "add", return true.
 * Given "foo", "bar", return false.
 * Given "paper", "title", return true.
 *
 * Note:
 * You may assume both s and t have the same length.
 *
 * Function Signature:
 * public boolean isIsomorphic(String a, String b) {...};
 * */
public class E205_Isomorphic_String {
    public static void main(String[] args) {
        System.out.println(isIsomorphic2("aabbccabc", "xxyyzzzyx"));
    }

    // Value as Index解法，o(n)
    // 这个算法的核心在于：如果相同位置的两个字符上次出现的位置也相同，那么两个字符串一定相似。
    // abccba <-> 123321 中， 第二个c和3中的值都是3，即上次都是一起出现的。
    // 之所以存的内容是上次出现的索引值i + 1, 是为了避免在第0个元素出现的时候记录与初始化的全0没法区分。
    static boolean isIsomorphic2(String a, String b) {
        int[] map1 = new int[256];
        int[] map2 = new int[256];
        for (int i = 0; i < a.length(); i++) {
            if (map1[a.charAt(i)] != map2[b.charAt(i)]) return false;
            map1[a.charAt(i)] = i + 1;
            map2[b.charAt(i)] = i + 1;
        }
        return true;
    }

    // 哈希表解法，o(n^2)：因为for循环（复杂度为o(n)）内嵌哈希表的containsKey/containsValue操作（复杂度为o(n)）
    // 注意对于哈希表来说，真正计算复杂度为o(1)的操作只有put和get。
    // 核心思路在于抓住：要保证a与b的每位字符串都是一一映射，不允许一对多和多对一的映射关系。
    // a中键已经存在，但是值却与b的值不同 -> 一对多出现
    // a中键不存在，但是b的值已经存在了 -> 多对一出现
    static boolean isIsomorphic(String a, String b) {
        if (a.length() != b.length()) return false;

        Map<Character, Character> map = new HashMap<>(a.length());
        for (int i = 0; i < a.length(); i++) {
            if (map.containsKey(a.charAt(i)) && map.get(a.charAt(i)) != b.charAt(i)) return false;
            if (!map.containsKey(a.charAt(i)) && map.containsValue(b.charAt(i))) return false;
            map.put(a.charAt(i), b.charAt(i));
        }
        return true;
    }
}
