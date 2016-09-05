package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by Michael on 2016/9/5.
 *
 * Given an array of strings, group anagrams together.
 *
 * For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Return:
 * [
 *  ["ate", "eat","tea"],
 *  ["nat","tan"],
 *  ["bat"]
 * ]
 *
 * Note: All inputs will be in lower-case.
 *
 * Function Signature:
 * public List<List<String>> groupAnagram(String[] a) {...}
 * */
public class M49_Group_Anagrams {
    public static void main(String[] args) {
        String[] a = {"abc", "cba", "fuck", "bca", "fcuk"};
        List<List<String>> r = groupAnagram2(a);
    }

    static List<List<String>> groupAnagram2(String[] a) {
        Map<String, Integer> map = new HashMap<>();
        List<List<String>> result = new ArrayList<>();
        for (String s : a) {
            // Sort the string to get its pattern.
            char[] c = s.toCharArray();
            Arrays.sort(c);
            String pattern = new String(c);

            if (map.containsKey(pattern))
                result.get(map.get(pattern)).add(s);
            else {
                map.put(pattern, map.size());   // 巧妙使用map的大小作为idx存入，省去了定义单独counter
                List<String> anotherrow = new ArrayList<>();
                anotherrow.add(s);
                result.add(anotherrow);
            }
        }
        return result;
    }


    // 哈希表 + 特征提取比对，
    // 如果当前字符串的模式遇到过，就找到哈希表中的相应模式的索引，把当前字符串加进去
    // 如果当前字符串头一次遇到，就把当前字符串存入结果列表的新生成分支中，并同时把该模式与当前分支的索引存入哈希表
    static List<List<String>> groupAnagram(String[] a) {
        Map<String, Integer> map = new HashMap<>();
        List<List<String>> result = new ArrayList<>();
        int count = 0;
        for (String s : a) {
            String pattern = extractPattern(s);
            if (map.containsKey(pattern)) result.get(map.get(pattern)).add(s);
            else {
                List<String> newPattern = new ArrayList<>();
                newPattern.add(s);
                result.add(newPattern);
                map.put(pattern, count++);
            }
        }
        return result;
    }

    private static String extractPattern(String a) {
        int[] map = new int[26];
        for (int i = 0; i < a.length(); i++) {
            map[a.charAt(i) - 'a']++;
        }
        return Arrays.toString(map);
    }
}
