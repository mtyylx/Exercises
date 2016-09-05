package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/9/5.
 * Given a string, find the length of the longest substring without repeating characters.
 *
 * Examples:
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * Given "bbbbb", the answer is "b", with the length of 1.
 * Given "pwwkew", the answer is "wke", with the length of 3.
 *
 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Function Signature:
 * public int longestSubStringWithoutRepeat(String a) {...}
 * */
public class M3_Longest_SubString_Without_Repeating_Characters {
    public static void main(String[] args) {
        System.out.println(longestSubStringWithoutRepeat2("abbabbabcdadckdiac"));
    }

    // 哈希表+双指针（快慢指针）解法，
    // 基本思路：
    // 维护一个哈希表，从第一个元素开始将元素值作为键，元素索引作为值存入哈希表
    // j为快指针，不断添加或更新现有元素最近一次出现的索引位置
    // i仅在遇到重复元素时，才会更新为重复的元素最近一次出现的位置的下一个元素，作为重新计算长度的起始位置
    // 每当快指针移动一次，都重新计算当前已经达到的最大长度
    static int longestSubStringWithoutRepeat2(String a) {
        if (a == null || a.length() == 0) return 0;
        int result = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < a.length(); j++){
            if (map.containsKey(a.charAt(j))){
                // 取max是为了避免abba这种情况，即b重复完了以后又出现了a重复，如果但这时候不能从a的上次出现的接下来位置，而是应该从i的位置开始重新计算
                i = Math.max(i, map.get(a.charAt(j)) + 1);
            }
            map.put(a.charAt(j), j);
            result = Math.max(result, j - i + 1);
        }
        return result;
    }

    // 这个解法思路其实和上面是相似的，但是区别在于遇到重复元素以后怎么处理。
    // 这里选择把整个哈希表都清空，然后指针位置从上次出现重复元素的位置开始，重新扫描添加，所以会效率很低。
    static int longestSubStringWithoutRepeat(String a) {
        if (a == null || a.length() == 0) return 0;
        int i = 0;
        int j = i + 1;
        int result = 1;
        Map<Character, Integer> map = new HashMap<>();
        map.put(a.charAt(0), 0);
        while (j < a.length()) {
            if (!map.containsKey(a.charAt(j))) {
                map.put(a.charAt(j), j);
                j++;
                result = Math.max(result, j - i);
            }
            else {
                result = Math.max(result, j - i);
                i = map.get(a.charAt(j)) + 1;
                j = i + 1;
                map.clear();
                map.put(a.charAt(i), i);
            }
        }
        return result;
    }
}
