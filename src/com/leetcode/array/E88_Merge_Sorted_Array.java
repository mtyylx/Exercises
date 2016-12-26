package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/8/18.
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 *
 * Note:
 * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
 * The number of elements initialized in nums1 and nums2 are m and n respectively.
 * 即：数组的长度并不是有效元素的长度。有效长度之外的区域分布着可以被修改和替换的元素。
 *
 * Solution Signature:
 * public void merge(int[] a, int m, int[] b, int n) {...}
 *
 * <Tags>
 * - Two Pointers: [ ... ← ← ← i ], [ ... ← ← ← j ]
 * - Reverse Scan
 * - Min/Max Sentinel: int x = (y >= 0) ? z : Integer.MIN_VALUE;
 *
 */
public class E88_Merge_Sorted_Array {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 0, 0}; int[] b = {2, 4};
        mergeSortedArray(a, b, 3, 2);
        System.out.println(Arrays.toString(a));
        int[] a1 = {1, 3, 5, 0, 0}; int[] b1 = {2, 4};
        mergeSortedArray2(a1, b1, 3, 2);
        System.out.println(Arrays.toString(a));
    }

    /** 解法2：双指针（分别扫描两个数组） + 逆序扫描。 （与解法1基本思路一样，具体实现略有不同。）
     *  使用极大极小值保护已经扫描完毕的数组。优点是不用分两段扫描。一个while就结束战斗。
     */
    static void mergeSortedArray2(int[] a, int[] b, int m, int n) {
        if (b == null || b.length == 0) return;
        int ptr = m + n - 1;
        int i = m - 1;
        int j = n - 1;
        while (ptr >= 0) {
            int val1 = (i >= 0) ? a[i] : Integer.MIN_VALUE;
            int val2 = (j >= 0) ? b[j] : Integer.MIN_VALUE;
            if (val1 >= val2) { a[ptr--] = val1; i--; }        // 每个循环只能拷贝一个元素，因此只能移动一个指针。
            else              { a[ptr--] = val2; j--; }
        }
    }

    /** 解法1：双指针（分别扫描两个数组） + 逆序扫描。
     *  为了避免两数组其中之一提前扫描完毕导致数组越界，将扫描分为两部分。
     *  一部分是两个数组都未扫描完成时，一部分是其中一个没有扫描完成时。
     */
    // 由于是原位更新数组A，因此需要考虑如果数组A提前于数组B扫描完成的情况下，需要接着把数组B中剩下的元素放入A中。
    // a[x--] = a[i--]这类语句一行等效于三行：a[x] = a[i]; x--; i--;
    static void mergeSortedArray(int[] a, int[] b, int m, int n) {
        int i = m - 1;
        int j = n - 1;
        int x = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (a[i] > b[j])
                a[x--] = a[i--];
            else
                a[x--] = b[j--];
        }
        while (j >= 0)
            a[x--] = b[j--];
    }
}
