package com.leetcode.dp;

/**
 * Created by Michael on 2016/11/6.
 * You are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 *
 * Example 1:
 * coins = [1, 2, 5], amount = 11
 * return 3 (11 = 5 + 5 + 1)
 *
 * Example 2:
 * coins = [2], amount = 3
 * return -1.
 *
 * Note: You may assume that you have an infinite number of each kind of coin.
 *
 * Function Signature:
 * public int minCoinComb(int[] coins, int target) {...}
 */
public class M322_Coin_Change {
    public static void main(String[] args) {
        System.out.println(minCoinComb(new int[] {1, 2, 5}, 11));
    }

    // 1, 2, 5     11
    static int minCoinComb(int[] coins, int target) {
        if (coins == null || coins.length == 0) return -1;
        if (target <= 0) return -1;
        int dp[] = new int[target + 1];
        for (int i = 1; i < dp.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (target - coins[j] >= 0 && dp[target - coins[j]] >= 0)
                    min = Math.min(min, dp[target - coins[j] + 1]);
            }
            dp[i] = (min == Integer.MAX_VALUE) ? -1 : min;
        }
        return dp[target];
    }
}
