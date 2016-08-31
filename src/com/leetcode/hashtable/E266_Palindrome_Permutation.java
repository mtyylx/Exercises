package com.leetcode.hashtable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/8/31.
 * Given a string, determine if a permutation of the string could form a palindrome.
 *
 * For example,
 * "code" -> False,
 * "aab" -> True,
 * "carerac" -> True.
 *
 * Hint:
 * Consider the palindromes of odd vs even length.
 * What difference do you notice? Count the frequency of each character.
 * If each character occurs even number of times, then it must be a palindrome.
 * How about character which occurs odd number of times?
 *
 * Function Signature:
 * public boolean isPalindromePermutation(String a) {...}
 * */
public class E266_Palindrome_Permutation {
    public static void main(String[] args) {
        System.out.println(isPalindromePermutation2("aaaabbbccc"));
    }

    // Value-as-Index解法
    // 统计每个字符出现的次数，再统计出现次数为奇数的次数
    // 可以不使用Hashtable数据结构
    static boolean isPalindromePermutation2(String a) {
        int[] map = new int[256];
        char[] str = a.toCharArray();
        for (char x : str)
            map[x]++;
        int odd_count = 0;
        for (int x : map)
            if (x % 2 != 0) odd_count++;
        return odd_count <= 1;
    }

    // 哈希表解法，o(n)
    // 所谓的Palindrome，就是指字符串是镜像对称的
    // 奇数长度的字符串：只能有一个字符出现奇数次，其他必须全部偶数次（当然也不可能出现全部字符都出现偶数次）
    // 偶数长度的字符串：所有字符都必须出现偶数次，（当然也不可能出现只有一个字符出现奇数次）
    // 所以如果使用添加/删除方式，
    // 奇数长度字符串的HashSet长度必须为1，且不可能为0
    // 偶数长度字符串的HashSet长度必须为0，且不可能为1
    // 所以综上所述，只要size小于等于1即可。
    static boolean isPalindromePermutation(String a) {
        if (a == null || a.length() == 0) return true;
        Set<Character> set = new HashSet<>();
        for (char i : a.toCharArray())
            if (!set.add(i)) set.remove(i);
        return set.size() <= 1;
        // 可以简化的部分：不需要计算奇数偶数长度
//        if (a.length() % 2 == 0 && set.size() == 0) return true;
//        if (a.length() % 2 != 0 && set.size() == 1) return true;
//        return false;
    }
}
