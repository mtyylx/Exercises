package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael on 2016/10/20.
 * Given a pattern and a string str, find if str follows the same pattern.
 * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.
 *
 * Examples:
 * pattern = "abba", str = "dog cat cat dog" should return true.
 * pattern = "abba", str = "dog cat cat fish" should return false.
 * pattern = "aaaa", str = "dog cat cat dog" should return false.
 * pattern = "abba", str = "dog dog dog dog" should return false.
 *
 * Notes: You may assume pattern contains only lowercase letters,
 * and str contains lowercase letters separated by a single space.
 *
 * Function Signature:
 * public static boolean matchPattern(String pattern, String str) {...}
 */
public class E290_Word_Pattern {
    public static void main(String[] args) {
        System.out.println(matchPattern2("abba", "hey you you hey"));
        System.out.println(matchPattern2("abba", "hey hey hey hey"));
    }

    /** HashMap + HashSet 解法 */
    // 注意，这里要求的是一一对应，不能有多对一映射，也不能有一对多映射。
    // HashMap本身可以避免一对多映射，因为同样的Key只能映射一个值，
    // 但是HashMap是允许多对一映射存在的，即不同的Key对应的Value是可以一样的。
    // 这就需要最后再检查一下，是否HashMap的所有Value值都是独一无二的，只有都是独一无二的才能返回true。
    // 为了实现这个，必须把所有的Values导入到HashSet中，看看经过去重后，Set的体积是否小于Map。
    static boolean matchPattern(String pattern, String str) {
        String[] target = str.split(" ");
        if (pattern.length() != target.length) return false;
        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char p = pattern.charAt(i);
            if (map.containsKey(p) && !map.get(p).equals(target[i])) return false;
            else map.put(p, target[i]);
        }
        Set<String> set = new HashSet<>(map.values());
        return set.size() == map.size();
    }

    /** 仅用HashMap，无需HashSet的简化解法 */
    // 确定Key不存在的前提下，如果Value已经存在了，就说明存在了多对一的映射，因此可以直接返回false.
    // 只有在Key独特，且Value也独特的情况下，才能确保哈希表中的每一个键值对都是一一映射且没有多对一/一对多存在。
    static boolean matchPattern2(String pattern, String str) {
        String[] target = str.split(" ");
        if (pattern.length() != target.length) return false;
        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char p = pattern.charAt(i);
            if (map.containsKey(p) && !map.get(p).equals(target[i])) return false;
            else if (!map.containsKey(p) && map.containsValue(target[i])) return false;
            else map.put(p, target[i]);
        }
        return true;
    }

}
