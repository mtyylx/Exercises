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
        System.out.println(longestSubStringWithoutRepeat("pwwkew"));
    }

    static int longestSubStringWithoutRepeat2(String a) {
        if (a == null || a.length() == 0) return 0;
        int result = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0, j=0; i<a.length(); ++i){
            if (map.containsKey(a.charAt(i))){
                j = Math.max(j, map.get(a.charAt(i))+1);
            }
            map.put(a.charAt(i), i);
            result = Math.max(result, i - j + 1);
        }
        return result;
    }

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
