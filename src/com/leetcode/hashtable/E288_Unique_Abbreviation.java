package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by LYuan on 2016/9/2.
 * An abbreviation of a word follows the form <first letter><number><last letter>.
 * Below are some examples of word abbreviations:
 * a) it                      --> it    (no abbreviation)
 * b) d|o|g                   --> d1g
 * c) i|nternationalizatio|n  --> i18n
 * d) l|ocalizatio|n          --> l10n
 * Assume you have a dictionary and given a word,
 * find whether its abbreviation is unique in the dictionary.
 * A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.
 *
 * Example:
 * Given dictionary = [ "deer", "door", "cake", "card" ]
 * isUnique("dear") -> false
 * isUnique("cart") -> true
 * isUnique("cane") -> false
 * isUnique("make") -> true
 *
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
 * vwa.isUnique("Word");
 * vwa.isUnique("anotherWord");
 *
 * Function Signature:
 * public ValidWordAbbr(String[] dictionary) {...}
 * public boolean isUnique(String word) {...}
 */
public class E288_Unique_Abbreviation {
    public static void main(String[] args) {
        E288_Unique_Abbreviation dict = new E288_Unique_Abbreviation(new String[]{"deer", "door", "cake", "card"});
        System.out.println(dict.isUnique("dear")); // false
        System.out.println(dict.isUnique("cart")); // true
        System.out.println(dict.isUnique("cane")); // false
        System.out.println(dict.isUnique("make")); // true
    }

    // 哈希表解法，难点在于如何处理dictionary中出现多个不同的字符串具有相同pattern时如何返回false
    // 题目没有说清楚什么时候给的word叫unique：有以下几种情况
    // 1. word的pattern根本dictionary中就没有，即：!map.containsKey(pattern)
    // 2. word的pattern在dictionary中有，但是dictionary中所有pattern和word一样的字符串都和word拼写一模一样。即：word.equals(map.get(pattern))
    // 将每个字符串的pattern作为Key，原始字符串作为Value，
    // 在扫描入库dictionary的时候就统计是否有不同Value对应一个Key的情况出现，
    // 如果有的话，就把这个Value设为一个不可能与任何word一样的值，避免被匹配到
    private Map<String, String> map;
    public E288_Unique_Abbreviation(String[] dictionary) {
        map = new HashMap<>();
        for (String s : dictionary) {
            String pattern = getAbbr(s);
            if (!map.containsKey(pattern))
                map.put(pattern, s);
            else if (!s.equals(map.get(pattern)))
                map.put(pattern, "");
        }
    }

    public boolean isUnique(String word) {
        String pattern = getAbbr(word);
        if (!map.containsKey(pattern) || word.equals(map.get(pattern))) return true;
        else return false;
    }

    private String getAbbr(String s) {
        int l = s.length();
        if (l < 3)  return s;
        else        return String.valueOf(s.charAt(0)) + (l - 2) + String.valueOf(s.charAt(l - 1));
    }
}
