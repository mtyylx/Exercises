package com.leetcode.array;

/**
 * Created by Michael on 2017/1/7.
 *
 * Follow up for "Search in Rotated Sorted Array": What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Write a function to determine if a given target is in the array.
 * The array may contain duplicates.
 *
 * Example:
 * - [3, 3, 3, 1, 3], target = 3, idx 任意一个
 * - [3, 1, 3, 3, 3], target = 3, idx 任意一个
 *
 * Function Signature:
 * public boolean search(int[] a, int target) {...}
 *
 * <系列问题>
 * - M153 Find Min in Rotated Sorted Array 1: 给定一个被折断的有序数组，找到折断的起点（即最小值），该数组<无重复元素>。
 * - H154 Find Min in Rotated Sorted Array 2: 给定一个被折断的有序数组，找到折断的起点（即最小值），该数组<有重复元素>。
 * - H33  Search in Rotated Sorted Array 1:   给定一个被折断的有序数组和一个目标值，如果目标值在数组中就返回所在索引，如果不在就返回-1，该数组<无重复元素>。
 * - M81  Search in Rotated Sorted Array 2:   给定一个被折断的有序数组和一个目标值，判断目标值是否存在于数组中，该数组<有重复元素>。
 *
 * <Tags>
 * - Binary Search
 * - Two Pointers: 左右指针首尾包围 [left → → → ... ← ← ← right]
 * - Rotated Array: 折断数组由两个已排序数组构成，最小值通过比较中点与右指针之间的关系得到。
 *
 */
public class M81_Search_Rotated_Array_2 {
    public static void main(String[] args) {
        System.out.println(search(new int[] {3, 3, 3, 1, 3}, 1));
        System.out.println(search(new int[] {3, 1, 3, 3, 3}, 1));
        System.out.println(search2(new int[] {3, 3, 3, 1, 3}, 1));
    }

    /** 解法2：与H154相同，遇到a[mid]与右指针重复时，退化为线性扫描。Time - o(logN), Time Worst - o(n). */
    static boolean search(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            if (a[mid] == target) return true;
            if (a[mid] < a[j]) {
                if (target <= a[j] && target > a[mid]) i = mid + 1;
                else j = mid - 1;
            }
            else if (a[mid] > a[j]) {
                if (target >= a[i] && target < a[mid]) j = mid - 1;
                else i = mid + 1;
            }
            else j--;       // 放弃二分法，退化为线性扫描
        }
        return a[i] == target;
    }

    /** 解法1：Brain Friendly Solution. Time - o(n). */
    static boolean search2(int[] a, int target) {
        for (int x : a)
            if (x == target) return true;
        return false;
    }
}
