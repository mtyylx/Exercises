package com.leetcode.dp;

import java.util.Arrays;

/**
 * Created by Michael on 2016/11/5.
 * Given a m x n grid filled with non-negative numbers,
 * find a path from top left to bottom right which minimizes the sum of all numbers along its path.
 * Note: You can only move either down or right at any point in time.
 *
 * Function Signature:
 * public int minPathSum(int[][] grid) {...}
 */
public class M64_Min_Path_Sum {
    public static void main(String[] args) {
        int[][] grid = new int[3][];
        grid[0] = new int[] {1, 2, 3, 4};
        grid[1] = new int[] {2, 3, 4, 5};
        grid[2] = new int[] {3, 4, 5, 6};
        System.out.println(minPathSum3(grid));
    }

    /** DP解法，Bottom-Up，Memoization (更改原有矩阵数值), Time - o(n*m), Space - o(1)
     * 状态转移方程：dp[i][j] = Min{ dp[i - 1][j], dp[i][j - 1] } + a[i][j] */
    //   状态转移图     (i - 1, j)
    //                    ↓
    // (i, j - 1)  →  ( i , j )
    // 有了状态转移方程之后，只需要再针对边界情况（第0行/第0列）进行一一特殊处理即可！是不是非常简单！
    // 其实矩阵的行走问题都是对树进行遍历问题的变体而已，只不过这个树是斜躺着呆着的，而且不是以抵达叶子节点为终止，而是以抵达右下角为终止。
    // 要注意的一点是，虽然这里我们是从左上角向右下角扫描的，但是算法的逻辑却并不是Top-Down而是Bottom-Up的。
    // 问题1：为什么是Bottom-Up？
    // 这需要我们从右下角开始逆推分析，23要获得最小和，取决于22和13各自路径到自己这的最小和，又接着取决于其左和其上元素的最小和。
    // 触到边界的时候，则只考虑其左和其上之中真实存在的元素的最小和，
    // 最后可以发现，一切的一切，都取决与左上角元素本身，这时候，左上角元素本身就是那个树的唯一叶子节点。
    // 所以我们说，这是从树底向树顶的Bottom-Up顺序。
    //        0    1    2    3
    //        ↓    ↓    ↓    ↓
    //        |    |    |    |
    // 0 → - 00 - 01 - 02 - 03  有先后递推顺序，从左至右，再从上到下，边界的上个两个子树只有一个有值，因此直接叠加即可。
    //        |    |    |    |
    // 1 → - 10 - 11 - 12 - 13  对于中间的节点，左和上两个子树都有值，因此需要选择两者之中更小的那个叠加。
    //        |    |    |    |
    // 2 → - 20 - 21 - 22 - 23  整个矩阵扫描完成后，右下角元素的值就是最小值。
    static int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int row = grid.length;
        int col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i - 1 < 0 && j - 1 < 0) continue;                        // 跳过左上角元素不处理
                else if (i - 1 < 0) grid[i][j] += grid[i][j - 1];            // 第0列的元素跳过左侧分支
                else if (j - 1 < 0) grid[i][j] += grid[i - 1][j];            // 第0行的元素跳过上侧分支
                else grid[i][j] += Math.min(grid[i][j - 1], grid[i - 1][j]); // 中间元素累加左或上之中的最小值
            }
        }
        return grid[row - 1][col - 1];
    }

    // 上面解法的一种更简洁的写法：分开处理第0行，第0列，以及其他元素，巧妙地跳过了左上角元素本身。
    static int minPathSum2(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int row = grid.length;
        int col = grid[0].length;
        for (int i = 1; i < row; i++) grid[i][0] += grid[i - 1][0];     // 第0列
        for (int i = 1; i < col; i++) grid[0][i] += grid[0][i - 1];     // 第0行
        for (int i = 1; i < row; i++)                                   // 其他元素
            for (int j = 1; j < col; j++)
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
        return grid[row - 1][col - 1];
    }

    /** DP解法，Memoization (使用额外空间)，Time - o(n*m), Space - o(n) */
    // 由于状态转移可以看到其实每一刻的状态只与之前的一行和左边的部分有关。
    // 猛一看会以为要使用额外空间的话，必须得用矩阵，用一个数组不够，因为不仅需要上面一行，还要需要左边的部分。
    // 但是仔细分析会发现，并不是需要上面一整行，上面一行所需要的那一部分刚好可以和左边部分拼成完整的一行！所以使用一个数组即可。
    // - - - x x x x
    // x x x y .....
    // 另外一个巧妙地地方在于如何绕过左上角的元素。
    // 为了使代码简洁，我们使用一个长度为列长度加1的数组，而且初始值全设为MAX，唯一除外的就是第1个元素。形如：
    // Max  0  Max  Max  Max  Max   <- dp
    //     00   01   02   03   04   <- grid[0][j]
    // 这样，我们在开始扫描包括左上角元素的第0行时，就会发现，由于dp[0]=Max,dp[1]=0，因此刚好dp[1]会被更新为grid[0][0]，
    // 并且从此之后，dp[1]的值就正常了，而dp[0]=Max的这个值，将会始终用于帮助第0列的元素正确的获得最小值。
    // 这样就成功避免了用更多的代码专门处理边界情况，代码很简洁优美。
    static int minPathSum3(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int row = grid.length;
        int col = grid[0].length;
        int[] dp = new int[col + 1];
        // 非常聪明的地方：初始值设计的很讲究，简化了很多边界条件的处理，还能同时确保正确。第1个而不是第0个设为0，很有用意。
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                dp[j + 1] = Math.min(dp[j], dp[j + 1]) + grid[i][j];
        return dp[col];
    }
}
