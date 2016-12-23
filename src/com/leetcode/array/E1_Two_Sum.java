package com.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael on 2016/8/22.
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * You may assume that each input would have exactly one solution.
 *
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 *
 * Function Signature:
 * public int[] twoSum(int[] a, int target) {...}
 *
 * <K-Sum系列问题>
 *    E1 2-Sum: 给定一个整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *   M15 3-Sum: 给定一个整形数组a和目标值0，求解所有相加等于0的三元组，不可重复。（解个数随机）
 *   M18 4-Sum: 给定一个整型数组a和目标值k，求解所有相加等于0的四元组，不可重复。（解个数随机）
 *  M167 2-Sum Sorted: 给定一个已排序的整型数组a和目标值k，求解相加等于k的两个元素的索引。（有且仅有一个解）
 *  E170 2-Sum Data Structure: 给定一系列整型数值和目标值，提供添加元素和寻找内部数据库中是否存在和等于目标值的两个元素的功能。
 *   M16 3-Sum Closest: 给定一个整型数组a和目标值k，求解距离k最近的一个三元组之和。（有且仅有一个解）
 *  M259 3-Sum Smaller: 给定一个整型数组a和目标值k，求解和小于target的三元组个数，可以重复。
 *
 * <Tags>
 * - HashMap
 * - Sort + Two Pointers: [left → → → ... ← ← ← right]
 *
 */
public class E1_Two_Sum {
    public static void main(String[] args) {
        int[] a = new int[] {0, 1, 2, 7, 10};
        System.out.println(Arrays.toString(twoSum(a, 9)));
        System.out.println(Arrays.toString(twoSum2(a, 9)));
        List<List<Integer>> results = twoSum3(new int[] {1, 2, 2, 3, 4, 5, 6, 6, 7}, 8);
    }

    /** 哈希表解法，Time - o(n) */
    // 关键在于给键和值赋予什么含义：键存储补值（target - value），值存储索引。
    static int[] twoSum(int[] a, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (!map.containsKey(a[i])) map.put(target - a[i], i);
            else return new int[] {map.get(a[i]), i};
        }
        return new int[] {-1};
    }

    /** 排序 + 双指针解法。排序时间复杂度为 o(nlogn)，但是寻找两个值本身的操作依然是o(n)。 */
    // 利用了已排序数组的性质。
    static int[] twoSum2(int[] a, int target) {
        Arrays.sort(a);
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            int sum = a[left] + a[right];
            if      (sum < target) left++;
            else if (sum > target) right--;
            else return new int[] {left, right};
        }
        return new int[] {};
    }

    /** 当前问题的发散，使得算法可以处理多种组合解的情况。 */
    // 使用双指针处理解不为一的情况，同时避免重复解的记录。
    // 修改接口，并且直接记录元素值本身。
    static List<List<Integer>> twoSum3(int[] a, int target) {
        Arrays.sort(a);
        List<List<Integer>> result = new ArrayList<>();
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            int sum = a[left] + a[right];
            if      (sum < target) left++;
            else if (sum > target) right--;
            else {
                List<Integer> set = new ArrayList<>(Arrays.asList(a[left], a[right]));
                result.add(set);
                left++;
                right--;
                while (left < right && a[left] == a[left - 1]) left++;      // Skip duplicate
                while (left < right && a[right] == a[right + 1]) right--;   // Skip duplicate
            }
        }
        return result;
    }

}
