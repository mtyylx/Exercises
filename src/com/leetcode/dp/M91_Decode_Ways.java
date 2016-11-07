package com.leetcode.dp;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michael on 2016/11/7.
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 * Given an encoded message containing digits, determine the total number of ways to decode it.
 *
 * For example,
 * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 * The number of ways decoding "12" is 2.
 *
 * Function Signature:
 * public int decodeWays(String s) {...}
 */
public class M91_Decode_Ways {
    public static void main(String[] args) {
        for(int i = 1; i < 10000; i++) {
            System.out.println(decodeWays(Integer.toString(i)) == decodeWays2(Integer.toString(i)));
        }
    }

    /** DP解法1，Memoization，Time - o(n), Space - o(n) */
    // 一开始我天真的认为这可以通过递推解决，因为看上去每增加一个数字的状态数只于上一个字符串的状态数有关。
    // 蛋似，实际并不是这样。下面的例子中，计算最后一个元素1的状态数时，正确的答案是5，因为可以从dp[2]获得2两个状态，但是如果仅从dp[3]递推的话，只能得到4.
    // 1 2 2 1
    // dp[0] = 1
    //        { }
    // dp[1] = 1
    //        {1}
    // dp[2] = dp[0]     +    dp[1] = 2
    //         {1/2}           {12}
    // dp[3] = dp[1]     +    dp[2] = 3
    //         {1+22}     {1/2+2，12+2})
    // dp[4] = dp[2]     +    dp[3] = 5
    // {1/2+21, 12+21}   {1/22+1, 1/2/2+1, 12/2+1})
    /** 从上面的推导可以看到，其实每一轮的状态数，都由之前两轮的状态数完全决定（当然也要根据one和two的值决定是否用前两轮的状态数）*/
    // 因此其实可以不用一个dp数组，而是只用一个3个变量就可以实现。
    static int decodeWays(String s) {
        if(s == null || s.length() == 0) return 0;
        int n = s.length();
        int[] dp = new int[n + 1];
        // 种子值，只要第一位不是0就可以传递下去。
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for(int i = 1; i < n; i++) {
            int one = Integer.valueOf(s.substring(i, i + 1));           // current digit
            int two = Integer.valueOf(s.substring(i - 1, i + 1));       // last two digits
            if (one > 0) dp[i + 1] += dp[i];                            // 如果当前字符为0，则不能使用上一个dp的值，因为0没有对应字符。
            if (two >= 10 && two <= 26) dp[i + 1] += dp[i - 1];         // 只要合起来在10到26之间，就可以用上上个dp值，否则对于"909"这种情况不能把09算上。
        }
        return dp[n];
    }

    /** DP解法2，Memoization，Time - o(n), Space - o(1) */
    // 由于每轮的状态数只于前一轮以及前前轮的状态数相关，因此简化使用三个变量缓存。缓存更新方向为：prev2 <- prev <- current
    // prev2表示前前轮状态数
    // prev表示前一轮状态数
    // current初始值为1，为了处理输入只有一位无法进入for循环的情况。
    // current每次都要重置为0，因为不确定当前轮是否有状态。只有满足值的要求才累加前面几轮的状态数。
    static int decodeWays2(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;   // 排除以0开头的情况
        int prev2 = 1;
        int prev = s.charAt(0) == '0' ? 0 : 1;
        int current = 1;
        for (int i = 1; i < s.length(); i++) {
            current = 0;
            int one = Integer.valueOf(s.substring(i, i + 1));           // current digit
            int two = Integer.valueOf(s.substring(i - 1, i + 1));       // last two digits
            if (one > 0) current += prev;
            if (two >= 10 && two <= 26) current += prev2;
            prev2 = prev;       // buffer
            prev = current;     // buffer
        }
        return current;
    }
}
