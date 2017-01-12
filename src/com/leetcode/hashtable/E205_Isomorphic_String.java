package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Michael on 2016/8/29.
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 * All occurrences of a character must be replaced with another character while preserving the order of characters.
 * No two characters may map to the same character but a character may map to itself.
 *
 * For example,
 * Given "egg", "add", return true.
 * Given "foo", "bar", return false.
 * Given "paper", "title", return true.
 *
 * Note:
 * You may assume both s and t have the same length.
 *
 * Function Signature:
 * public boolean isIsomorphic(String a, String b) {...};
 *
 * <Tags>
 * - HashMap: Key-Value-Pair, 排除一对多映射（HashMap自动完成）和多对一映射（需用containsValue手动判断）
 * - Value-As-Index: 字符数组ASCII值作为索引访问，可以等效于HashMap。
 *
 */
public class E205_Isomorphic_String {
    public static void main(String[] args) {
        System.out.println(isSimilar("ab", "bb"));
        System.out.println(isSimilar2("ab", "bb"));
        System.out.println(isSimilar3("ab", "bb"));
        System.out.println(isSimilar4("ab", "bb"));
    }

    /** 解法2：字符数组 Value-As-Index 模拟HashTable功能。Time - o(n) */
    // 使用字符的ASCII值作为索引存储映射关系。
    // 需要注意的是Key和Value必须存成两个数组，而不能在一个数组中。
    // 否则会错误判断a = "ac" b = "ba". a作为val是头一回使用，但是作为key却不是，如果只用一个数组就会认为重复。
    static boolean isSimilar3(String a, String b) {
        char[] keys = new char[128];
        char[] vals = new char[128];
        for (int i = 0; i < a.length(); i++) {
            char key = a.charAt(i);
            char val = b.charAt(i);
            if (keys[key] == 0) {
                if (vals[val] != 0) return false;       // Rule #1: 多对一
                keys[key] = val;
                vals[val] = key;
            }
            else if (keys[key] != val) return false;    // Rule #2: 一对多
        }
        return true;
    }
    // 简化写法：之所以val是i + 1, 是为了避免在第0个元素出现的时候记录与初始化的全0没法区分。
    static boolean isSimilar4(String a, String b) {
        int[] key = new int[128];
        int[] val = new int[128];
        for (int i = 0; i < a.length(); i++) {
            if (key[a.charAt(i)] != val[b.charAt(i)]) return false;
            key[a.charAt(i)] = i + 1;
            val[b.charAt(i)] = i + 1;
        }
        return true;
    }

    /** 解法1：HashMap记录键值对映射关系，检查是否一一映射。Time - o(n^2) */
    // 以字符串a中的每个字符为key，字符串b中的每个字符为val，有如下两条准则：
    // Rule #1: 如果HashMap中尚未记录这个key，但val却已经记录在HashMap的val池中了，返回false。例如: a = "ab", b = "cc" （多对一映射）
    // Rule #2: 如果HashMap中已经有了这个key，但记录的val却与现在的val不同，返回false。例如：a = "aa", b = "bc" （一对多映射）
    // 其他情况一律返回true。例如：a = "ac", b = "ba" （虽然a已经映射到b，但是这并不妨碍c映射到a）
    //
    // 复杂度分析：
    // put - o(1)
    // get - o(1)
    // containsKey - o(1)
    // containsValue - o(n)
    static boolean isSimilar(String a, String b) {
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            if (!map.containsKey(a.charAt(i))) {
                if (map.containsValue(b.charAt(i))) return false;           // Rule #1: 用containsValue判断重复 - o(n)
                else map.put(a.charAt(i), b.charAt(i));
            }
            else if (map.get(a.charAt(i)) != b.charAt(i)) return false;     // Rule #2
        }
        return true;
    }
    // 使用额外的HashSet降低复杂度至o(n)
    // HashSet: contains - o(1)
    static boolean isSimilar2(String a, String b) {
        Map<Character, Character> map = new HashMap<>();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            if (!map.containsKey(a.charAt(i))) {
                if (!set.add(b.charAt(i))) return false;                    // Rule #1: 用contains判断重复 - o(1)
                else map.put(a.charAt(i), b.charAt(i));
            }
            else if (map.get(a.charAt(i)) != b.charAt(i)) return false;     // Rule #2
        }
        return true;
    }
}
