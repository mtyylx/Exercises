package com.leetcode.array;

import java.util.HashMap;

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
 * 1. Sorted + Two Pointers: [left → → → ... ← ← ← right]
 * 2. HashMap
 * 3. Binary Search
 *
 */
public class M167_Two_Sum_Sorted_2 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,4,9,56,90};
        int[] result = twoSumSorted(a, 8);
        int[] result2 = twoSumSorted2(a, 8);
        int[] result3 = twoSumSorted3(a, 8);
        int[] result4 = twoSumSorted4(a, 8);
    }

    // 本题与2Sum几乎完全一样，一定有唯一解。区别在于
    // 1. 数组已排序，无需再手动排序
    // 2. 要求返回的索引是以1开始的而不是以0开始。无关痛痒。

    /** 解法3：Binary Search。Time - o(nlogn)，迭代写法 */
    static int[] twoSumSorted4(int[] a, int target) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] * 2 > target) continue;
            int left = i + 1;
            int right = a.length - 1;
            int t = target - a[i];                      // new target
            while (left <= right) {                     // 标准二分查找
                int mid = left + (right - left) / 2;
                if      (a[mid] < t) left = mid + 1;
                else if (a[mid] > t) right = mid - 1;
                else return new int[] {i + 1, mid + 1};
            }
        }
        return new int[] {};
    }

    /** 解法3：Binary Search。Time - o(nlogn)，递归写法 */
    // 其实在这里使用二分查找的算法复杂度略高于双指针解法。但是多一个解法终归是个好事，毕竟对于已排序数组，使用二分查找是固定套路。
    // 基本思路是按住一个值，然后在这个值的右侧范围内进行二分查找。找的值是target - 按住的这个值。
    static int[] twoSumSorted3(int[] a, int target) {
        for (int i = 0; i < a.length; i++) {
            int result = binarySearchRecursive(a, target - a[i], i + 1, a.length - 1);      // 注意这里start是i + 1.
            if (result != -1) return new int[] {Math.min(i + 1, result + 1), Math.max(i + 1, result + 1)};
        }
        return new int[] {-1, -1};
    }

    static int binarySearchRecursive(int[] a, int target, int left, int right) {
        if (left <= right) {
            int middle = (left + right) / 2;
            if      (target < a[middle]) return binarySearchRecursive(a, target, left, middle - 1);
            else if (target > a[middle]) return binarySearchRecursive(a, target, middle + 1, right);
            else return middle;
        }
        else return -1;
    }

    /** 解法2：Two Pointers。推荐解法。Time - o(n) */
    // 利用已排序数组中任意两个元素之和也有序的性质。
    static int[] twoSumSorted2(int[] a, int target) {
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            if      (a[left] + a[right] > target) right--;
            else if (a[left] + a[right] < target) left++;
            else return new int[] {left + 1, right + 1};    // 1-based, not 0-based.
        }
        return new int[] {};
    }

    /** 解法1：哈希表解法，直接套用 2Sum 的解法没有问题。Time - o(n) */
    // 关键在于给键和值赋予什么含义：键存储补值（target - value），值存储索引。
    static int[] twoSumSorted(int[] a, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (!map.containsKey(a[i])) map.put(target - a[i], i);
            else return new int[] {map.get(a[i]) + 1, i + 1};
        }
        return new int[] {-1};
    }
}
