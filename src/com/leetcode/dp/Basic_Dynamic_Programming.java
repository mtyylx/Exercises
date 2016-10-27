package com.leetcode.dp;

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
        for(int i = 1; i <= 50; i++) System.out.println(fib_memo(i));
    }

    // 应将斐波那契数列看作一个以n为根的二叉树：
    //                      fib(n)
    //                      /    \
    //              fib(n-1)      fib(n-2)
    //              /      \       /     \
    //       fib(n-2)  fib(n-3) fib(n-3) fib(n-4)

    /** 递推解法，递归形式
     *  time - o(2^n), space - o(1)
     */
    // 虽然只需一行，但是时间复杂度非常高。主要原因是这里面的绝大多数运算都是重复的。
    // 重复的根源在于相邻两个点的分支中至少有一个分支是相同的。等效于一个二叉树结构。
    static long fib_naive(int n) {
        return n < 3 ? 1 : fib_naive(n - 1) + fib_naive(n - 2);
    }

    /** DP解法，Memoization，Top-Down，递归形式
     *  time - o(n), space - o(2n)
     */
    // Memoization：这里使用一个数组来存储已经计算过的节点值，相当于是做了备忘（Memo）。
    // Top-down顺序：上来就计算位于树顶端的fib(n)然后递归至叶子节点。
    // 由于计算fib(n)需要依赖fib(n-1)和fib(n-2)，所以查数组中的这两个值是否已经计算过了，
    // 如果没有，就递归，如果有，就直接取数组里的值，而不再递归，这就是这个解法比纯递推解法性能提升的根本原因。
    // 递归函数需要包一层，Memoization的数组必须是公用的，因此需要作为参数传入每个递归函数。
    static long fib_memo(int n) {
        long[] state = new long[n + 1];
        return fib_memo(n, state);
    }

    static long fib_memo(int n, long[] state) {
        if (n < 3) {
            state[n] = 1;
            return 1;
        }
        if (state[n - 1] == 0) state[n - 1] = fib_memo(n - 1, state);   // 只有在该节点没有计算过的情况下(0)，才递归调用。
        if (state[n - 2] == 0) state[n - 2] = fib_memo(n - 2, state);   // 只有在该节点没有计算过的情况下(0)，才递归调用。
        state[n] = state[n - 1] + state[n - 2];
        return state[n];
    }

    /** DP解法，Memoization，Bottom-Up，迭代形式
     *  time - o(n), space - o(n)
     */
    // Memoization：同样使用一个数组来存储已经计算过的节点数值。
    // Bottom-up顺序：for循环直接从1开始计算，相当于是从树最底端的叶子节点开始计算，在不断向上爬的过程中顺序填满备忘录数组
    // 由于每个值都是顺序的由前面两个值计算得来，因此备忘用的状态数组中的元素是顺序填满的。无需像递归解法一样判断是否已填上。
    static long fib_dp(int n) {
        long[] state = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i < 3) state[i] = 1;
            else state[i] = state[i - 1] + state[i - 2];
        }
        return state[n];
    }

    /** DP解法，Memoization，Bottom-Up，迭代形式，优化空间复杂度
     *  time - o(n), space - o(1)
     */
    // 上面的解法空间复杂度是o(n)，但是本质上来讲，第N个斐波那契数只跟它前面的两个数有关，并不需要前面所有的数。
    // 所以空间复杂度应该可以从o(n)降至o(1)
    // 下面使用3个缓存整型变量来轮换更新(n-2),(n-1),n这三者的值。
    static long fib_dp2(int n) {
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
