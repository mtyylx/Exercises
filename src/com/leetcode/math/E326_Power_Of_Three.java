package com.leetcode.math;

import java.util.*;

/**
 * Created by LYuan on 2016/10/17.
 * Given an integer, write a function to determine if it is a power of three.
 * Follow up: Could you do it without using any loop / recursion?
 *
 * Function Signature:
 * public boolean isPowerOfThree(int n) {...}
 */
public class E326_Power_Of_Three {
    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            System.out.println(i + ":" + isPowerOfThree4(i));
        }
    }

    /** 短除解法 */
    static boolean isPowerOfThree(int n) {
        if (n < 1) return false;
        while (n % 3 == 0) n /= 3;
        return n == 1;
    }

    /** 质数特性：3^19相当于只有唯一的质数因子3，因此除非除数也只包含3，否则余数不可能为0. */
    static boolean isPowerOfThree2(int n) {
        return n > 0 && 1162261467 % n == 0;
    }

    /** 反求对数：换底公式 */
    // log3 (n) 如果是整数那么n一定是3的幂。
    // log3 (n) = Math.log10(n) / Math.log10(3) 使用Math库的对数API求解。
    // 任何整数求模1都是0，任何小数求模1都不是0，这就是模1运算的功能：区分是否带有小数位。
    // 但是这里不能用自然对数Math.log(n)，会恰好无法得到整数，因此243会误判为不是3的幂。
    static boolean isPowerOfThree4(int n) {
        return n > 0 && Math.log10(n) / Math.log10(3) % 1 == 0;
    }

    /** 穷举出来Integer.MAX_VALUE范围内的所有3的幂 */
    static boolean isPowerOfThree3(int n) {
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 3, 9, 27, 81, 243, 729, 2187, 6561,
                19683, 59049, 177147, 531441, 1594323, 4782969, 14348907, 43046721, 129140163, 387420489, 1162261467));
        return set.contains(n);
    }
}
