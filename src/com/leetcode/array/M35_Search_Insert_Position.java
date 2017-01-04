package com.leetcode.array;

/**
 * Created by LYuan on 2016/10/14.
 * Given a sorted array and a target value, return the index if the target is found.
 * If not, return the index where it would be if it were inserted in order.
 * You may assume no duplicates in the array.
 *
 * Here are few examples.
 * [1,3,5,6], 5 → 2
 * [1,3,5,6], 2 → 1
 * [1,3,5,6], 7 → 4
 * [1,3,5,6], 0 → 0
 *
 * Function Signature:
 * public int insertPos(int[] a, int target) {...}
 *
 * <系列问题>
 * M35  Search Insert Position: 给定一个已排序数组a和一个目标值k，求k在a中出现的位置值。如果k不在a中，求将k插入a中后让a依然保持有序的位置值。
 * M34  Search In Range:        给定一个已排序数组a和一个目标值，求k在a中出现的起始和终止位置值。如果k不在a中，则返回[-1, -1]
 * E278 First Bad Version:      给定一个取值范围1至n（即有序）和一个判定函数，求让判定函数第一次返回true的位置索引。
 * E374 Guess Number:           给定一个取值范围1至n（即有序）和一个判定函数，求让判定函数返回0的位置索引。
 *
 * <Tags>
 * - Binary Search
 * - Two Pointers: [left → → → ... ← ← ← right]
 *
 */
public class M35_Search_Insert_Position {
    public static void main(String[] args) {
        int[] a = {0, 1, 3, 5, 7, 9};
        System.out.println(insertPos(a, 10));
        System.out.println(insertPos2(a, 10));
    }

    /** 本质是对于二分查找的实现。具体分析可见Basic_Binary_Search.java */
    // 易疏忽点1：求中点的加法有可能会整型溢出，所以最好使用先减后加的方式。
    // 易疏忽点2：while循环条件应包含左右指针重合的情况，只有左右指针位置交错才退出。
    // 易疏忽点3：即使没有找到，target的理想位置也就刚好等于退出while循环后的left指针位置。
    // Example: 证明left刚好处于待插入位置。
    // 以下四种情况涵盖了left/right指针的所有可能情况，可以看到left最终的位置都能确保是正确的插入位置。
    // target = 0 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [0, 1, 4, 7]
    //             ↑   ↑   ↑      ↑            ↑ ↑
    //             l   m   r    l,m,r          r l
    //
    // target = 8 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [1, 4, 7, 8]
    //             ↑   ↑   ↑              ↑              ↑ ↑
    //             l   m   r            l,m,r            r l
    //
    // target = 3 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [1, 3, 4, 7]
    //             ↑   ↑   ↑      ↑              ↑   ↑
    //             l   m   r    l,m,r            r   l
    //
    // target = 6 [1,  4,  7] -> [1,  4,  7] -> [1,  4,  7] -> [1, 4, 6, 7]
    //             ↑   ↑   ↑              ↑          ↑   ↑
    //             l   m   r            l,m,r        r   l

    /** 解法2：双指针相向扫描，迭代写法。Time - o(logn) */
    static int insertPos(int[] a, int target) {
        if (a == null || a.length == 0) return 0;
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if      (a[mid] > target) right = mid - 1;
            else if (a[mid] < target) left = mid + 1;
            else return mid;
        }
        return left;
    }

    /** 解法1：双指针相向扫描，递归写法。Time - o(logn) */
    static int insertPos2(int[] a, int target) {
        if (a == null || a.length == 0) return 0;
        return helper(a, target, 0, a.length - 1);
    }

    static int helper(int[] a, int target, int left, int right) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            if      (a[mid] < target) return helper(a, target, mid + 1, right);
            else if (a[mid] > target) return helper(a, target, left, mid - 1);
            else return mid;
        }
        return left;
    }
}
