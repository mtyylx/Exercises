package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/12/28.
 *
 * Given an array of numbers nums, in which exactly two elements appear only once
 * and all the other elements appear exactly twice.
 * Find the two elements that appear only once.
 *
 * For example: Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].
 *
 * Note:
 * The order of the result is not important. So in the above example, [5, 3] is also correct.
 * Your algorithm should run in linear runtime complexity.
 * Could you implement it using only constant space complexity?
 *
 * Function Signature:
 * public int[] singleNumber(int[]) {...}
 *
 * <系列问题>
 * E136 Single Number1: 给定一个数组，元素值成双出现，只有一个值出现了一次，找到这个元素。
 * M137 Single Number2: 给定一个数组，元素值成仨出现，只有一个值出现了一次，找到这个元素。
 * M260 Single Number3: 给定一个数组，元素值成双出现，只有两个值出现了一次，找到这两个元素。
 *
 * <Tags>
 * - Bit Manipulation: XOR, "x & (-x)"
 *
 */
public class M260_Single_Number_3 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(singleNumber(new int[] {1, 4, 7, 2, 5, 1, 4, 7})));
    }

    /** 比特翻转法，关键在于如何分离两个被异或的值。特别牛逼的一个题。 */
    // 假设这两个值分别叫x和y，由于可以肯定这两个值是不同的，因此他们的二进制码一定至少有一位不同，
    // 简单的说就在这一位上，x为0，y为1（如果全相同那么异或就等于0了）
    // 不同的这一位一定会显示在x^y上，体现为1。例如 101 ^ 001 = 100. 左侧第一位的1就来自于101.
    // 接下来我们要做的就是从xor结果中分离出最右侧的一个1的作为分辨x和y的mask。
    // 这里就用到了一个技巧：x & (-x)
    // x & (-x) 的含义是提取x最右侧的1bit的值
    // 1 1 0 0 0 right-most 1-bit 0 1 0 0 0
    //   ↑                          ↑
    // 0 1 1 1 0 right-most 1-bit 0 0 0 1 0
    //       ↑                          ↑
    // 对于x与y之外的其他值，由于他们是成对出现的，因此相同的值总会落入同一个袋子并被异或运算清空。最后每个袋子中只剩下来两个落单的值。
    static int[] singleNumber(int[] a) {
        int xor = 0;
        for (int x : a) xor ^= x;
        xor = xor & (-xor);             // x & (-x) 提取 x 的最低位1作为mask使用
        int[] res = new int[2];         // 两个袋子，一个袋子装mask位为0的，一个袋子装mask位为1的。
        for (int x : a) {
            if ((x & xor) == 0) res[0] ^= x;
            else                res[1] ^= x;
        }
        return res;
    }
}
