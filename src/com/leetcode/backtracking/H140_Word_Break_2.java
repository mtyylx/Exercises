package com.leetcode.backtracking;

import java.util.*;

/**
 * Created by LYuan on 2016/11/17.
 * Given a string s and a dictionary of words dict,
 * add spaces in s to construct a sentence where each word is a valid dictionary word.
 * Return all such possible sentences.
 *
 * For example, given s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"].
 * A solution is ["cats and dog", "cat sand dog"].
 *
 * Function Signature:
 * public List<String> wordBreak(String s, Set<String> dict) {...}
 */
public class H140_Word_Break_2 {
    public static void main(String[] args) {
        Set<String> dict = new HashSet<>(Arrays.asList("a", "aa", "aaa", "aaaa"));
        List<String> result = wordBreak2("aaaaaa", dict);
    }

    /** 回溯法，穷举所有的划分可能，每次分区都作为之后分区的基础。 */
    // s可以出现重复的词典单词，只要走到死路上（无论怎么分段也匹配），就应该换条路。
    // 窗口从第一个字符开始扩张，直到找到一个匹配点，从该点后再开一个窗口继续扩张。每个窗口都要扫描至结尾才能停止。
    static List<String> wordBreak(String s, Set<String> dict) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0) return result;
        wordBreak_Recursive(s, dict, result, 0, "");
        return result;
    }

    // 注意HashSet的contains比较用的是对应类型的equals方法。如果这个类型已经重写了equals方法使其有比较具体值的能力，那么即使是引用类型照样可以用HashSet。
    static void wordBreak_Recursive(String s, Set<String> dict, List<String> result, int idx, String path) {
        if (idx == s.length()) {                                // 递归终止条件：字符串扫描结束。
            result.add(path.substring(0, path.length() - 1));   // 去掉最后一个空格，存入结果中。
            return;
        }
        for (int i = idx; i < s.length(); i++) {
            String section = s.substring(idx, i + 1);
            if (dict.contains(section))                 // 只有在当前分区有解时才继续递归剩下的部分。
                wordBreak_Recursive(s, dict, result, i + 1, path + section + " ");
        }
    }

    /** DP解法，Memoization，避免重复计算。 */
    // 针对重复字符很多的情况，回溯法的重复计算率很高
    // aaaaaa, [a, aa, aaa, aaaa]
    // a 只有一种拆法
    private static Map<String, List<String>> dp = new HashMap<>();
    static List<String> wordBreak2(String s, Set<String> dict) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0) return result;
        if (dp.containsKey(s)) return dp.get(s);
        if (dict.contains(s)) result.add(s);

        for (int i = 1; i < s.length(); i++) {
            String rest = s.substring(i);
            if (!dict.contains(rest)) continue;
            List<String> cur_result = wordBreak2(s.substring(0, i), dict);
            for (String entry : cur_result) result.add(entry + " " + rest);
        }
        dp.put(s, result);
        return result;
    }
}
