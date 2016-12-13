package com.leetcode.dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michael on 2016/12/11.
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit. You may complete at most two transactions.
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
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
public class H123_Best_Time_Sell_Stock_3 {
    public static void main(String[] args) {
        System.out.println(bestTimeSellStock(new int[] {1, 2, 4, 2, 5, 7, 2, 4, 9, 0}));
        System.out.println(bestTimeSellStock2(new int[] {1, 2, 4, 2, 5, 7, 2, 4, 9, 0}));
        System.out.println(bestTimeSellStock3(new int[] {1, 2, 4, 2, 5, 7, 2, 4, 9, 0}));
    }

    // 错误解法：认为只要按照M122的方法记录下来所有每个分组的profit值然后取最大的两个加起来即可。但是这种策略未能考虑到多个分区合并后的最大值是会变化的。
    // 按照M122的策略，处理这种情况是对的： [2 3 5 7 1 4 3 8 9] 分解为三段上升序列 [2 3 5 7] [1 4] [3 8 9]，profit = 5, 3, 6, 因此最大为5+6=11
    // 但是处理这种情况就不行了：[2 3 5 7 3 9 3 8 9] 分解为 [2 3 5 7] [3 9] [3 8 9], profit = 5, 6, 6, 但最大并不是6+6=12，
    // 而是[2 3 5 7 3 9] [3 8 9] 的7+6=13。

    /** 解法3：通用DP解法，使用二维数组来备忘，解法来自于H188。 */
    // 这里的最大交易次数是2，因此只需要一个3行的二维数组即可。
    static int bestTimeSellStock3(int[] a) {
        int[][] dp = new int[3][a.length];
        for (int i = 1; i < 3; i++) {
            int max = -a[0];
            for (int j = 1; j < a.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], a[j] + max);
                max = Math.max(max, dp[i - 1][j - 1] - a[j]);
            }
        }
        return dp[2][a.length - 1];
    }

    /** 解法2：DP的变体，省略了备忘用的二维数组，用四个变量代替。更为简洁。 */
    // 正号表示卖出的收益，负号表示买入时的花销（即负收益）
    static int bestTimeSellStock2(int[] a) {
        int buy1 = Integer.MIN_VALUE;
        int buy2 = Integer.MIN_VALUE;
        int sell1 = 0;
        int sell2 = 0;
        for(int i = 0; i < a.length; i++){
            sell2 = Math.max(sell2, buy2 + a[i]);   // 选择在第i天卖出，交易收益为buy2 + a[i]，如果能比sell2大就更新sell2
            buy2 = Math.max(buy2, sell1 - a[i]);    // 选择在第i天买入，交易收益为sell1 - a[i]（第一次交易的总收益加这次花销），如果能比buy2大就更新buy2
            sell1 = Math.max(sell1, buy1 + a[i]);   // 选择在第i天卖出，交易收益为buy1 + a[i]，如果能比sell1大就更新sell1
            buy1 = Math.max(buy1, - a[i]);          // 选择在第i天买入，交易收益为-a[i]（因为这是第一次交易），如果能比buy1大就更新buy1
        }
        return sell2;
    }


    /** 解法1：正反两次扫描解法。Time - o(2n), Space - o(n) */
    // 第一次进行正向扫描：用额外的数组记录从最左端（i = 0）到每一天（i = 1 to len）的区间内可以达到的<单次>最大收益。
    // 这个单次最大收益可以有两种可能：1. 当前元素减掉到目前为止的最小元素的差值。2. 前面区间曾经获得到的最大收益值。
    // Example: a = [1 2 4 2 5 7 2 4 9 0]
    //  maxprofit = [0 1 3 3 4 6 6 6 8 8]
    // 第二次进行反向扫描：依次计算以当前元素作为分段点的两次买卖的收益总和，并记录最大值，就是两次买卖所能获得的最大值。
    // 其中两次买卖的收益 = 左侧买卖（maxprofit[i]）+ 右侧买卖（max - a[i]）
    // 这种计算方法自动的包含了两次买卖其实是一次买卖的情况，例如全上升序列[1 3 7 9 10 14]，在每一点进行分段其实都是相同的值，因为根本一段就可以。
    static int bestTimeSellStock(int[] a) {
       if (a == null || a.length <= 1) return 0;
       int[] maxprofit = new int[a.length];
       int min = a[0];
       for (int i = 1; i < a.length; i++) {
           maxprofit[i] = Math.max(maxprofit[i - 1], a[i] - min);     // 记录到第i个元素为止可以获得的最大profit：一种可能是第i个元素减当前最小值，一种可能是前面元素获得的最大profit
           min = Math.min(min, a[i]);       // 当前序列的最小值实时更新
       }
       int max = a[a.length - 1];
       int result = 0;
       for (int i = a.length - 2; i >= 0; i--) {
           max = Math.max(a[i], max);
           result = Math.max(result, max - a[i] + maxprofit[i]);
       }
       return result;
    }


    // 下面只是我一开始的思路，但后来发现不太有效。不应该以上升序列为主，而应该以交易为主。
    // 起初我的思路找到相邻两个分段之间的最大收益，然后步步为营，但是后来发现每新加一段，就需要检查新的一段和已扫描所有段结合的收益，算法反而复杂化了。
    // 如果上升序列段小于等于2段，那么最大profit一定是两段之和。无需考虑
    // 上升序列的定义：序列中每个元素的值都大于等于左侧相邻元素的值。
    // 两个相邻上升序列A和B具有隐式特性：<B的起点元素一定小于A的终止元素>
    // A/B两序列的位置关系有3种可能：
    //  Case 1               Case 2              Case 3           这种情况不存在
    // [1 3] [2 4] √       [1 3] [0 4] √      [2 4] [1 3] X       [1 2] [5 7]
    // ┏━━━━━┓              ┏━━━━━┓               ┏━━━━━┓        ┏━━━━━┓
    // ┗━━━━━┛ A            ┗━━━━━┛ A             ┗━━━━━┛ A      ┗━━━━━┛ A
    //     ┏━━━━━┓        ┏━━━━━━━━━┓         ┏━━━━━┓                      ┏━━━━━┓
    //     ┗━━━━━┛ B      ┗━━━━━━━━━┛ B       ┗━━━━━┛ B                    ┗━━━━━┛ B
    // ┏━━━━━━━━━┓          ┏━━━━━━━┓             ┏━┓             因为[1 2 5 7]是一个上升序列。
    // ┗━━━━━━━━━┛ A+B      ┗━━━━━━━┛ A+B         ┗━┛ A+B
    // 从上面三种可能位置关系可以看到，前两种情况下，A和B组合之后可以扩增容量。即<B的终止元素一定要大于A的终止元素>
}
