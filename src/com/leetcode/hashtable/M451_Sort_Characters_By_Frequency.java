package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 2017/1/18.
 * Given a string, sort it in decreasing order based on the frequency of characters.
 * Example 1:
 * Input: "tree"
 * Output: "eert"
 * Explanation:
 * 'e' appears twice while 'r' and 't' both appear once.
 * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
 *
 * Example 2:
 * Input: "cccaaa"
 * Output: "cccaaa"
 * Explanation:
 * Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
 * Note that "cacaca" is incorrect, as the same characters must be together.
 *
 * Example 3:
 * Input: "Aabb"
 * Output: "bbAa"
 * Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect.
 * Note that 'A' and 'a' are treated as two different characters.
 *
 * Function Signature:
 * public String frequencySort(String a) {...}
 *
 * <出现频率分布统计 系列问题>
 * - M347 Top K Frequent Element      : 给定一个整型数组和一个值K，按照元素出现次数从大到小的顺序返回前K个元素值。
 * - M451 Sort Characters By Frequency: 给定一个字符串，按照字符出现次数从大到小的顺序返回字符串。
 *
 * <Tags>
 * - HashMap: Key - 字符， Value - 字符出现的次数
 * - Value-As-Index: 针对小字符集可以代替HashMap实现映射功能。
 * - Bucket: 桶归类的思想，可用于反向映射哈希表。
 *
 */
public class M451_Sort_Characters_By_Frequency {
    public static void main(String[] args) {
        System.out.println(frequencySort("bcbcbcdad"));
        System.out.println(frequencySort("ebce"));
    }

    /** 解法1；HashMap + 反向映射。Time - o(n), Space - o(n). */
    // 第一步，我们需要用HashMap或Value-As-Index方式获得每个出现字符与其出现次数的映射关系。
    // 第二步，我们需要用数组的索引和元素表达每个出现次数下都各有哪些字符（即反向索引）
    // 第三步，根据获得的反向索引，按照出现频率由高到低的顺序构建新字符串。
    // 本质上第二步所做的，和Bucket Sort是一样的归类方式，即收集出现次数相同的字符，合并在一起（无需关心顺序）
    static String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();          // HashMap：出现的字符做Key，字符出现的次数做Value
        for (char c : s.toCharArray()) {                        // HashMap遍历字符串统计字符频率分布（也可以用Value-As-Index做到）
            if (map.containsKey(c)) map.put(c, map.get(c) + 1);
            else map.put(c, 1);
        }
        String[] bucket = new String[s.length() + 1];           // 反向映射的容器桶，需要字符串长度大一，因为存在出现次数为0的情况。
        for (char c : map.keySet()) {                           // 按Key对HashMap遍历
            int times = map.get(c);                             // 获取出现次数
            StringBuilder comb = new StringBuilder();
            for (int i = 0; i < times; i++) comb.append(c);     // 根据出现次数构造字符串，有几个重复的就加几个。例如 e,4 就变成 "eeee".
            if (bucket[times] == null) bucket[times] = comb.toString();
            else bucket[map.get(c)] += comb.toString();         // 累加或新增
        }
        StringBuilder sb = new StringBuilder();
        for (int i = bucket.length - 1; i > 0; i--) {
            if (bucket[i] != null) sb.append(bucket[i]);        // 逆向扫描bucket，组合成新数组返回。
        }
        return sb.toString();
    }
}
