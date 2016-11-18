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
        Set<String> dict = new HashSet<>(Arrays.asList("a", "b", "ab"));
        List<String> result = wordBreak2("abab", dict);
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


    /** DP, Top-Down不断分解字符串，Memoization，工作原理看懂了，但是还是搞不懂怎么写出来的。*/
    // 针对重复字符很多的情况，回溯法的重复计算率很高。很可能重复遇到的pattern之前已经得到了对应的局部result了，但是每次都要重复计算。
    // s = abab, dict = {a, b, ab}
    //     a [bab]
    //     ab [ab] -> matched
    //                s = ab  result = [ab] ------------------------
    //                    a [b] -> matched                         |
    //                             s = a  result = [a]             |
    //                             "a" -> [a]                      |
    //                    "ab" -> [ab, a b]                        |
    //     "abab" -> [ab ab, a b ab]                               |
    //     aba [b] -> matched                                      |
    //                s = aba                                      |
    //                    a [ba]                                   |
    //                    ab [a] -> matched                        |
    //                              s = ab -> [ab, a b] 避免重复计算<-
    //                    "aba" -> [ab a, a b a]
    //     "abab" -> [ab a b, a b a b]
    // "abab" -> [ab ab, a b ab, ab a b, a b a b]
    private static Map<String, List<String>> dp = new HashMap<>();
    static List<String> wordBreak3(String s, Set<String> dict) {
        List<String> result = new ArrayList<>();            // 每个递归方法中都有一个自己的result，这个result是局部的result，返回给上层接在一起用。
        if (s == null || s.length() == 0) return result;
        if (dp.containsKey(s)) return dp.get(s);            // 只要发现当前要分解的字符串已经被解过，就直接返回对应的结果，而不再继续。
        if (dict.contains(s)) result.add(s);
        // 如果分解后的s长度已经等于1，就直接记录进dp并且返回result。
        for (int i = 1; i < s.length(); i++) {
            String rest = s.substring(i);       // 表示从i开始一直到字符串结尾的部分。
            if (dict.contains(rest)) {
                List<String> partial = wordBreak3(s.substring(0, i), dict);
                for (String entry : partial)
                    result.add(entry + " " + rest);
            }
        }
        dp.put(s, result);  // 每次递归结束把当前字符串对应的结果存入哈希表，以备不时之需。
        return result;
    }


}
