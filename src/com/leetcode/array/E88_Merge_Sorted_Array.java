package com.leetcode.array;

/**
 * Created by LYuan on 2016/8/18.
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 *
 * Note:
 * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
 * The number of elements initialized in nums1 and nums2 are m and n respectively.
 *
 * Solution Signature:
 * public void merge(int[] a, int m, int[] b, int n) {...}
 */
public class E88_Merge_Sorted_Array {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 0, 0};
        int[] b = {2, 4};
        mergeSortedArray(a, 3, b, 2);
        for (int x : a) {
            System.out.print(x + ",");
        }
    }

    // 逆序扫描+双指针解法。使用while循环更方便检测数组扫描结束的信号，以防越界。
    // 由于是原位更新数组A，因此需要考虑如果数组A提前于数组B扫描完成的情况下，需要接着把数组B中剩下的元素放入A中。
    // a[x--] = a[i--]这类语句一行等效于三行：a[x] = a[i]; x--; i--;
    static void mergeSortedArray(int[] a, int m, int[] b, int n) {
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
