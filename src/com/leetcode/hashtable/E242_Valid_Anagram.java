package com.leetcode.hashtable;

import java.util.*;

/**
 * Created by Michael on 2016/8/31.
 * Given two strings s and t, write a function to determine if t is an anagram of s.
 *
 * For example,
 * s = "anagram", t = "nagaram", return true.
 * s = "rat", t = "car", return false.
 *
 * Note:
 * You may assume the string contains only lowercase alphabets.
 *
 * Follow up:
 * What if the inputs contain unicode characters? How would you adapt your solution to such case?
 *
 * Function Signature:
 * public boolean isValidAnagram(String a, String b) {...}
 *
 * <Anagram系列问题>
 * E242 Valid Anagram: 给定字符串a和字符串b，判断a和b是否是Anagram。
 * M49  Group Anagram: 给定一个字符串数组，按Anagram分组返回。
 * E438 Find Anagram : 给定字符串a和字符串b，返回a中所有出现b的Anagram的起始位置。
 *
 * <Tags>
 * - HashMap: 字符做键，出现次数做值。
 * - Value-As-Index：字符数组ASCII值作为索引访问，等效于HashMap。
 * - Sort + Scan: 利用已排序数组内容的可预测性。
 *
 */
public class E242_Valid_Anagram {
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "gramana"));
        System.out.println(isAnagram2("anagram", "gramana"));
        System.out.println(isAnagram3("anagram", "gramana"));
        System.out.println(isAnagram4("anagram", "gramana"));
    }

    /** 解法3：Sort + Scan. 利用排序带来的性质同步扫描两个字符串。Time - o(nlogn). Space - o(n). */
    // 先排序后比对解法，o(nlogn)
    static boolean isAnagram4(String a, String b) {
        if (a.length() != b.length()) return false;
        char[] A = a.toCharArray();
        char[] B = b.toCharArray();
        Arrays.sort(A);
        Arrays.sort(B);
        for (int i = 0; i < A.length; i++)
            if (A[i] != B[i]) return false;
        return true;
    }

    /** 解法2：数组 Value-As-Index解法，模拟HashMap的功能，在字符集较小时速度快于HashMap。Time - o(n), Space - o(1). */
    // 可以视为是HashMap的简单版，无需HashMap数据结构，但是在字符集较大的情况下（例如Unicode会有100,000,000个字符），效率不如HashMap。
    // Anagram指的就是字符串的同分异构体，算是String Permutation
    // 同时扫描a和b，a字符串只管加，b字符串只管减，最后如果全0就说明对。
    // 如果想进一步节省空间，利用题目给出的字符只在小写的a-z之间的限制，可以通过求差值来得到索引，
    // 即map[a.charAt(i) - 'a']++，map的数组长度可以减小到26
    static boolean isAnagram3(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] map = new int[128];
        for (int i = 0; i < a.length(); i++) {
            map[a.charAt(i)]++;
            map[b.charAt(i)]--;
        }
        for (int x : map)
            if (x != 0) return false;
        return true;
    }

    /** 解法1：HashMap记录字符及其出现次数。Time - o(n), Space - o(1) since char set is fixed. */
    // 字符本身作为key，出现次数作为value，用正负区别来自两个字符串的字符
    // 来自字符串a的出现次数+1，来自字符串b的出现次数-1，最后判断是否全都为0.
    // 如果字符集很大（0-65535），使用HashMap会比Value-As-Index的数组效率更高
    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            if (map.containsKey(a.charAt(i))) map.put(a.charAt(i), map.get(a.charAt(i)) + 1);
            else map.put(a.charAt(i), 1);
            if (map.containsKey(b.charAt(i))) map.put(b.charAt(i), map.get(b.charAt(i)) - 1);
            else map.put(b.charAt(i), -1);
        }
        for (Integer x : map.values())
            if (x != 0) return false;
        return true;
    }
    // 也可以用两个HashMap分别记录，最后用equals()方法比较。
    static boolean isAnagram2(String a, String b) {
        if (a.length() != b.length()) return false;
        HashMap<Character, Integer> mapA = new HashMap<>();
        HashMap<Character, Integer> mapB = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            if (mapA.containsKey(a.charAt(i))) mapA.put(a.charAt(i), mapA.get(a.charAt(i)) + 1);
            else mapA.put(a.charAt(i), 1);
            if (mapB.containsKey(b.charAt(i))) mapB.put(b.charAt(i), mapB.get(b.charAt(i)) + 1);
            else mapB.put(b.charAt(i), 1);
        }
        return mapA.equals(mapB);   // 比较两个HashMap的内容是否完全相同。
    }


    // 试图用XOR解法，但发现不可行，因为XOR只关注结果，不能关注过程，无法对a和b的每个字符进行检查
    // 因此在遇到"aa"和"bb"的情况时，会误判
    static boolean isValidAnagram(String a, String b) {
        char x = 0;
        for (char i : a.toCharArray()) x ^= i;
        for (char i : b.toCharArray()) x ^= i;
        return x == 0;
    }
}
