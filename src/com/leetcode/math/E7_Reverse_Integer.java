package com.leetcode.math;

import java.util.Arrays;

/**
 * Created by Michael on 2016/10/16.
 * Reverse digits of an integer.
 *
 * Example1: x = 123, return 321
 * Example2: x = -123, return -321
 *
 * Have you thought about this?
 * Here are some good questions to ask before coding. Bonus points for you if you have already thought through this!
 * If the integer's last digit is 0, what should the output be? ie, cases such as 10, 100.
 * Did you notice that the reversed integer might overflow? Assume the input is a 32-bit integer,
 * then the reverse of 1000000003 overflows. How should you handle such cases?
 * For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
 *
 * Function Signature:
 * public int reverseInt(int a) {...}
 */
public class E7_Reverse_Integer {
    public static void main(String[] args) {
        System.out.println(-9/10);
        System.out.println(reverseInt(-2147483648));
        System.out.println(reverseInt3(-123456789));
    }

    /** math这个分类下的题目大多是与任何数据结构都没什么太大关系（连数组都用不到），仅仅围绕一个Number出的题目。
     *  但是这并不意味着题目的最佳解法会很简单，
     *  实际上，这个分类下的题目会特别考察你对于<各种边界情况>的提前预知和处理方式。
     *  你需要比平时更小心，考虑的特殊情况更全面才可以。
     */

    /** 边界情况考虑：
     *  1. 负数，需要确保符号信息不丢失。
     *  2. 尾数为0，反转后需要正确输出。数值解法不需要担心，字符串解法需要处理。
     *  3. 反转后整型溢出如何提前处理。思路一：提前预判处理法，思路二：让rev使用long。
     */
    // 小心使用Math.abs()这个API，我发现它对于Integer.MIN_VALUE不会正常工作，依然返回Integer.MIN_VALUE本身。
    // 可以看到乘十叠加和除十提取对于负数同样生效，与正数完全没有区别。
    // 例如org = -123，
    // -123 % 10 = -3, -123 / 10 = -12, rev = -3
    // -12 % 10 = -2, -12 / 10 = -1, rev = -30 + -2 = -32
    // -1 % 10 = -1, -1 / 10 = 0, rev = -320 + -1 = -321
    //
    /** 提前预判整型溢出 */
    // 考虑到唯一会出现溢出的语句就是这句：rev * 10 + digit
    // 但是又不能直接判断rev * 10 + digit > Integer.MAX_VALUE，这时候前半部已经溢出了，没法正确判断了。
    // 因此需要把不等式稍微移动一下，转而判断rev > (Integer.MAX_VALUE - digit) / 10，
    // 这下就会在溢出之前就判断了，对于负数换为MIN_VALUE即可。
    static int reverseInt(int org) {
        int rev = 0;
        while (org != 0) {
            int digit = org % 10;
            if (rev > 0 && (Integer.MAX_VALUE - digit) / 10 < rev ) return 0;
            if (rev < 0 && (Integer.MIN_VALUE - digit) / 10 > rev ) return 0;
            else rev = rev * 10 + digit;
            org /= 10;
        }
        return rev;
    }

    /** 使用long避免溢出 */
    static int reverseInt2(int org) {
        long rev = 0;
        while (org != 0) {
            rev = rev * 10 + org % 10;  // 自动向上转型：int -> long
            if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) return 0;
            org /= 10;
        }
        return (int) rev;
    }

    // 使用字符串
    static int reverseInt3(int org) {
        return org < 0 ?
                -Integer.parseInt(new StringBuilder(String.valueOf(-org)).reverse().toString()) :
                Integer.parseInt(new StringBuilder(String.valueOf(org)).reverse().toString());
    }

}
