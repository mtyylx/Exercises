package com.leetcode.array;

/**
 * Created by Michael on 2017/2/19.
 *
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
 * Given two integers x and y, calculate the Hamming distance.
 *
 * Note: 0 ≤ x, y < 231.
 *
 * Example: Input: x = 1, y = 4 Output: 2
 * Explanation: 1   (0 0 0 1)
 *              4   (0 1 0 0)
 *                     ↑   ↑
 * The above arrows point to positions where the corresponding bits are different.
 *
 * Function Signature:
 * public int hammingDistance(int x, int y) {...}
 *
 * <Tags>
 * - Bit Manipulation: XOR
 * - Bit Manipulation: Last Digit Extraction
 *
 */
public class E461_Hamming_Distance {
    public static void main(String[] args) {
        System.out.println(hammingDistance(1, 4));      // 0001, 0100
        System.out.println(hammingDistance(4, 9));      // 0100, 1001
    }

    static int hammingDistance(int x, int y) {
        int xor = x ^ y;                // 首先获得两个数值二进制不同位构成的数值
        int count = 0;
        while (xor > 0) {               // 然后提取这个数值中为1的比特个数
            count += xor & 1;           // 通过mask提取最低位数值
            xor >>= 1;                  // 右移直至该值为0
        }
        return count;
    }
}
