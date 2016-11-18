package com.leetcode.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/10/26.
 * Intro-level algorithm for Dynamic Programming.
 * Calculate the nth number in Fibonacci series.
 * Example:
 * fib(1) = 1
 * fib(2) = 1
 * fib(3) = 2
 * fib(4) = 3
 * fib(5) = 5
 * fib(x) = fib(x - 1) + fib(x - 2)
 *
 */
public class Basic_Dynamic_Programming {
    public static void main(String[] args) {
        for(int i = 1; i <= 50; i++) {
            System.out.println("Naive: " + fib_naive(i));   // 随着i变大，直接递归不带备忘的运算复杂度上升速度是Exponentially (2^n)的。
            System.out.println("DP Top-down: " + fib_top_down(i));
            System.out.println("DP Bottom-Up: " + fib_bottom_up(i));
        }
    }

    // 应将斐波那契数列看作一个以n为根的二叉树：
    //                      fib(n)
    //                      /    \
    //              fib(n-1)      fib(n-2)
    //              /      \       /     \
    //       fib(n-2)  fib(n-3) fib(n-3) fib(n-4)

    /** 递推解法，递归形式，time - o(2^n), space - o(1) */
    // 虽然只需一行，但是时间复杂度非常高。主要原因是这里面的绝大多数运算都是重复的。
    // 重复的根源在于相邻两个点的分支中至少有一个分支是相同的。等效于一个二叉树结构。
    static long fib_naive(int n) {
        if (n < 3) return 1;
        return fib_naive(n - 1) + fib_naive(n - 2);
    }

    /** DP解法，Top-Down，Memo，递归形式，time - o(n), space - o(2n) */
    // 采用Top-Down方式，因为任务是求第n个斐波那契数，所以上来就求fib(n)。由于fib(n)由fib(n-1)和fib(n-2)决定，因此转而求这两个斐波那契数。
    // 对比fib_naive的递归方法，可以看到，整个架构其实一样的，都分成n < 3和n >= 3的情况。
    // 区别在于，对于上面这两种情况我都会把fib(n)计算结果存在dp[n]中。然后每次递归前先看dp[xxx]是不是已经算过了，没算过才递归
    // 需要定义Wrapper方法，因为备忘数组dp必须脱离递归方法本身独立存在，但是数组的长度又由问题规模决定。
    private static long[] dp;
    static long fib_top_down(int n) {
        dp = new long[n + 1];
        return fib(n);
    }

    static long fib(int n) {
        if (n < 3) { dp[n] = 1; return 1; }
        return dp[n] = (dp[n - 1] == 0 ? fib(n - 1) : dp[n - 1]) +     // 等于0说明还没有计算过
                       (dp[n - 2] == 0 ? fib(n - 2) : dp[n - 2]);
    }

    /** DP解法，Bottom-Up，Memo，迭代形式，time - o(n), space - o(n) */
    // 采用Bottom-up顺序，虽然任务是求第n个斐波那契数，我们并不上来就求fib(n)，而是从fib(1)开始
    // 这就是所谓的“不积跬步，无以至千里”吧。我们要以积少成多的方式搞定它。
    // for循环直接从1开始计算，相当于是从树最底端的叶子节点开始计算，在不断向上爬的过程中顺序填满备忘录数组
    // 由于每个值都是顺序的由前面两个值计算得来，因此备忘用的状态数组中的元素是顺序填满的。无需像递归解法一样判断是否已填上。
    static long fib_bottom_up(int n) {
        long[] state = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i < 3) state[i] = 1;
            else state[i] = state[i - 1] + state[i - 2];
        }
        return state[n];
    }

    /** DP解法，Bottom-Up，Memo，迭代形式，优化空间复杂度，time - o(n), space - o(1) */
    // 上面的解法空间复杂度是o(n)，但是本质上来讲，第N个斐波那契数只跟它前面的两个数有关，并不需要前面所有的数。
    // 所以空间复杂度应该可以从o(n)降至o(1)，直接用3个整型变量缓存轮换更新(n-2),(n-1),n这三者的值。
    static long fib_bottom_up2(int n) {
        long a = 1;
        long b = 1;
        long current = 0;
        for (int i = 3; i <= n; i++) {
            current = a + b;
            a = b;
            b = current;
        }
        return n < 3 ? 1 : current;
    }


}
