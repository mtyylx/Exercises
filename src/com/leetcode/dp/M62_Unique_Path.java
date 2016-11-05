package com.leetcode.dp;

/**
 * Created by Michael on 2016/11/5.
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * How many possible unique paths are there?
 * Note: m and n will be at most 100.
 *
 * Function Signature:
 * public int uniquePath(int m, int n) {...}
 */
public class M62_Unique_Path {
    public static void main(String[] args) {
        System.out.println(uniquePath(3, 4));
        System.out.println(uniquePath2(3, 4));
    }

    /** DP解法1，Bottom-Up，Memoization，Time - o(n*m), Space - o(n) */
    // 与M64_Min_Path_Sum的区别在于：
    // M64的状态转移方程描述的是：从左上角到当前结点为止的最小路径和。dp[i] = Min{ dp[i], dp[i - 1]} + m[i][j]，求得是最小值
    // M62的状态转移方程则描述：从左上角走到当前结点为止的不重复路径条数。dp[i] = dp[i] + dp[i - 1]，求的就是和。
    // 而且这两道题对于边界情况的处理方式也不同：M64中对于超出边界的分支视为Max，M62对于超出边界的则需要视为0.
    //      0    0    0    0
    //      ↓    ↓    ↓    ↓
    // 0 → 00 - 01 - 02 - 03
    //      |    |    |    |
    // 0 → 10 - 11 - 12 - 13
    //      |    |    |    |
    // 0 → 20 - 21 - 22 - 23
    static int uniquePath(int m, int n) {
        if (m * n == 0) return 0;
        if (n == 1 || m == 1) return 1;

        int[] dp = new int[n + 1];      // 使用一个全0数组，为了确保正确的起始值，需要给dp[1]设为1。
        dp[1] = 1;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                dp[j + 1] = dp[j] + dp[j + 1];
        return dp[n];
    }

    /** DP解法2，区别于上面的解法是这里备忘用的是一整个矩阵，而不是一个数组，因此空间复杂度更高。Time - o(n*m), Space - o(n*m) */
    static int uniquePath2(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) dp[i][0] = 1;       // 边界处理：第0列
        for (int i = 0; i < n; i++) dp[0][i] = 1;       // 边界处理：第0行
        for (int i = 1; i < m; i++)                     // 正常情况，求和即可。
            for (int j = 1; j < n; j++)
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        return dp[m - 1][n - 1];
    }
}
