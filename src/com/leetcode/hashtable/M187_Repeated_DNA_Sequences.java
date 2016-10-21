package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by LYuan on 2016/10/21.
 * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG".
 * When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
 * Write a function to find all the 10-letter-long sequences (substrings) that occur <more than once> in a DNA molecule.
 *
 * For example,
 * Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
 * Return:
 * ["AAAAACCCCC", "CCCCCAAAAA"].
 *
 * Function Signature:
 * public List<String> getSequence(String s) {...}
 */
public class M187_Repeated_DNA_Sequences {
    public static void main(String[] args) {
        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        List<String> result = getSequence(s);
    }

    /** 哈希表解法1，双HashSet */
    // 这道题的独特之处是要求寻找固定长度且出现次数多于一次的独特字符串。
    // 而HashSet和HashMap只支持出现次数大于0次的独特字符串。
    // 灵活使用ArrayList和HashSet的构造器来进行类型互转。
    // "new ArrayList(set)"可以将HashSet转换为ArrayList。
    static List<String> getSequence(String s) {
        Set<String> result = new HashSet<>();
        Set<String> set = new HashSet<>();
        for (int i = 0; i + 9 < s.length(); i++) {
            String current = s.substring(i, i + 10);    // 注意substring的stop是exclusive的。
            if (set.contains(current) && !result.contains(current)) result.add(current);
            else set.add(current);
        }
        return new ArrayList<>(result);
    }

    /** 哈希表解法2，HashMap记录出现次数 */
    static List<String> getSequence2(String s) {
        List<String> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i + 9 < s.length(); i++) {
            String current = s.substring(i, i + 10);
            if (map.containsKey(current)) map.put(current, map.get(current) + 1);
            else map.put(current, 1);
        }
        for (String key : map.keySet())
            if (map.get(key) > 1) result.add(key);
        return result;
    }
}
