package com.leetcode.sort;

import java.util.Arrays;

/**
 * Created by LYuan on 2016/9/27.
 *
 * Basic Algorithm: Selection Sort
 * Time - o(n^2)
 * Space - o(1)
 */
public class Basic_Selection_Sort {
    public static void main(String[] args) {
        int[] a = new int[] {9, 7, 6, 5, 4, 2, 3, 8, 1};
        SelectionSort(a);
        System.out.println(Arrays.toString(a));
    }

    // 一开始未排序部分就是原数组，扫描找到最小的元素，与未排序部分的第一个元素交换，
    // 未排序变短，已排序变长，每次扫描都确定出来一个local min。
    static void SelectionSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i; j < a.length; j++) {
                if (a[j] < a[min]) min = j;
            }
            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }
}
