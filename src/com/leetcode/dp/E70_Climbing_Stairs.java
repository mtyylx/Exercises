package com.leetcode.dp;

/**
 * Created by LYuan on 2016/10/27.
 * You are climbing a stair case.
 * It takes n steps to reach to the top.
 * Each time you can <either climb 1 or 2 steps>.
 * In how many <distinct ways> can you climb to the top?
 *
 * Function Signature:
 * public int climbStairs(int n) {...}
 */
public class E70_Climbing_Stairs {
    public static void main(String[] args) {

    }

    // 找规律：找到相邻的n之间的扩展关系，而不是按照传统思路孤立的列举每个n下的排列组合。
    // n = 1     n = 2     n = 3     n = 4      n = 5
    // (1)       (2)       (1,2) *   (2,2) *    (1,2,2) *
    //           (1,1)     (2,1)     (1,1,2) *  (2,1,2) *
    //                     (1,1,1)   (1,2,1)    (1,1,1,2) *
    //                               (2,1,1)    (2,2,1)
    //                               (1,1,1,1)  (1,1,2,1)
    //                                          (1,2,1,1)
    //                                          (2,1,1,1)
    //                                          (1,1,1,1,1)
    // 上面打*的结果来自n - 2.
    // 从n = 3开始，结果完全等于(n-1)的所有结果后缀1 与 (n-2)的所有结果后缀2，这两者的总和。
    // 所以递推的逻辑和Fibonacci是一模一样的，只是起始基数不同而已：
    // Fibonacci: 1, 1, 2, 3, 5, 8
    // Stairs: 0, 1, 2, 3, 5, 8

    /** DP, Memoization, Bottom-Up, Iterative: Time - o(n), Space - o(n) */
    static int climbStairs(int n) {
        int[] ways = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            if (i < 3) ways[i] = i;
            else ways[i] = ways[i - 1] + ways[i - 2];
        }
        return ways[n];
    }

    /** DP, Memoization, Bottom-Up, Iterative: Time - o(n), Space - o(1) */
    // 优化了空间复杂度，只用了三个int而不是一个int[n + 1]。
    static int climbStairs2(int n) {
        int a = 1;
        int b = 2;
        int current = 0;
        for (int i = 3; i <= n; i++) {
            current = a + b;
            a = b;
            b = current;
        }
        return n < 3 ? n : current;
    }
}
