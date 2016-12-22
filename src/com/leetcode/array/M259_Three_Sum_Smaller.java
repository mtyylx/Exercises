package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/12/22.
 * Given an array of n integers nums and a target,
 * find the number of index triplets i, j, k with 0 <= i < j < k < n that
 * satisfy the condition nums[i] + nums[j] + nums[k] < target.
 *
 * For example, given nums = [-2, 0, 1, 3], and target = 2.
 * Return 2. Because there are two triplets which sums are less than 2:
 * [-2, 0, 1]
 * [-2, 0, 3]
 * Could you solve it in O(n2) runtime?
 *
 * Function Signature:
 * public int threeSumSmaller(int[] a, int target) {...}
 *
 * <K-Sum系列问题>
 *    E1 2-Sum: 给定一个整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *   M15 3-Sum: 给定一个整形数组a和目标值0，求解所有相加等于0的三元组，不可重复。（解个数随机）
 *   M18 4-Sum: 给定一个整型数组a和目标值k，求解所有相加等于0的四元组，不可重复。（解个数随机）
 *  M167 2-Sum Sorted:
 *  E170 2-Sum Data Structure:
 *   M16 3-Sum Closest: 给定一个整型数组a和目标值k，求解距离k最近的一个三元组之和。（有且仅有一个解）
 *  M259 3-Sum Smaller: 给定一个整型数组a和目标值k，求解和小于target的三元组个数，可以重复。
 *
 * <Tags>
 * - Sort + Two Pointers: [left → → → ... ← ← ← right]
 *
 */
public class M259_Three_Sum_Smaller {
    public static void main(String[] args) {
        System.out.println(threeSumSmaller(new int[] {-2, 0, 1, 3}, 2));
    }

    /** 解法1：Sort + Two Pointers. Time - o(n^2). */
    // 思路与 3Sum 基本一致，都是利用已排序数组的性质，降低时间复杂度。
    // 区别在于这里的目标是记录三元组的个数，并不关心解都是什么，而且无需去重。
    static int threeSumSmaller(int[] a, int target) {
        int count = 0;
        if (a == null || a.length < 0) return count;
        Arrays.sort(a);
        for (int i = 0; i < a.length - 2; i++) {
            // if (i > 0 && a[i] == a[i - 1]) continue; 注意这里不应该去重，因为记录的是三元组个数，有一个算一个，重复的也算。
            int left = i + 1;
            int right = a.length - 1;
            while (left < right) {
                int sum = a[i] + a[left] + a[right];
                if (target <= sum) right--;     // sum 位于 target 右侧，需要向左寻找
                else {
                    count += right - left;      // sum 位于 target 左侧，因此所有大于left的right都是解，无需再一一遍历，直接记录。
                    left++;                     // 向右寻找（有可能sum依然在target左侧，也有可能已经跑到了target的右侧）
                }
            }
        }
        return count;
    }
}
