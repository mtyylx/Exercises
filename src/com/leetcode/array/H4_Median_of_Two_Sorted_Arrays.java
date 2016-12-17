package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/12/16.
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The median is 2.0
 *
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The median is (2 + 3)/2 = 2.5
 *
 * Function Signature:
 * public double findMedianSortedArrays(int[] nums1, int[] nums2) {...}
 */
public class H4_Median_of_Two_Sorted_Arrays {
    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[] {2, 3, 5, 7}, new int[] {1, 4, 6}));
    }

    // 解法1：Naive最简单解法，按顺序合并两个数组，再找中间元素。Time - o(n + m), Space - o(n + m)
    public static double findMedianSortedArrays(int[] a, int[] b) {
        List<Integer> comb = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < a.length || j < b.length) {                  // 管用伎俩：同时扫描两个数组，直至两个数组都扫描完毕。
            int x = i < a.length ? a[i] : Integer.MAX_VALUE;
            int y = j < b.length ? b[j] : Integer.MAX_VALUE;
            if (x < y)  comb.add(a[i++]);
            else        comb.add(b[j++]);
        }
        int size = comb.size();
        if (size == 0) return 0;                                // empty
        if (size % 2 == 0) return ((double) comb.get(size / 2) + (double) comb.get(size / 2 - 1)) / 2;  // even
        else return comb.get(size / 2);                         // odd
    }
}
