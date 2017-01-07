package com.leetcode.array;

/**
 * Created by Michael on 2016/8/28.
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * You may assume no duplicate exists in the array.
 *
 * Function Signature:
 * public int findMin(int[] a) {...}
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
public class M153_Find_Min_Rotated_Sorted_Array {
    public static void main(String[] args) {
        System.out.println(findMin2(new int[] {1, 2, 3}));
        System.out.println(findMin(new int[] {3, 1, 2}));
    }

    /** 解法2：Binary Search的变体。Time - o(logn) */
    // 与标准的二分查找不同，这里完全根据a[mid]和a[j]两者的大小关系移动左右指针，而不是target与a[mid]之间的大小关系。
    // Case #1：如果a[mid] > a[j]，就说明最小值一定在mid之右，且mid本身不可能是min
    // 例如[2 3 1]中，a[mid] = 3 > 1，因此最小值只可能在3的右侧
    // Case #2：如果a[mid] <= a[j]，就说明最小值在mid之左，且mid本身可能就是min
    // 例如[3 1 2]中，a[mid] = 1 < 2，因此最小值在包括1在内的左侧
    // 需要注意的是，这里使用的循环终止条件是i < j，而不是i <= j（后者无法很好的处理边界情况）
    static int findMin2(int[] a) {
        if (a == null || a.length == 0) return -1;
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            if (a[mid] > a[j]) i = mid + 1;     // 如果中点mid比右指针j还大，说明最小值范围在[mid + 1, j]，mid本身不可能是最小值
            else               j = mid;         // 如果中点mid小于等于右指针j，说明最小值范围在[i, mid]，mid本身有可能是最小值
        }
        return a[i];
    }


    /** 解法1：顺序扫描，最简单。Time - o(n) */
    // 第一个不满足的元素就是最小值。
    static int findMin(int[] a) {
        if (a == null || a.length == 0) return -1;
        for (int i = 1; i < a.length; i++)
            if (a[i] < a[i - 1]) return a[i];
        return a[0];
    }
}
