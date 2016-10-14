package com.leetcode.array;

/**
 * Created by LYuan on 2016/10/14.
 * Given a sorted array and a target value, return the index if the target is found.
 * If not, return the index where it would be if it were inserted in order.
 * You may assume no duplicates in the array.
 * 考察Binary Search。
 *
 * Here are few examples.
 * [1,3,5,6], 5 → 2
 * [1,3,5,6], 2 → 1
 * [1,3,5,6], 7 → 4
 * [1,3,5,6], 0 → 0
 *
 * Function Signature:
 * public int insertPos(int[] a, int target) {...}
 * */
public class E35_Search_Insert_Position {
    public static void main(String[] args) {
        int[] a = {0, 1, 3, 5, 7, 9};
        System.out.println(insertPos(a, 10));
    }

    /** 双指针实现迭代Binary Search: Time - o(logn) */
    // 易疏忽点1：求中点的加法有可能会整型溢出，所以最好使用先减后加的方式。
    // 易疏忽点2：while循环条件应包含左右指针重合的情况，只有左右指针位置交错才退出。
    // 易疏忽点3：即使没有找到，target的理想位置也就刚好等于退出while循环后的left指针位置。
    // Example:
    // target = -1. [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4]     应插入至left所在位置
    //               ↑     ↑     ↑    ↑  ↑             ↑              ↑ ↑
    //               l     m     r   l,m r           l,m,r            r l
    //
    // target = 5.  [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4]     应插入至left所在位置
    //               ↑     ↑     ↑             ↑  ↑                ↑                ↑ ↑
    //               l     m     r            l,m r              l,m,r    move l    r l
    //
    // target = 7.  [0, 2, 4, 6, 8], [0, 2, 4, 6, 8], [0, 2, 4, 6, 8], [0, 2, 4, 6, 8]     应插入至left所在位置
    //               ↑     ↑     ↑             ↑  ↑                ↑              ↑ ↑
    //               l     m     r            l,m r              l,m,r   move r   r l
    static int insertPos(int[] a, int target) {
        if (a == null) return 0;
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == a[mid]) return mid;
            if (target < a[mid]) right = mid - 1;
            if (target > a[mid]) left = mid + 1;
        }
        return left;
//        if (target < a[0]) return 0;
//        if (target > a[a.length - 1]) return a.length;
//        if (target < a[left]) return left;
//        if (target > a[right]) return right;
//        return -1;
    }
}
