package com.leetcode.dp;

/**
 * Created by Michael on 2016/12/10.
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like (i.e. buy one and sell one share of the stock multiple times).
 * However, you may not engage in multiple transactions at the same time (i.e. you must sell the stock before you buy again).
 *
 * Function Signature:
 * public int bestTimeSellStock(int[] a) {...}
 *
 * 系列问题：（不允许同一天内进行两次操作，先买后卖和先卖后买都不可以）
 * E121 Best Time Sell Stock 1: 给定股票价格数组，限制交易一次，求最大利润。
 * M122 Best Time Sell Stock 2: 给定股票价格数组，不限制交易次数，同一时间至多只能持有一笔股票，求最大利润。
 * H123 Best Time Sell Stock 3: 给定股票价格数组，限制交易次数不大于2次，同一时间至多只能持有一笔股票，求最大利润。
 * H188 Best Time Sell Stock 4: 给定股票价格数组，限制交易次数不大于K次，同一时间至多只能持有一笔股票，求最大利润。
 * M309 Best Time Sell Stock 5: 给定股票价格数组，不限制交易次数，同一时间至多只能持有一笔股票，且卖出后需要隔一天才能再买入，求最大利润。
 *
 */
public class M122_Best_Time_Sell_Stock_2 {
    public static void main(String[] args) {
        System.out.println(bestTimeSellStock(new int[] {1, 2, 3, 9, 7, 8}));
    }

    /** 贪心算法：基本思路是<针对每一段上升序列>实时累计最大收益，最后累加收益即可。*/
    // 思考方式：举例后递推，不断扩大举例规模直至特性显现。
    // 例1：a = [1, 2, 3] 整体是上升序列，最大收益就是 3(max) - 1(min) = 2，但是[3-1]可以分解为[2-1]+[3-2]以方便实现，相当于是实时更新每天的收益。
    // 例2：a = [1, 2, 3, 9] 增加了不连续元素9，但是整体仍然是上升序列，最大收益就等于 9(max) - 1(min) = 8.
    // 例3：a = [1, 2, 3, 9, 7] 增加元素7，只有前半部分是上升序列，但是由于7小于9，因此最大收益仍然就是前半段的最大收益。
    // 例4：a = [1, 2, 3, 9, 7, 8] 增加元素8，包含两个上升序列，各自的最大收益是8和1，因此总最大收益是9。
    // 总结起来，可以将 [2 3 5 7 1 4 3 8 9] 分解为三段上升序列 [2 3 5 7] [1 4] [3 8 9]
    // 然后针对每一段，分解为 7-5+5-3+3-2, 4-1, 9-8+8-3，实时更新累计收益（但并不是在同一天卖出和买入，只是一种等效的分解过程）
    static int bestTimeSellStock(int[] a) {
        if (a == null || a.length == 0) return 0;
        int profit = 0;
        int prev = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > prev) profit += a[i] - prev;     // 上升序列时，不断更新收益
            prev = a[i];
        }
        return profit;
    }

    // 更简洁的写法，推荐。
    static int bestTimeSellStock2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int profit = 0;
        for (int i = 1; i < a.length; i++)
            profit += Math.max(0, a[i] - a[i - 1]);      // 只在上升序列时累加收益。
        return profit;
    }
}
