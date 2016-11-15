package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by LYuan on 2016/11/15.
 * Gray Code，就是格雷码。
 * The gray code is a binary numeral system where two successive values differ in only one bit.
 * Given a non-negative integer n representing the total number of bits in the code,
 * print the sequence of gray code. A gray code sequence must begin with 0.
 *
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * Note: For a given n, a gray code sequence is not uniquely defined.
 * For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
 * For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
 *
 * Function Signature:
 * public List<Integer> generateGrayCode(int n) {...}
 */

public class M89_Gray_Code {
    public static void main(String[] args) {
        System.out.println(generateGrayCode2(3).toString());
    }


    /** 比特翻转法，速度快的跟作弊一样。 */
    // 似乎格雷码的标准生成方式就是i异或i的右移一位。
    // 异或可以被视作是位运算的加法：因为有0^0=0, 0^1=1, 1^0=1, 1^1=0。即a ^ b 约等于 a + b
    static List<Integer> generateGrayCode2(int n) {
        List<Integer> result = new ArrayList<>();
        int length = 1 << n;                        // 因为n代表bit位数，因此n是多少，序列长度就应该是2^n个，也就是1左移n次。
        for (int i = 0; i < length; i++)
            result.add(i ^ (i>>1));
        return result;
    }

    /** DP解法，找到状态转移规律即可。Time - o(n), Space - o(1) */
    // DP解法，求n位格雷码全部序列需要从n = 2开始，每一轮是上一轮状态数的一倍，增加的部分等于新加位的比特值逆序叠加。
    // n = 0: 0
    // n = 1: 0, 1
    // n = 2: 00, 01, 11, 10 = 0, 1, 1 + 2, 0 + 2 = 0, 1, 3, 2
    // n = 3: 000, 001, 011, 010, 110, 111, 101, 100 = 0, 1, 3, 2, 2 + 4, 3 + 4, 1 + 4, 0 + 4 = 0, 1, 3, 2, 6, 7, 5, 4
    // 由上面的推导可以看到，从n=2到n=3，增加的后四个状态完全可以通过前面的四个已知状态得到，如下面所示。
    // 000↓ ↑100
    // 001↓ ↑101
    // 011↓ ↑111
    // 010↓_↑110
    static List<Integer> generateGrayCode(int n) {
        List<Integer> result = new ArrayList<>(Arrays.asList(0));
        int base = 1;
        for (int i = 1; i <= n; i++, base <<= 1)
            for (int j = result.size() - 1; j >= 0; j--)
                result.add(result.get(j) + base);
        return result;
    }
}
