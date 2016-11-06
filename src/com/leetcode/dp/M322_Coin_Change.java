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

    /** DP解法，相当经典的动归题。Memoization, Iterative, Bottom-Up (From 0 to target)
     *  Time - o(target * coin.length), Space - o(target) */
    // 如果要解决target金额的分解，那么我们首先从0做起。稳扎稳打。
    // 对于从0开始的每个金额，我们都用coins中的所有面值尝试分解。
    // 如果分解后差值小于0，说明不可以用这个面值分解，跳过，
    // 如果dp[分解后差值]小于0，说明之前计算这个值时就根本没找到任意面值的解，因此也要跳过。
    // 除上面两种情况外，只需要取之前解加1的最小值即可。
    static int minCoinComb(int[] coins, int target) {
        if (coins == null || coins.length == 0) return -1;  // 各种边界情况考虑
        if (target < 0) return -1;
        if (target == 0) return 0;

        int dp[] = new int[target + 1];                     // Memo
        for (int i = 1; i < dp.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                int left = i - coins[j];
                if (left >= 0 && dp[left] >= 0)
                    min = Math.min(min, dp[left] + 1);
            }
            dp[i] = (min == Integer.MAX_VALUE) ? -1 : min;  // 出了内循环需要检查是否无解，无解直接设为-1。
        }
        return dp[target];
    }

}
