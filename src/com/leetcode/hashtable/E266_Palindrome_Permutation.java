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
 *
 * <Tags>
 * - HashSet: 统计字符分布。
 * - Coin-Flipping：硬币式增删法。有就删掉，没有就添上，最后剩下的一定是落单的。
 * - Value-As-Index：模拟哈希表。
 *
 */
public class E266_Palindrome_Permutation {
    public static void main(String[] args) {
        System.out.println(isPalindromePermutation("aaaabbbccc"));
        System.out.println(isPalindromePermutation2("aaaabbbccc"));
    }

    /** Palindrome就是镜像对称的字符串，其特征是： */
    // 奇数长度的字符串：只能有1个字符出现奇数次，其他必须全部偶数次
    // 偶数长度的字符串：所有字符都必须出现偶数次（等效于出现0次奇数次字符）

    /** 解法2：Value-as-Index 模拟哈希表。Time - o(n). Space - o(1). */
    // 由于字符集有限，因此可以使用字符对应的ASCII数值作为索引值，统计每个字符出现的次数，
    // 第二次扫描统计出现奇数次的字符个数。为了确保是Palindrome，奇数次字符只有有0个或1个。
    // Value-As-Index是HashTable的一个可靠的替代品，同时也很高效。
    static boolean isPalindromePermutation2(String a) {
        int[] map = new int[256];
        for (char x : a.toCharArray()) map[x]++;    // 第一次扫描，确定分布规律
        int odd = 0;
        for (int x : map)
            if (x % 2 != 0) odd++;                  // 第二次扫描，确定奇数个数
        return odd <= 1;
    }

    /** 解法1：HashSet + 硬币式增删法。Time - o(n), Space - o(1) */
    // 为了省去第二次扫描，这里可以使用硬币式增删法。即如果有，就删掉（抵消），如果没有，就添上（落单），最后只剩下落单的所有字符。
    // 结合Palindrome的构造要求，只需要HashSet最后只有一个元素，或者一个元素都没有即可。即：size < 2.
    static boolean isPalindromePermutation(String a) {
        if (a == null || a.length() == 0) return true;
        Set<Character> set = new HashSet<>();
        for (char c : a.toCharArray()) {
            if (set.contains(c)) set.remove(c);
            else set.add(c);
        }
        return set.size() < 2;
    }
}
