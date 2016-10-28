package com.leetcode.dp;

/**
 * Created by LYuan on 2016/10/28.
 * There is a fence with n posts, each post can be painted with one of the k colors.
 * You have to paint all the posts such that no more than two adjacent fence posts have the same color.
 * Return the total number of ways you can paint the fence.
 * Note: n and k are non-negative integers.
 *
 * Function Signature:
 * public int paintWays(int n, int k) {...}
 */
public class E276_Paint_Fence {
    public static void main(String[] args) {
        System.out.println(paintWays(3, 3));
    }

    /** DP解法，Memoization，BottomUp，Iterative：Time - o(n), Space - o(1) */
    // 难点在于定义状态转移方程
    // 首先列出特例：
    // n = 0, result = 0
    // n = 1, result = k
    // n = 2, result = k * k = same + diff = 两节点相同条件下的状态数 + 两节点不同条件下的状态数 = 两节点状态的全排列。
    // 从n = 3开始（当前节点为3）存在稳定的状态转移，分为两种情况：
    /** Case 1. 前提条件：i与(i-1)状态相同。所有状态数 = (i-1)与(i-2)不相同的状态数。
     *  Case 2. 前提条件：i与(i-1)不同。所有状态数 = (i-1)具有的总状态数 * (k - 1)。 */
    // 对于Case 1，由于前提条件是当前节点i与上一节点(i-1)不同，又因为不能存在超过两个节点相同状态的状态 (no more than two adjacent fence)，
    // 所以，所有状态数等于(i-1)与(i-2)不相同的状态数，即prevDiff。
    // 对于Case 2，由于前提是当前节点i与上一节点(i-1)不同，所以上一节点(i-1)既可以与上上节点(i-2)相同，即prevSame，也可以不同，即prevDiff
    // 因此对于(i-1)节点每一个可能的状态，只能搭配(k-1)个当前节点i的可能状态，因此整体状态数需要乘(k-1)，即(prevSame+prevDiff)*(k-1)

    static int paintWays2(int n, int k) {
        // 特例处理
        if (n == 0 || k == 0) return 0;
        if (n == 1) return k;
        if (n == 2) return k * k;
        // 定义初始状态（对于n = 3来说的初始状态）
        int prevSame = k;
        int prevDiff = k * (k - 1);
        int same = 0;
        int diff = 0;
        // 状态转移
        for (int i = 2; i < n; i++) {
            same = prevDiff;                            // Case 1
            diff = (prevSame + prevDiff) * (k - 1);     // Case 2
            prevSame = same;        // 变成上一节点
            prevDiff = diff;        // 变成上一节点
        }
        return same + diff;
    }

    // 精简版本，状态转移关系没有那么清楚了。
    static int paintWays(int n, int k) {
        if (n == 0 || k == 0) return 0;
        if (n == 1) return k;
        int same = k;
        int diff = k * (k - 1);
        // Start from n = 3
        for (int i = 2; i < n; i++) {
            int prevDiff = diff;
            diff = (prevDiff + same) * (k - 1);
            same = prevDiff;
        }
        return diff + same;
    }
}
