package com.leetcode.math;

/**
 * Created by LYuan on 2016/10/17.
 * Write a program to check whether a given number is an ugly number.
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 *
 * For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.
 * Note that 1 is typically treated as an ugly number.
 *
 * Function Signature:
 * public boolean isUgly(int n) {...}
 */
public class E263_Ugly_Number {
    public static void main(String[] args) {

    }

    /** 短除解法，考虑负数，0，1等情况。 */
    // 负数因为一定有-1这个东东，所以应该不算Ugly。
    // 如果短除到了1，或其他质数因子，再检查是否是1，如果是1那么就是Ugly.
    static boolean isUgly(int n) {
        if (n < 0) return false;
        while (n > 1) {
            if      (n % 2 == 0) n /= 2;
            else if (n % 3 == 0) n /= 3;
            else if (n % 5 == 0) n /= 5;
            else break;
        }
        return n == 1;
    }
}
