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
    }

    /** 典型的位运算技巧 */
    // 加法的本质，由两部分运算构成：一个是当前位的运算，一个是上一位带来的进位。
    // 当前位的运算可以用异或实现：0 ^ 0 = 0, 0 ^ 1 = 1, 1 ^ 0 = 1, 1 ^ 1 = 0
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
}
