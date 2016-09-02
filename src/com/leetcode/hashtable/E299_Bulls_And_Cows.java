package com.leetcode.hashtable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LYuan on 2016/9/2.
 * You are playing the following Bulls and Cows game with your friend:
 * You write down a number and ask your friend to guess what the number is.
 * Each time your friend makes a guess, you provide a hint that indicates:
 * 1. how many digits in said guess match your secret number exactly in both digit and position (called "bulls")
 * 2. how many digits match the secret number but locate in the wrong position (called "cows").
 * Your friend will use successive guesses and hints to eventually derive the secret number.
 *
 * For example:
 * Secret number:  "1807"
 * Friend's guess: "7810"
 * Hint: 1 bull and 3 cows. (The bull is 8, the cows are 0, 1 and 7.)
 * Write a function to return a hint according to the secret number and friend's guess,
 * use A to indicate the bulls and B to indicate the cows.
 * In the above example, your function should return "1A3B".
 *
 * Notes:
 * Both secret number and friend's guess may contain duplicate digits, for example:
 * Secret number:  "1123"
 * Friend's guess: "0111"
 * In this case, the 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow, and your function should return "1A1B".
 * You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.
 *
 * Function Signature:
 * public String getHint(String secret, String guess) {..}
 * */
public class E299_Bulls_And_Cows {
    public static void main(String[] args) {
        System.out.println(getHint2("1807", "7810"));
    }

    // 简化解法
    // ASCII Value-As-Index解法：仅使用1个map，将secret中找到的内容++，将guess中找到的内容--，
    // 通过正负号区分来自两个字符串的内容。这也是用一个哈希表做起来比较麻烦的地方，区分到底是来自于guess还是来自于secret不好操作。
    static String getHint2(String secret, String guess) {
        int countA = 0;
        int countB = 0;
        int[] map = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            int a = secret.charAt(i) - '0';
            int b = guess.charAt(i) - '0';
            if (a == b) countA++;
            else {
                if (map[a]++ < 0) countB++;     // 仅在secret对应字符数量小于0（即guess中出现）才算cow，统计完就自增抵消这个cow
                if (map[b]-- > 0) countB++;     // 仅在guess对应字符数量大于0（即secret中出现）才算cow，统计完就减掉抵消这个cow
            }
        }
        return countA + "A" + countB + "B" ;
    }

    // ASCII Value-As-Index解法：使用两个map，最后统计两者的最小值（即重叠元素）
    // 时间复杂度O(n)，空间复杂度O(1)
    // 仅在相同位置的字符不同时才计入map中供最后扫描统计B情况的数量
    static String getHint(String secret, String guess) {
        int countA = 0;
        int countB = 0;
        int[] map1 = new int[10];
        int[] map2 = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            int a = secret.charAt(i) - '0';
            int b = guess.charAt(i) - '0';
            if (a == b) countA++;
            else {
                map1[a]++;
                map2[b]++;
            }
        }
        for (int i = 0; i < 10; i++) {
            countB += Math.min(map1[i], map2[i]);
        }
        return countA + "A" + countB + "B" ;
    }
}
