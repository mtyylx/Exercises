package com.leetcode.hashtable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/8/31.
 * Given two arrays, write a function to compute their intersection.
 *
 * Example:
 * Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
 *
 * Note:
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 *
 * Follow up:
 * What if the given array is already sorted? How would you optimize your algorithm?
 * 答：如果两个数组都已经有序，那么使用two pointer可以达到o(min(m, n))
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * 答：如果一长一短的话，用two pointer为o(short)，用二分查找则为o(short * log long)
 * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
 *
 * Function Signature:
 * public int[] intersection(int[] a, int[] b) {...}
 *
 * */
public class E350_Intersection_of_Arrays_2 {
    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 4, 66, 77, 77, 85};
        int[] b = {4, 77, 77, 85, 85};
        int[] result = intersection(a, b);
    }

    // 双指针解法，假设俩数组都已有序, o(min(m, n))
    static int[] intersection(int[] a, int[] b) {
        int i = 0;
        int j = 0;
        List<Integer> list = new ArrayList<>();
        while (i < a.length && j < b.length) {
            if      (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else    {
                list.add(a[i]);
                i++;
                j++;
            }
        }
        int[] result = new int[list.size()];
        int idx = 0;
        for (int x : list) {
            result[idx++] = x;
        }
        return result;
    }
}
