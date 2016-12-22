package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/12/22.
 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
 * Return the sum of the three integers. You may assume that each input would have exactly one solution.
 *
 * For example, given array S = {-1 2 1 -4}, and target = 1.
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 *
 * Function Signature:
 * public int threeSumClosest(int[] a, int target) {...}
 *
 * <K-Sum系列问题>
 *    E1 2-Sum: 给定一个整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *   M15 3-Sum: 给定一个整形数组a和目标值0，求解所有相加等于0的三元组，不可重复。（解个数随机）
 *   M18 4-Sum: 给定一个整型数组a和目标值k，求解所有相加等于0的四元组，不可重复。（解个数随机）
 *  M167 2-Sum Sorted:
 *  E170 2-Sum Data Structure:
 *   M16 3-Sum Closest: 给定一个整型数组a和目标值k，求解距离k最近的一个三元组之和。（有且仅有一个解）
 *
 * <Tags>
 * - Sort + Two Pointers: [left → → → ... ← ← ← right]
 *
 */
public class M16_Three_Sum_Closest {
    public static void main(String[] args) {
        System.out.println(threeSumClosest(new int[] {-1, 2, 1, -4}, 1));
    }

    /** 解法1：Sort + Two Pointers */
    // 因为目标是求距离target最近的一个sum，那么就需要从两个方向approach这个target，即绝对值最小。
    // 区别与 3Sum 的地方在于，3Sum 需要的是让sum = target的成员集合，而这里求的是让Math.min(|sum - target|)的sum值本身。
    // 记住一点：两个值，不管他们是正是负，两者相减的绝对值一定是他们之间的距离。我们现在的使命就是让这个距离最小化。
    // 未变的性质：left++可以使sum增加，right--可以使sum减小。
    static int threeSumClosest(int[] a, int target) {
        int min = Integer.MAX_VALUE;
        int result = 0;
        Arrays.sort(a);
        for (int i = 0; i < a.length - 2; i++) {
            if (i > 0 && a[i] == a[i - 1]) continue;
            int left = i + 1;
            int right = a.length - 1;
            while (left < right) {
                int sum = a[left] + a[right] + a[i];
                int distance = Math.abs(target - sum);
                if (distance < min) {
                    result = sum;
                    min = distance;
                }
                if      (target > sum) left++;      // sum 位于 target 的左侧，移动left使得sum向右移动。
                else if (target < sum) right--;     // sum 位于 target 的右侧，移动right使得sum向左移动。
                else return sum;                    // 如果发现距离为0，直接返回sum，因为不会有比这个距离再小的可能了。
            }
        }
        return result;      // 记得返回sum而不是distance
    }
}
