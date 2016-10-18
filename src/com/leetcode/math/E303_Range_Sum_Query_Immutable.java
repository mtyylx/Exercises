package com.leetcode.math;

/**
 * Created by Michael on 2016/10/18.
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
 *
 * Example: Given nums = [-2, 0, 3, -5, 2, -1]
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 *
 * Note:
 * You may assume that the array does not change.
 * There are many calls to sumRange function.
 * 关键在于题目明确表示这个方法会被高频调用。所以不能每次都临时计算
 */
public class E303_Range_Sum_Query_Immutable {
    public static void main(String[] args) {
        NumArray my = new NumArray(new int[] {1, 6, 4, 7, 0, -4, -9, 5});
        System.out.println(my.querySum(3, 5));
    }
}

/** 累计分布解法，类似于Counting Sort */
// 将数组处理成为累计分布数组，方便以后查询。一开始的时间复杂度为o(n)，之后的复杂度则是o(1)
// 原数组：[1, 6, 4, 7, 0, -4, -9, 5]
// 累积分布：[1, 7, 11, 18, 18, 14, 7, 12]
//             ^       ^       histo[3] - histo[1] = nums[2,3]
class NumArray {
    private int[] histo;
    public NumArray(int[] nums) {
        histo = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        histo = nums;
    }

    public int querySum(int start, int end) {
        if (start <= end && start >= 0 && end < histo.length) {
            return start == 0 ? histo[end] : histo[end] - histo[start - 1];
        }
        return -1;
    }
}
