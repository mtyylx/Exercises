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
 * <系列问题>
 * - E191 Hamming Weight  : 给定一个无符号数，返回该数值二进制形式1bit的个数。
 * - E461 Hamming Distance: 给定两个数值，返回两个数值二进制形式不同的比特个数。（本质上就是求两个数值异或值的Hamming Weight）
 *
 * <Tags>
 * - Bit Manipulation: XOR with ^ operator.
 * - Bit Manipulation: Bit Mask with & operator.
 * - Bit Manipulation: x & (x - 1) to cleanup the lowest 1 bit.
 *
 */
public class E461_Hamming_Distance {
    public static void main(String[] args) {
        System.out.println(hammingDistance1(1, 4));      // 0001, 0100
        System.out.println(hammingDistance2(4, 9));      // 0100, 1001
        System.out.println(hammingDistance3(4, 9));      // 0100, 1001
    }

    /** 解法1：异或 + 与运算Mask + 平移。速度最快。 */
    static int hammingDistance1(int x, int y) {
        int xor = x ^ y;                // 首先获得两个数值二进制不同位构成的数值
        int count = 0;
        while (xor > 0) {               // 然后提取这个数值中为1的比特个数
            count += xor & 1;           // 通过mask提取最低位数值
            xor >>= 1;                  // 右移直至该值为0
        }
        return count;
    }

    /** 解法2：异或 + 最低位1比特清零法。 */
    static int hammingDistance2(int x, int y) {
        int xor = x ^ y;                // 首先获得两个数值二进制不同位构成的数值
        int count = 0;
        while (xor > 0) {               // 然后提取这个数值中为1的比特个数
            xor &= xor - 1;             // 每对相邻值进行一次与运算，xor中最低位的1比特就会被清理为0.
            count++;
        }
        return count;
    }

    /** 解法3：求余除2法提取比特数。虽然逻辑一样，但是速度明显慢于纯比特运算。 */
    static int hammingDistance3(int x, int y) {
        int xor = x ^ y;                // 首先获得两个数值二进制不同位构成的数值
        int count = 0;
        while (xor > 0) {               // 然后提取这个数值中为1的比特个数
            count += xor % 2;           // 求余提取当前最低位比特
            xor /= 2;                   // 将数值整个向右移动一位（等效于xor >>= 1）
        }
        return count;
    }
}
