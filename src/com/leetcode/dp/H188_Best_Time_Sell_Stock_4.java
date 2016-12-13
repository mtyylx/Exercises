package com.leetcode.dp;

/**
 * Created by LYuan on 2016/12/13.
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete at most k transactions.
 * Note: You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 *
 * Function Signature:
 * public int bestTimeSellStock(int[] a, int k) {...}
 *
 * 系列问题：（不允许同一天内进行两次操作，先买后卖和先卖后买都不可以）
 * E121 Best Time Sell Stock 1: 给定股票价格数组，限制交易一次，求最大利润。
 * M122 Best Time Sell Stock 2: 给定股票价格数组，不限制交易次数，同一时间至多只能持有一笔股票，求最大利润。
 * H123 Best Time Sell Stock 3: 给定股票价格数组，限制交易次数不大于2次，同一时间至多只能持有一笔股票，求最大利润。
 * H188 Best Time Sell Stock 4: 给定股票价格数组，限制交易次数不大于K次，同一时间至多只能持有一笔股票，求最大利润。
 * M309 Best Time Sell Stock 5: 给定股票价格数组，不限制交易次数，同一时间至多只能持有一笔股票，且卖出后需要隔一天才能再买入，求最大利润。
 */
public class H188_Best_Time_Sell_Stock_4 {
    public static void main(String[] args) {
        System.out.println(bestTimeSellStock(new int[] {2, 3, 5, 7, 3, 9, 3, 8, 9}, 3));
    }

    /** DP解法，大概是最近做的最难的一道题了。 */
    // 视角聚焦在交易上，而不是上升序列上。一次交易由分布在不同天的1次买入和1次卖出构成。
    /** 关键1：找到递推关系。*/
    // dp[i]表示最多交易i次的情况下，在不同区间内所能获得的最大收益表。
    // dp[i][j]表示最多交易i次的情况下，从第0天到第j天这个区间内所能获得的最大收益值。
    // 递推关系：dp[i]数组的元素完全可以通过dp[i - 1]计算获得。
    /** 关键2：分情况处理。当k >= len/2的情况下就退化至M122的解法。*/
    // 题目限定一天只能做一件事，要不买要不卖，而一次transaction等效于1次买1次卖（两天），因此对于长度为len的天数，交易次数最多不会超过len/2
    // 一旦限定的最大交易次数大于了len/2，就说明这种场景下的交易次数可以是任意次。也就退化成为了M122的区间解题法。
    /** 关键3：讨论第j天能获得的最大收益。 */
    // Case #1：如果第j天什么都不做，那么第j天的最大收益肯定和前一天一样，即dp[i][j] = dp[i][j - 1]
    // Case #2：如果第j天卖出，那么肯定在[0,j - 1]区间内买入过一次，比如买入这天是第x天，那么x这天之前就发生了至多i - 1次交易。
    // 此时dp[i][j]由两部分构成，
    // 第一部分是发生在第X天以前的<至多i - 1次交易>的最大收益值：dp[i - 1][x - 1]
    // 第二部分是从第X天买入到第j天卖出的<这1次交易>的最大收益值：a[j] - a[x]
    // 为了求这两部分的整体最大收益，我们将它交叉分解，分解为max = dp[i - 1][x - 1] - a[x] 和　a[j]
    //                                                    ↳最多i-1次交易收益     ↳Buy    ↳Sell
    // 重新分解的表达式中，带正号的a[j]表示在第j天卖出的收益，带负号的a[x]是个下一循环用的，表示在第x天买入的收益，因为买入是花钱，所以是负值。
    // 为了简化代码，将x和j都用j来循环，只不过x是延迟到下一循环才使用。
    // 另外max的初始值严格来讲是dp[i - 1][0] - a[0]，但是因为不管允许最多交易多少次，如果区间只是第0天，那么不可能完成买入和卖出
    // 因此dp[i - 1][0]恒为0，即max = -a[0]，含义是在第0天区间内只买不卖的最大收益。（只买不卖是因为Case#2的卖要留到第j天）

    public static int bestTimeSellStock(int[] a, int k) {
        if (a == null || a.length < 2) return 0;
        int len = a.length;

        if (k >= len/2) {               // Degrade to M122: Unlimited transactions.
            int profit = 0;
            for (int i = 1; i < len; i++)
                if (a[i] > a[i - 1]) profit += a[i] - a[i - 1];
            return profit;
        }

        int[][] dp = new int[k + 1][len];       // 备忘，根据递推关系更新dp[i][j]和max
        for (int i = 1; i <= k; i++) {
            int max = dp[i - 1][0] - a[0];
            for (int j = 1; j < len; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], a[j] + max);      // a[j] + max = a[j] - a[x] + dp[i - 1][x - 1]
                max = Math.max(max, dp[i - 1][j - 1] - a[j]);       //                ↳Sell  ↳Buy           ↳Before Buy
            }
        }
        return dp[k][len - 1];
    }

    //     a = [2  3  5  7  3  9  3  8  9]
    // dp[0] = [0  0  0  0  0  0  0  0  0] transaction <= 0，收益恒为0
    // dp[1] = [0  1  3  5  5  7  7  7  7] transaction <= 1
    //   max = -2 -2 -2 -2 -2 -2 -2 -2 -2
    // dp[2] = [0  1  3  5  5 11 11 12 13] transaction <= 2
    //   max = -2 -2 -2 -2 -2  2  2  4  4
    // dp[3] = [0  1  3  5  5 11 11 16 17] transaction <= 3
    //   max = -2 -2 -2 -2 -2  2  2  8  8
}
