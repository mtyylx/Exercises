package com.leetcode.math;

/**
 * Created by LYuan on 2016/10/17.
 * Given an integer n, return the number of trailing zeroes in n!.
 * Note: Your solution should be in logarithmic time complexity.
 *
 * Funtion Signature:
 * public int trailingZeros(int n) {...}
 */
public class E172_Factorial_Trailing_Zeros {
    public static void main(String[] args) {
        System.out.println(trailingZeros3(100));
    }

    /** 穷举找规律解法 time - o(logn) */
    // 一开始直觉的想法是数有多少个10，以及多少个2和5的对
    // 但是仔细想就会发现，其实10本质上也是2乘以5，
    // 阶乘看上去是4*3*2*1，但实际上只是无数个质数因子重复相乘的过程：2*2*2*3*1
    // 因此想要知道有多少个0，那么只需要有多少个2*5即可
    // 2非常容易获得，可以说是物产丰富的资源，而5就相对没那么多了，因此问题可以进一步简化成为计算5的个数。
    // 20的阶乘中只有5,10,15,20含有5，再将它们质数分解就是5*5*5*5*2*3*4，搭配大把的2，因此有4个零。也就是20/5 = 4
    // 25的阶乘中则有5,10,15,20,25含有5，但是25/5后还是5，因此有5个零。也就是25/5 + 5/5 = 5 + 1 = 6.
    // 所以不需要线性的扫描从n到1的所有值，只需要不断短除5，再累加就是5的总个数了。
    // 100的阶乘，100/5 + 20/5 + 4/5 = 20 + 4 + 0 = 24.
    static int trailingZeros(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n / 5;
            n /= 5;
        }
        return sum;
    }

    static int trailingZeros3(int n) {
        return n == 0 ? 0 : n / 5 + trailingZeros3(n / 5);
    }

    /** 穷举找规律解法 time - o(n) */
    // 遍历从n到1的每一个数，短除每个数以获得每个数种包含的5的个数。
    static int trailingZeros2(int n) {
        int sum = 0;
        for (int i = n; i > 0; i--) {
            int x = i;
            while (x % 5 == 0) {
                sum++;
                x /= 5;
            }
        }
        return sum;
    }
}
