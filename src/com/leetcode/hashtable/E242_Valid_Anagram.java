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
 * */
public class E242_Valid_Anagram {
    public static void main(String[] args) {
        System.out.println(isValidAnagram3("anagram", "gramana"));
    }

    // 先排序后比对解法，o(nlogn)
    static boolean isValidAnagram3(String a, String b) {
        if (a.length() != b.length()) return false;
        char[] aa = a.toCharArray();
        char[] bb = b.toCharArray();
        Arrays.sort(aa);
        Arrays.sort(bb);
        for (int i = 0; i < aa.length; i++)
            if (aa[i] != bb[i]) return false;
        return true;
    }

    // Value-as-Index解法，o(n)。可以视为是HashMap的简单版，无需HashMap数据结构
    // Anagram指的就是字符串的同分异构体，也算是String Permutation
    // 同时扫描a和b，a字符串只管加，b字符串只管减，最后如果全0就说明对。
    // 这种方法的另一个变体是利用题目给出的特殊限制，即字符只在小写的a-z之间，所以mask的数组长度可以减小到26
    // 通过求差值来记录，即map[a.charAt(i) - 'a']++.
    static boolean isValidAnagram2(String a, String b) {
        if (a.length() != b.length()) return false;
        int[] map = new int[256];
        for (int i = 0; i < a.length(); i++) {
            map[a.charAt(i)]++;
            map[b.charAt(i)]--;
        }
        for (int x : map)
            if (x != 0) return false;
        return true;
    }

    // XOR解法，不可行，因为没有对a和b的实际内容进行检查
    // 因此在遇到"aa"和"bb"的情况时，会判断错误。
    static boolean isValidAnagram(String a, String b) {
        char x = 0;
        for (char i : a.toCharArray()) x ^= i;
        for (char i : b.toCharArray()) x ^= i;
        return x == 0;
    }
}
