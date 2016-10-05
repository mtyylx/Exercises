package com.leetcode.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LYuan on 2016/9/27.
 *
 * Basic Algorithm: Merge Sort
 * Time: o(n * log n) 因为虽然是双循环，但是内循环是二分法，所以是2的对数
 * Space: o(n) 因为merge的过程需要临时数组
 */
public class Basic_Merge_Sort {
    public static void main(String[] args) {
        int[] a = {9, 6, 5, 3, 4, 2, 7, 8, 1};
        MergeSort_Iterative(a);
        System.out.println(Arrays.toString(a));
    }

    /** 迭代解法 - 直接合并 */
    // 直接合并 - BottomUp Iterative：相当于省略了递归方法不断除二分解将数组分段的过程，
    // 直接指定最小比较区段就是1，1比完了比2，2比完了比3，一直比到数组长度N
    // 外循环控制区段长度，内循环用区段长度扫描并按区段长度merge整个数组
    // 难点: 在于如何处理长度不是2的幂的数组的merge
    // 将i设计为merge两个区段各自的长度，而不是总长度，
    // 然后将j跳跃的间隔设计为2倍的i，可以确保mid的值不会越界，再用Math.min确保right的值也不越界
    // len = 9:
    // 0 1 | 2 3 | 4 5 | 6 7 | 8
    // 0 1 2 3 | 4 5 6 7 | 8
    // 0 1 2 3 4 5 6 7 | 8
    // len = 6:
    // 0 1 | 2 3 | 4 5
    // 0 1 2 3 | 4 5
    static void MergeSort_Iterative(int[] a) {
        for (int i = 1; i < a.length; i *= 2)
            for (int j = 0; j < a.length - i; j += 2 * i)
                merge(a, j, j + i - 1, Math.min(j + 2 * i - 1, a.length - 1));
    }

    /** 递归解法 - 先分解再合并 */
    static void MergeSort_Recursive(int[] a) {
        MergeSortRecursive(a, 0, a.length - 1);
    }

    // 先分解再合并 - Topdown Recursive: 逆序递归，即先递归至终点，再在返回的过程中进行Conquer
    static void MergeSortRecursive(int[] a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            MergeSortRecursive(a, left, mid);
            MergeSortRecursive(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    // 使用ArrayList实现merge功能，双指针同时扫描两段，并存入ArrayList，最后由ArrayList覆盖当前区间
    // 用极大值保护先扫描入库完成的那个部分，直至左右两部分都扫完。
    // 算法导论里介绍的方法是先把左右两段元素备份至一个临时数组，然后用双指针扫描更新原数组，
    // 下面的解法则是直接双指针扫描数组两段，存入临时数组，然后顺序扫描更新现有数组，本质上是一样的，不过感觉我的方法写起来更简单些
    static void merge(int[] a, int left, int mid, int right) {
        List<Integer> list = new ArrayList<>(right - left + 1);
        int i = left;
        int j = mid + 1;
        while (i <= mid || j <= right) {
            int x, y;
            if (i <= mid) x = a[i];
            else x = Integer.MAX_VALUE;
            if (j <= right) y = a[j];
            else y = Integer.MAX_VALUE;
            list.add(Math.min(x, y));
            if (x <= y) i++;
            else j++;
        }
        int idx = left;
        for (int element : list) {
            a[idx++] = element;
        }
    }
}
