package com.leetcode.dp;

/**
 * Created by LYuan on 2016/8/24.
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
 * design an algorithm to find the maximum profit.
 *
 * Example 1:
 * Input: [7, 1, 5, 3, 6, 4]
 * Output: 5
 * max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
 *
 * Example 2:
 * Input: [7, 6, 4, 3, 1]
 * Output: 0
 * In this case, no transaction is done, i.e. max profit = 0.
 *
 * Function Signature:
 * public int bestTimeSellStock(int[] a) {...}
 *
 * 系列问题：
 * E121 Best Time Sell Stock 1: 给定股票价格数组，限制交易一次，求最大利润。
 * M122 Best Time Sell Stock 2: 给定股票价格数组，不限制交易次数，同一时间至多只能持有一笔股票，求最大利润。
 * H123 Best Time Sell Stock 3: 给定股票价格数组，限制交易次数不大于2次，同一时间至多只能持有一笔股票，求最大利润。
 * H188 Best Time Sell Stock 4: 给定股票价格数组，限制交易次数不大于K次，同一时间至多只能持有一笔股票，求最大利润。
 * M309 Best Time Sell Stock 5: 给定股票价格数组，不限制交易次数，同一时间至多只能持有一笔股票，且卖出后需要隔一天才能再买入，求最大利润。
 *
 * */
public class E121_Best_Time_Sell_Stock {
    public static void main(String[] args) {
        int[] a = {7, 1, 5, 3, 6, 4};
        System.out.println(bestTimeSellStock(a));
        System.out.println(bestTimeSellStock2(a));
        System.out.println(bestTimeSellStock3(a));
    }

    /** DP解法, Memoization, Bottom-Up, Iterative */
    // 初始利润为0
    // 第i天的利润，可能有这么两种情况：
    // Case 1: 第i天本身带来的利润(a[i] - minBuy)小于记录下来的利润最大值(profit)，因此第i天的最大利润还是之前的利润，即profit
    // Case 2: 第i天本身带来的利润(a[i] - minBuy)大于了profit，因此更新profit。
    // 所以每个循环除了确定第i天的利润，还要在其后更新当前扫描的所有天中的最低买入值(minBuy)，如果第i天比有记录的最低买入值还小，那么就更新minBuy。
    // 所以相比之下，Fibonacci或HouseRobber等题的状态转移是<相邻节点的状态转移> <= 第i个点的状态(抢劫收益)由第i-1和第i-2个点的状态决定，
    // 而这里的状态转移，是由前面所有点的最大值和最小值决定 <= 第i个点的状态(利润)由前面所有点的最大值和最小值决定。
    static int bestTimeSellStock3(int[] a) {
        if (a == null || a.length == 0) return 0;
        int minBuy = Integer.MAX_VALUE;
        int profit = 0;
        for (int i = 0; i < a.length; i++) {
            profit = Math.max(profit, a[i] - minBuy);       // 状态转移
            minBuy = Math.min(minBuy , a[i]);               // 状态转移
        }
        return profit;
    }


    // 简化版，无需定义max这个变量
    // 因为实际上我真正需要关心的只是当前元素与min的差会不会刷新profit的值，让profit更大而已
    // 所以只需要比较profit和a[i] - min谁更大。
    // if和else的两个分支一个确保在遇到更小元素的时候更新min，一个确保在元素不是最小的情况下更新最大利润，即Math.max(profit, a[i] - min).
    // min一定是在a[i]位置之前得到的，因此a[i] - min一定是正值。
    static int bestTimeSellStock2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int min = a[0];
        int profit = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) min = a[i];
            else profit = Math.max(profit, a[i] - min);     // 加不加else都可以正常判断，只不过不加else会白算一步。
        }
        return profit;
    }

    // 几条规则：
    // 1. 存储当前可以获得的最大利润
    // 2. 如果当前元素比min还小，就更新min为当前元素，并且同时重置max的值为当前元素值（确保卖出时间在买入时间之后）
    // 3. 如果当前元素比max还大，就更新max为当前元素
    // 4. 检查扫描完当前元素后，profit是否能够比当前利润最大（这样可以确保在扫描过程中只留下最大利润的结果）
    static int bestTimeSellStock(int[] a) {
        if (a == null || a.length == 0) return 0;

        int min = a[0];
        int max = a[0];
        int profit = 0;     // 需要存储当前扫描位置的最大获利空间
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
                max = min;
            }
            if (a[i] > max)
                max = a[i];
            profit = Math.max(profit, max - min);
        }
        return profit;
    }
}
