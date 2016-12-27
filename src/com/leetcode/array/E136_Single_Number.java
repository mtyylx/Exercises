package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/29.
 * Given an array of integers, every element <appears twice> except for one. Find that single one.
 *
 * Note: Your algorithm should have a linear runtime complexity.
 * Could you implement it without using extra memory?
 *
 * Function Signature:
 * public int singleNumber(int[] a) {...}
 *
 * <系列问题>
 * E136 Single Number1: 给定一个数组，元素值成双出现，只有一个值出现了一次，找到这个元素。
 * M137 Single Number2: 给定一个数组，元素值成叁出现，只有一个值出现了一次，找到这个元素。
 * M260 Single Number3: 给定一个数组，元素值成双出现，只有两个值出现了一次，找到这两个元素。
 *
 * <Tags>
 * - Bit Manipulation: XOR to find duplicate
 */
public class E136_Single_Number {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[] {1, 2, 3, 1, 2, 3, 4}));
    }

    /** 非常典型的比特翻转题：使用异或运算的性质。 Time - o(n), space - o(1) */
    // 只要出现数组元素重复的特性，并且要找的是一个与其他都不同的元素，就可以考虑XOR
    // (当然，其实比特翻转解法的使用局限性很大）
    static int singleNumber(int[] a) {
        int result = 0;                     // 任何值与0异或都得自己。也就是说，0在异或运算中就是“空气一样的存在”。
        for (int x : a) result ^= x;
        return result;
    }
}
