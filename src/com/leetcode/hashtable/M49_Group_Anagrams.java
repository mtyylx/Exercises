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
 *
 * <Tags>
 * - Sort String: 对字符串的字符进行排序，可以用于获得字符串的特征
 * - HashMap: Key → Pattern, Value → BucketID。哈希表可用作桶归类的辅助。
 *
 */
public class M49_Group_Anagrams {
    public static void main(String[] args) {
        String[] a = {"abc", "cba", "fuck", "bca", "fcuk"};
        List<List<String>> result = groupAnagram(a);
        List<List<String>> result2 = groupAnagram2(a);
    }

    /** 最佳解法：Sort + HashMap。Time - o(n*logs), Space - o(pattern) */
    // 其中HashMap用归一化的pattern作为Key，该pattern对应的子ArrayList索引作为Value
    // Anagram字符串的共同特征就是它们使用的字符顺序不同但内容完全一致，因此如果字符串属于同一个Anagram，对它们每个进行排序（按字典顺序）后一定相同
    // result是桶的集合，不同类的Anagram会被存入result下不同的桶中，HashMap用于记录这些桶的编号及其Anagram模式
    // 为了简化代码，我们可以发现，在扫描每个元素的时候，不管它属于哪个pattern，都一定会把它加入这个pattern所属桶中，
    static List<List<String>> groupAnagram(String[] s) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (String word : s) {
            char[] temp = word.toCharArray();           // 排序每个字符串以得到pattern：eat，eta，ate -> aet
            Arrays.sort(temp);
            String pattern = new String(temp);          // 小心不要用误用arr.toString()方法，因为字符数组不能用toString转化为字符串
            if (!map.containsKey(pattern)) {            // 只有发现了新pattern才需要创建新桶，并同时在map创建新键值对，存储新桶的编号
                result.add(new ArrayList<>());
                map.put(pattern, result.size() - 1);
            }
            result.get(map.get(pattern)).add(word);     // 简化代码，每个字符串都需要添加到result中，无需分类
        }
        return result;
    }


    /** 较早的解法，用Value-As-Index的方式提取每个字符串的模式。速度较慢。 */
    static List<List<String>> groupAnagram2(String[] a) {
        Map<String, Integer> map = new HashMap<>();
        List<List<String>> result = new ArrayList<>();
        for (String s : a) {
            String pattern = extractPattern(s);
            if (!map.containsKey(pattern)) {
                result.add(new ArrayList<>());
                map.put(pattern, result.size() - 1);
            }
            result.get(map.get(pattern)).add(s);
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
