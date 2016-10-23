package com.leetcode.math;

/**
 * Created by Michael on 2016/10/23.
 * Calculate the sum of two integers a and b,
 * but you are <not allowed> to use the operator + and -.
 *
 * Example:
 * Given a = 1 and b = 2, return 3.
 *
 * Function Signature:
 * public int bitSum(int a, int b) {...}
 */
public class E371_Sum_of_Two_Integers {
    public static void main(String[] args) {
        System.out.println(bitSum(1, 10));
        System.out.println(bitSubtract(13, 11));
    }

    /** 用位运算实现加法 */
    // 加法的本质，由两部分运算构成：一个是当前位的运算，一个是上一位带来的进位。
    // 当前位的运算可以用异或实现：0 ^ 0 = 0 + 0 = 0, 0 ^ 1 = 0 + 1 = 1, 1 ^ 0 = 1 + 0 = 1, 1 ^ 1 = 1 + 1 = 0
    // 进位可以用与运算加左移实现：1 & 1 = 1
    // a = 0 1 0 1 1     xor = 0 0 1 1 0      xor = 1 0 1 0 0      xor = 1 0 0 0 0      xor = 1 1 0 0 0
    // b = 0 1 1 0 1   carry = 1 0 0 1 0    carry = 0 0 1 0 0    carry = 0 1 0 0 0    carry = 0 0 0 0 0
    static int bitSum(int a, int b) {
        int carry, xor;
        while (b != 0) {
            xor = a ^ b;
            carry = (a & b) << 1;
            a = xor;
            b = carry;
        }
        return a;
    }

    // 递归写法
    // 把a当作base，把b当作carry，递归终止条件是carry为0。未终止前，不断计算新的base和carry。
    static int bitSum2(int a, int b) {
        return b == 0 ? a : bitSum2(a ^ b, (a & b) << 1);
    }

    /** 用位运算实现减法 */
    // 当前位的运算<依然>可以用异或实现：0 ^ 0 = 0 - 0 = 0, 0 ^ 1 = 0 - 1 = 1, 1 ^ 0 = 1 - 0 = 1, 1 ^ 1 = 1 - 1 = 0
    // 借位则用(~a & b) << 1：只希望0 - 1得到1，其他情况都得到0。
    // a = 0 1 1 0 1      xor = 0 0 1 1 0       xor = 0 0 0 1 0
    // b = 0 1 0 1 1   borrow = 0 0 1 0 0    borrow = 0 0 0 0 0
    static int bitSubtract(int a, int b) {
        int carry, xor;
        while (b != 0) {
            xor = a ^ b;
            carry = (~a & b) << 1;
            a = xor;
            b = carry;
        }
        return a;
    }

    // 递归写法
    // 把a当作base，把b当作carry，递归终止条件是carry为0。未终止前，不断计算新的base和carry。
    static int bitSubtract2(int a, int b) {
        return b == 0 ? a : bitSubtract2(a ^ b, (~a & b) << 1);
    }
}
