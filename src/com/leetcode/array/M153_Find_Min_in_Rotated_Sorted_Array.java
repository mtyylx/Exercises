package com.leetcode.array;

/**
 * Created by Michael on 2016/8/28.
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * You may assume no duplicate exists in the array.
 *
 * Function Signature:
 * public int findMinRotatedSortedArray(int[] a) {...}
 * */
public class M153_Find_Min_in_Rotated_Sorted_Array {
    public static void main(String[] args) {
        int[] a = {3, 1, 2};
        System.out.println(findMinRotatedSortedArray2(a));
    }

    // 二分查找解法，o(logn)
    // 难点在于找到终止条件：即什么情况下就能确认找到了pivot了
    // 如果中点元素大于右侧指针，则pivot一定在中点元素右边，即i应更新为middle + 1
    // 如果中点元素不大于右侧指针，则中点本身以及中点左侧的所有元素都有可能是pivot，例如{3, 1, 2}
    // 直到left和right重合之时，那个元素一定是pivot
    static int findMinRotatedSortedArray2(int[] a) {
        if (a == null || a.length == 0) return -1;
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            if (a[middle] > a[right]) left = middle + 1;
            else  right = middle;
        }
        return a[left];
    }

    // 常规解法，o(n)
    // 依次扫描，找到比上一个元素小的元素返回。
    static int findMinRotatedSortedArray(int[] a) {
        if (a == null || a.length == 0) return -1;
        int min = a[0];
        for (int x : a) {
            if (x < min) return x;
            else min = x;
        }
        return a[0];
    }
}
