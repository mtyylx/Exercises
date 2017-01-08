package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/8/24.
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 *
 * For example,
 * Given nums = [0, 1, 3] return 2.
 * Your algorithm should run in linear runtime complexity.
 * Could you implement it using only constant extra space complexity?
 *
 * Function Signature:
 * public int missingNumber(int[] a) {...}
 *
 * <Tags>
 * - Bit Manipulation: XOR of Value & Index
 * - Sum
 *
 */
public class M268_Missing_Number {
    public static void main(String[] args) {
        int[] a = new int[Integer.MAX_VALUE / 10];
        for (int i = 0; i < a.length; i++) a[i] = i;
        System.out.println(missingNumber(a));
        System.out.println(missingNumber2(a));
        System.out.println(missingNumber3(a));
    }

    /** 解法3：Bit Manipulation XOR. Time - o(n), Space - o(1) */
    // 比特反转法的最无敌的优势在于可以避免溢出，运算速度是所有操作中最快的
    // 通过解法2的启发，我们已经注意到这里数组的索引值范围与数组元素的取值范围完全重合，
    // 这两者之间的区别在于：数组索引一定是有序的，而数组元素内容因为缺少一个元素因此可能是断开的，或在首尾缺值。
    // 因此按理说对于一个完美的数组，其元素值和索引值的XOR应该为0。而对于缺了一个值得数组来讲，异或的结果就是缺的那个值。
    // 理想情况下取值范围应该是0 to a.length, 即a.length+1个元素，但是由于实际情况中数组只有a.length个元素，因此a.length索引值是单独存在的，可以作为初始值。
    static int missingNumber3(int[] a){
        int result = a.length;
        for (int i = 0; i < a.length; i++)
            result = result ^ i ^ a[i];
        return result;
    }

    /** 解法2：先排序，再利用已排序数组的性质。Time - o(nlogN), Space - o(1) */
    // 由于题目明确取值范围是0到n，恰好跟数组索引的取值范围完全一样，因此只要发现了索引与数值不匹配的元素，就一定是这个索引值丢失了。
    // 如果没有索引值丢失，就说明只缺一个尾部元素。
    static int missingNumber2(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++)
            if (i != a[i]) return i;
        return a.length;
    }

    /** 解法1：求和法。Time - o(n), Space - o(1) */
    // 唯一的缺点就是会溢出，因此必须使用long存储元素之和。
    static int missingNumber(int[] a) {
        int len = a.length;
        long sum = len * (len + 1) / 2;      // 首项0，末项len，项数len + 1
        for (int x : a) sum -= x;            // 减掉即可，无需先求和再相互减
        return (int) sum;
    }
}
