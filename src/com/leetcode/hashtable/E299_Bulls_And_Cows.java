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
 * 不要被唬住，这里需要做的只是按照指定规则提供两个字符串的比对模板，并不需要设计每次猜什么字符才能在最少尝试次数下得到secret。
 *
 * Function Signature:
 * public String getHint(String secret, String guess) {..}
 *
 * <Tags>
 * - Value-As-Index: 统计字符分布，在字符集较小的情况下会比HashMap更快。
 * - 硬币正负翻转法
 *
 */
public class E299_Bulls_And_Cows {
    public static void main(String[] args) {
        System.out.println(getHint2("1807", "7810"));
        System.out.println(getHint("1123", "0111"));
    }

    /** 解法2：Value-As-Index + 硬币正负翻转法，简化为单数组。Time - o(n), Space - o(1). */
    // 如果只想用一个数组，如何区分字符的count是来自secret还是guess呢，
    // 这里secret中出现的字符个数记为正数，guess中出现的字符个数记为负数，
    // 只要数组的元素值大于0，就说明有尚未匹配的secret字符，只要数组的元素小于0，就说明有尚未匹配的guess字符。
    // 如果用secret访问某个字符索引发现次数是小于0的，即map[a] < 0，就说明之前这个字符曾经在guess中出现过，所以算是发现了一个满足countB要求的。
    // 同理，如果用guess访问某个字符索引返现次数大于0，就说明这个字符之前在secret中出现过，也是满足countB的。
    // 上面两种情况出现后，最后都要把该字符的count抵消掉，等效于抵消了一个同时出现在guess和secret中不同位置的pair。
    // secret = 1 1 2 3    [0  1  2  3  4  5  6  7  8  9]
    // guess  = 0 1 1 1    -1  1
    //                     -1  2
    //                     -1  1
    //                     -1  0  1
    //                     -1 -1  1  1
    static String getHint2(String secret, String guess) {
        int countA = 0;
        int countB = 0;
        int[] map = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            int a = secret.charAt(i) - '0';
            int b = guess.charAt(i) - '0';
            if (a == b) countA++;
            else {
                if (map[a]++ < 0) countB++;     // secret字符在guess中出现过（小于0）
                if (map[b]-- > 0) countB++;     // guess字符在secret中出现过（大于0）
            }
        }
        return countA + "A" + countB + "B" ;
    }

    /** 解法1：Value-As-Index双数组，模拟HashMap统计分布。Time - o(n), Space - o(1). */
    // 由于这里的字符集被限定在'0'-'9'，相对很小，Value-As-Index解法在这种字符集很小的情况下会比HashMap更快。
    // 定义两个map，分别统计secret和guess的字符分布情况。最后取每个字符的次数最小值算作重叠元素个数。
    // 对于内容对但是位置不对的字符，可以单独用countB-countA刨掉，也可以用if-else分离
    static String getHint(String secret, String guess) {
        int countA = 0;
        int countB = 0;
        int[] map1 = new int[10];
        int[] map2 = new int[10];
        for (int i = 0; i < secret.length(); i++) {
            int a = secret.charAt(i) - '0';
            int b = guess.charAt(i) - '0';
            if (a == b) countA++;
            else {                                  // Only countB when NOT countA
                map1[a]++;
                map2[b]++;
            }
        }
        for (int i = 0; i < 10; i++)
            countB += Math.min(map1[i], map2[i]);
        return countA + "A" + countB + "B" ;        // int和string相连，自动转型为字符串
    }
}
