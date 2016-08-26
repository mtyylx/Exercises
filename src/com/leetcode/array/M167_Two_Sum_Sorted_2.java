package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/26.
 * Given an array of integers that is already sorted in ascending order,
 * find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the target,
 * where index1 must be less than index2.
 * Please note that your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution.
 *
 * Example:
 * Input: numbers = {2, 7, 11, 15}, target = 9
 * Output: index1 = 1, index2 = 2
 *
 * Function Signature:
 * public int[] twoSumSorted(int[] a, int target) {...}
 */
public class M167_Two_Sum_Sorted_2 {
    public static void main(String[] args) {
        int[] a = {1, 4, 7, 9, 10, 12};
        int[] result = twoSumSorted(a, 10);
    }


    // 双指针解法，o(n)
    // 该题区别于E1的关键在于两点：
    // 1. 数组已经有序
    // 2. 一定有唯一解
    // 由于是数组中两个元素相加，因此可以如下规律：
    // 索引值低的两个元素一定比索引值高的两个元素之和小。
    static int[] twoSumSorted(int[] a, int target) {
        if (a == null || a.length < 2) return new int[]{0, 0};
        int i = 0;
        int j = a.length - 1;
        while (a[i] + a[j] != target) {
            if (a[i] + a[j] > target) j--;
            else i++;
        }
        return new int[] {i + 1, j + 1};
    }
}
