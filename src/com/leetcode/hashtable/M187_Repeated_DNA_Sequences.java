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
 *
 * <Tags>
 * - HashSet: 判重，去重
 * - HashMap：Key → 字符串，Value → 出现次数
 * - Substring：起点包含，终点不包含
 *
 */
public class M187_Repeated_DNA_Sequences {
    public static void main(String[] args) {
        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        System.out.println(getDNASequence(s).toString());
        System.out.println(getDNASequence2(s).toString());
    }

    /** 解法1：双HashSet判重去重。Time - o(n). */
    // 这道题的独特之处是要求寻找固定长度且出现次数多于一次的独特字符串。
    // 而HashSet和HashMap只支持出现次数大于0次的独特字符串。
    // 灵活使用ArrayList和HashSet的构造器来进行类型互转。
    // "new ArrayList(set)"可以将HashSet转换为ArrayList。
    static List<String> getDNASequence(String s) {
        Set<String> set = new HashSet<>();
        Set<String> result = new HashSet<>();       // 避免结果有重复
        for (int i = 0; i + 9 < s.length(); i++) {  // [i, i+9] 区间是10个元素（因为个数等于差值加一：i+9 - i + 1 = 10）
            String sub = s.substring(i, i + 10);    // 注意substring的end位置是exclusive的，因此要额外加一
            if (!set.add(sub)) result.add(sub);     // 如果已经添加过，就把该子字符串加入result
        }
        return new ArrayList<>(result);             // 隐式Handle字符串长度不够10的情况，很优美
    }

    /** 解法2：HashMap记录出现次数，需要扫描两次，没有解法1简洁。 */
    static List<String> getDNASequence2(String s) {
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
