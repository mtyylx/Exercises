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
        for (int i = 1; i < 100; i++)
            System.out.println(minCoinComb2(new int[] {1, 2, 5}, i));
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

    /** DP解法，递归形式，TopDown (target to 0, 但通过备忘重用，将时间复杂度从o(n!)降低至o(n) */
    // 如果下面的代码中忘写了if (dp[target] != 0) return dp[target]这句话，那么这个解法的复杂度立刻就变成了o(n!)
    // 这会导致即使coin = {1, 2, 5}, target = 100，计算起来都是天文数字的时间。原因就在于复用！！！
    // 不重用的话，那么从100分解至1完整算一遍dp[]，接着从99分解至1，仍然需要再算一遍...
    // 由于状态转移过程不仅仅取决于前一个状态，而是根据coin的不同取决于前几个状态，因此如果用递归的方式，依然需要额外数组来备忘。
    static int minCoinComb2(int[] coins, int target) {
        int[] dp = new int[target + 1];
        return minCoinComb_Recursive(coins, target, dp);
    }

    static int minCoinComb_Recursive(int[] coins, int target, int[] dp) {
        if (target < 0) return -1;                  // 递归终止条件
        if (target == 0) return 0;                  // 递归终止条件
        if (dp[target] != 0) return dp[target];     /** 动态规划的核心：只要计算过，就重用。而不是再计算一次。比一般的递归多了一个终止条件。*/
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int ans = minCoinComb_Recursive(coins, target - coins[i], dp);
            if (ans >= 0) min = Math.min(min, 1 + ans);
        }
        dp[target] = (min == Integer.MAX_VALUE) ? -1 : min;
        return dp[target];
    }
}
