package com.leetcode.dp;

/**
 * Created by Michael on 2016/10/26.
 * Intro-level algorithm for Dynamic Programming.
 * Calculate the nth number in Fibonacci series.
 * fib(0) = 0
 * fib(1) = 1
 * fib(x) = fib(x - 1) + fib(x - 2)
 *
 */
public class Basic_Dynamic_Programming {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) System.out.println(fib_dp(i));
    }

    /** 递推解法，递归形式 */
    static int fib_recursive(int n) {
        if (n < 2) return n;
        return fib_recursive(n - 1) + fib_recursive(n - 2);
    }

    /** DP解法，迭代形式 */
    static int fib_dp(int n) {
        int[] state = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            if (i < 2) state[i] = i;
            else {
                state[i] = state[i - 1] + state[i - 2];
            }
        }
        return state[n];
    }


}
