package com.leetcode.math;

/**
 * Created by LYuan on 2016/10/17.
 * Given an integer, write a function to determine if it is a power of two.
 *
 * Function Signature:
 * public boolean isPowerOfTwo(int n) {...}
 */
public class E231_Power_Of_Two {
    public static void main(String[] args) {

    }

    /** 短除解法，关键在于考虑全面各种边界情况 */
    // 一个数如果是2的幂，那么它一定只包含1和2这两个因子。
    // n首先不能小于1.
    // n等于1时应该返回true.
    // 循环终止条件应该是n > 1，而不是一般的n > 0. 因为2的幂也包含1.
    // time - o(logn)
    static boolean isPowerOfTwo(int n) {
        if (n < 1) return false;
        while (n > 1) {
            if (n % 2 != 0) return false;
            n /= 2;
        }
        return true;
    }

    /** 简化解法，只要最后的非2因子不是1，就说明这个数一定不是2的幂，还包含别的因子 */
    // time - o(logn)
    static boolean isPowerOfTwo2(int n) {
        if (n < 1) return false;
        while (n % 2 == 0) n /= 2;
        return n == 1;
    }

    /** 比特翻转法：利用了2的幂都是只有一个比特位为1的特性 */
    // time - o(1)
    // 1 = 0000001 & 0000000 = 0
    // 2 = 0000010 & 0000001 = 0
    // 4 = 0000100 & 0000011 = 0
    // 8 = 0001000 & 0000111 = 0
    static boolean isPowerOfTwo3(int n) {
        return (n & (n - 1)) == 0 && n > 0;
    }
}
