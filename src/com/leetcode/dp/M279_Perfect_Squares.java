package com.leetcode.dp;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by LYuan on 2016/11/3.
 * Given a positive integer n,
 * find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 *
 * For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.
 *
 * Function Signature:
 * public int minComb(int n) {...}
 */
public class M279_Perfect_Squares {
    public static void main(String[] args) {
        System.out.println(minComb3(12));
    }

    /** DP解法，Memoization，Iterative，Time - o(n*sqrt(n)), Space - o(n) */
    // 难点在于找到<最少>平方数的组合。
    // 例如12 = 9 + 1 + 1 + 1 = 4 + 4 + 4，可以看到，虽然用最大平方数的不断减当前数一定可以找到一个平方数组合，但是没法保证是最小的。
    // 所以一般的思路是对于n，需要把他拆分成为所有允许平方数作为最大值的组合。
    // 例如下面对于12的分解过程，12下有三个平方数1/4/9，因此每一层都需要按1/4/9分解，直到不能再分解。
    //     第一次分解    第二次分解         第三次分解         第x次分解
    // 12 = (9) + 3  = 9 + 1 + 2    = 9 + 1 + 1 + 1
    //    = (4) + 8  = 4 + 4 + 4
    //    = (1) + 11 = 1 + (9) + 2  = 1 + 9 + 1 + 1
    //               = 1 + (4) + 7  = 1 + 4 + (4) + 3 = ...
    //                              = 1 + 4 + (1) + 6 = ...
    //               = 1 + (1) + 10 = 1 + 1 + (9) + 1
    //                              = 1 + 1 + (4) + 6 = ...
    //                              = 1 + 1 + (1) + 9 = ...
    // 对于一个很大的n，他下面的所有平方数个数应该为(int)sqrt(n)，但是每减去这些平方数之一所获得的新数，都需要再分解。因此是个级数的形式。
    // 如果干算的话，计算量很大，应该大于n^2小于n的阶乘。
    // 但是从上面的排列组合可以看到，其实重复的运算很多，比如第一次分解时得到需要查的新数值是3/8/11，这些值本身需要通过minComb(3)/(8)/11来得到。
    // 由于这些值一定在n之前出现，因此如果从头开始计算，打好基础的话，其实对于3/8/11的minComb已经计算得到了，这就是Memoization的体现。
    // 在此基础上，再加1即可（这个1就是第一次分解时减掉的那个平方数）
    // 上面的计算就变成了下面的简单运算，其中dp[3], dp[8], dp[11]会在之前就算出来存好了。
    // 12 = 1(9) + dp[3]  = 1 + 3 = 4 \
    //    = 1(4) + dp[8]  = 1 + 2 = 3 | --> min = 3
    //    = 1(1) + dp[11] = 3 + 1 = 4 /
    // 可以看到，这个问题的dp[i]不只取决于与dp[i - 1]的值，还取决于之前所有的dp（准确的说是绝大多数的状态）
    static int minComb(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int j = 2; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

    // 下面的解法是错误的，虽然考虑到例如12这种最小组合并不是由最大平方数构成的情况，针对所有可能的平方数进行了计算
    // 但是因为只循环了一层，也就是说只是把问题的规模削减了一层，当n更大时，像12一样的问题依然会暴露出来。
    // 例如43，虽然下面的内循环while遍历了1/4/9/16/25/36，但是减掉这些数之一之后，
    // 例如得到了43-25=18时，再对18分解时，依然会采用16而不是9，因而没法得到真正的最小值。
    static int minComb2(int n) {
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            int count = 0;
            int x = n;
            int j = i;
            while (x > 0) {
                while (j * j > x) j--;  // 没有遍历所有平方数可能
                x -= j * j;
                count++;
            }
            min = Math.min(min, count);
        }
        return min;
    }

    /** BFS解法，将问题转化为一个树结构（但并非二叉树） */
    // 效率比上面的DP解法更高。因为从1到n的DP中有一部分是没有用上的。
    //                            12
    //                   /1       |4       \9
    //                  11        8         3
    //               /1 |4 \9    /1 \4      |1
    //             10   7   2   7   4       2
    //           / | \ /\   |  /\   |4      |1
    //          9  6 1 6 3  1  6 3  0       1
    //                              ↑ 找到最短path，结束搜索。
    static int minComb3(int n) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(n);
        int depth = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();    // 需要按层扫描，为的是记录层高。（一般的广度遍历其实并不需要记录这个，只需要不断从队列取任务即可。）
            for (int j = 0; j < size; j++) {
                int current = queue.remove();
                int range = (int) Math.sqrt(current);
                for (int i = range; i > 0; i--) {           // 用逆序而不是正序扫描，是因为用大的平方数分解更容易min。（例如8=4+4而不是1+1+...)
                    if (current - i * i == 0) return depth; // 只要节点为0，就说明已经找到了一条最短路径。应该立即结束其他分支无用的计算。
                    queue.add(current - i * i);
                }
            }
            depth++;
        }
        return depth;
    }
}
