package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael on 2016/10/20.
 * Given a pattern and a string str, find if str follows the same pattern.
 * Here "follow" means a full match, such that there is a Bijection (一一映射) between a letter in pattern and a non-empty word in str.
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
 *
 * <一一映射系列问题>
 * E205 Isomorphic Strings: 给定两个字符串，判断两个字符串的字符与字符之间是否满足<一一映射>。
 * E290 Word Pattern      : 给定一个字符串，一个单词串，判断字符与单词之间是否满足<一一映射>。
 *
 * <Tags>
 * - HashMap: containsKey禁止一对多映射，containsValue禁止多对一映射。
 * - str.split(REGEX)
 *
 */
public class E290_Word_Pattern {
    public static void main(String[] args) {
        System.out.println(matchPattern("abba", "hey you you hey"));
        System.out.println(matchPattern2("abba", "hey hey hey hey"));
    }

    /** 解法2：HashMap + HashSet去重。 */
    // HashMap本身可以避免一对多映射，因为同样的Key只能映射一个值，
    // 但是HashMap是允许多对一映射存在的，即不同的Key对应的Value是可以一样的。
    // 这就需要最后再检查一下，是否HashMap的所有Value值都是独一无二的，只有都是独一无二的才能返回true。
    // 为了实现这个，必须把所有的Values导入到HashSet中，如果去重后体积未变则说明OK，否则就说明出现了多对一映射。
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

    /** 解法1：HashMap的<containsKey>和<containsValue>联合使用确保一一映射。 */
    // 与E205思路一致，需要确保pattern的每一个字符都与target的每一个单词是一一映射关系。
    // containsKey用于判断KVP映射中的Key是否已经存在，e.g. b → dog, b → cat wrong.
    // containsValue用于判断KVP映射中的Value是否已经存在，e.g. b → dog, a → dog wrong.
    static boolean matchPattern2(String pattern, String target) {
        if (target == null || pattern == null) return false;
        String[] words = target.split(" ");
        if (pattern.length() != words.length) return false;
        Map<Character, String> map = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char p = pattern.charAt(i);
            if (!map.containsKey(p)) {                          // 如果映射Key尚未存在
                if (map.containsValue(words[i])) return false;  // 先检查映射Value是否存在，如果已经被其他映射所用，不允许
                map.put(p, words[i]);                           // 确定是一一映射后添加该映射
            }
            else if (!map.get(p).equals(words[i])) return false;// 如果映射Key已经存在，但映射Value却并不匹配，不允许
        }
        return true;
    }

}
