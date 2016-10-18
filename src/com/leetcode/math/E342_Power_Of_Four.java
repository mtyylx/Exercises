package com.leetcode.math;

/**
 * Created by Michael on 2016/10/18.
 * Given an integer (signed 32 bits), write a function to check whether it is a power of 4.
 *
 * Example: Given num = 16, return true. Given num = 5, return false.
 * Follow up: Could you solve it without loops/recursion?
 *
 * Function Signature:
 * public boolean isPowerOfFour(int x) {...}
 */
public class E342_Power_Of_Four {
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            System.out.println((int)(Math.pow(2, i)) & 0x5555);
        }
    }

    /** 短除解法 */
    // 只需要不断检查是否可以被4整除，退出时为1才返回true即可。对于求任何值的幂都适用。
    static boolean isPowerOfFour(int x) {
        if (x < 1) return false;
        while (x % 4 == 0) x /= 4;
        return x == 1;
    }

    /** 进制转换 */
    // 从二进制的性质可以推导出来，只要进制数的幂用这个进制表示，那么一定是一个1搭配若干个0.
    // 2进制：1 = 1，2 = 10，4 = 100，8 = 1000
    // 4进制：1 = 1，4 = 10，16 = 100， 64 = 1000
    // 所以可以用到Integer类库提供的进制转换API，直接将任何数值转换成为你想要的进制的字符串表达。
    // 字符串的matches()方法可以判断字符串是否可以被正则表达式匹配上。
    // 因为我们只想匹配以1打头（必须有1），且后面只能跟0的字符串，可以跟0个，也可以跟多个，但是只能是字符0，不能是任何别的字符
    // 因此正则表达式是"10*"，1表示只匹配以字符1打头的，0*表示0-n个字符0，连起来意思就是：“我只匹配以1打头后跟任意个0的字符串”。很有意思。
    // 表面上似乎没用循环或递归，但是实际上这个API背后的本质还是短除4。
    static boolean isPowerOfFour2(int x) {
        return Integer.toString(x, 4).matches("10*");
    }

    /** 求对数是整数 */
    // 和前面的题一样，用换底公式求以4为底的对数是否是整数。
    static boolean isPowerOfFour3(int x) {
        return Math.log10(x) / Math.log10(4) % 1 == 0;
    }

    /** Bit Mask法 */
    // 从Power of Two题目可以得知，2的幂的二进制表示一定是1个1加无数个0。
    // 而Power of Four，4的幂则是在此基础上，对这单独的1的所在位置进行了限定，这个1只能出现在奇数位（从低位到高位数，最低位为第一位）
    // 1 = 1, 4 = 100, 16 = 10000
    // 因此为了匹配这些位置的1，只需要构造bit mask = 0101...01010101 (32位)，与运算不为0就说明这个数的1的位置确实处于奇数位。
    static boolean isPowerOfFour4(int x) {
        return x > 0 && (x & (x - 1)) == 0 && (x & 0x55555555) != 0;
    }
}
