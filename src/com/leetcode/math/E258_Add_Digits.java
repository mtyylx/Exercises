package com.leetcode.math;

/**
 * Created by Michael on 2016/10/15.
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
 *
 * For example:
 * Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.
 * Could you do it without any loop/recursion in O(1) runtime?
 *
 * Function Signature:
 * public int addInteger(int a) {...}
 */
public class E258_Add_Digits {
    public static void main(String[] args) {
        System.out.println(addDigits4(11));
    }

    /**
     * The period is 9 (1 to 9), except 0.
     * 0 = 0
     * 1 = 1
     * 2 = 2
     * ...
     * 9 = 9
     * 10 = 1
     * 11 = 2
     * ...
     * 18 = 9
     * 19 = 1
     * 20 = 2
     * ...
     * 27 = 9
     * 28 = 1
     * 29 = 2
     */

    /** 穷举找规律解法，寻找循环周期后用求模运算直接得到答案。Time - o(1) */
    // 通过穷举，可以看到结果在1到9之间周期出现，除了0是例外情况。
    // 本质上是把十进制的数字简化成为九进制的九个符号之一。
    // 整除的话，需要把结果修正为9
    static int addDigits(int a) {
        if (a == 0) return a;
        return a % 9 == 0 ? 9 : a % 9;
    }

    /** 简化解法，偏移再补偿 */
    // 为了简化专门对于0和余数为0的特殊处理，可以先偏移1位，然后求模以后再补偿回来。
    // 对于0：(0 - 1) % 9 + 1= -1 + 1 = 0
    // 对于余数为零的情况：(18 - 1) % 9 + 1 = 8 + 1 = 9
    // 可以看到偏移的解法有效的隐式包含了对上面两种情况的处理。
    static int addDigits2(int a) {
        return (a - 1) % 9 + 1;
    }

    /** 递归解法, Time - o(lnn), Space - o(lnn) */
    static int addDigits3(int a) {
        if (a < 10) return a;
        int sum = 0;
        while (a > 0) {
            sum += a % 10;
            a /= 10;
        }
        return addDigits3(sum);
    }

    /** 迭代解法 */
    static int addDigits4(int a) {
        while (a > 9) {
            int sum = 0;
            while (a > 0) {
                sum += a % 10;
                a /= 10;
            }
            a = sum;
        }
        return a;
    }
}

