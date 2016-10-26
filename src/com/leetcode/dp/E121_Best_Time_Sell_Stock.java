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
 * */
public class E121_Best_Time_Sell_Stock {
    public static void main(String[] args) {
        int[] a = {7, 1, 5, 3, 6, 4};
        System.out.println(bestTimeSellStock2(a));
    }

    // 简化版，无需定义max这个变量
    // 因为实际上我真正需要关心的只是当前元素与min的差会不会刷新profit的值，让profit更大而已
    // 所以只需要比较profit和a[i] - min谁更大。
    static int bestTimeSellStock2(int[] a) {
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
        if (a.length == 0) return 0;

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
