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
 * <Tags>
 * - Binary Search
 * - Two Pointers: 左右指针首尾包围 [left → → → ... ← ← ← right]
 *
 */
public class M153_Find_Min_in_Rotated_Sorted_Array {
    public static void main(String[] args) {
        System.out.println(findMin2(new int[] {1, 2, 3}));
        System.out.println(findMin(new int[] {3, 1, 2}));
    }

    /** 解法2：Binary Search的变体。Time - o(logn) */
    // 与标准的二分查找不同，这里只需要判断a[mid]与a[j]的大小关系就可以移动左右指针了。
    // 如果a[mid] > a[j]，就说明最小值一定在mid之右，且mid本身不可能是min
    // 例如[2 3 1]中，a[mid] = 3 > 1，因此最小值只可能在3的右侧
    // 如果a[mid] <= a[j]，就说明最小值在mid之左，且mid本身可能就是min
    // 例如[3 1 2]中，a[mid] = 1 < 2，因此最小值在包括1在内的左侧
    // 试图用标准的i<=j方式解决，但是似乎不如这个来的逻辑清晰
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
