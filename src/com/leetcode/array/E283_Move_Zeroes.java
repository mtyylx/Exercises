package com.leetcode.array;

import java.util.Arrays;

/**
 * Created by Michael on 2016/8/23.
 * Given an array nums, write a function to move all 0's to the end of it,
 * while maintaining the relative order of the non-zero elements.
 *
 * For example,
 * given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 *
 * Function Signature:
 * public void moveZeroes(int[] a) {...}
 *
 * <Tags>
 * - Two Pointers (Slow Fast Pointer): [slow → ... fast → → → ... ]
 *
 */
public class E283_Move_Zeroes {
    public static void main(String[] args) {
        int[] a = {0, 1, 0, 2, 13, 0, 4, 5, 6};
        moveZeroes(a);
        System.out.println(Arrays.toString(a));
        int[] b = {0, 1, 0, 2, 13, 0, 4, 5, 6};
        moveZeroes2(b);
        System.out.println(Arrays.toString(b));
        int[] c = {0, 1, 0, 2, 13, 0, 4, 5, 6};
        moveZeroes3(c);
        System.out.println(Arrays.toString(c));
    }

    /** 解法1：双指针（快慢指针），元素交换，一次扫描。Time - o(n) */
    // 快指针j在非零元素停留，并与慢指针i交换内容，慢指针i永远指向已扫描部分的下一个位置
    // [0, 1, 0, 2, 13, 0, 4, 5, 6] 1 - 0
    // [1, 0, 0, 2, 13, 0, 4, 5, 6] 2 - 0
    // [1, 2, 0, 0, 13, 0, 4, 5, 6] 13 - 0
    // [1, 2, 13, 0, 0, 0, 4, 5, 6] 4 - 0
    // [1, 2, 13, 4, 0, 0, 0, 5, 6] 5 - 0
    // [1, 2, 13, 4, 5, 0, 0, 0, 6] 6 - 0
    // [1, 2, 13, 4, 5, 6, 0, 0, 0]
    static void moveZeroes(int[] a) {
        if (a == null) return;                  // avoid a.length cause null pointer exception
        int i = 0;                              // Slow Pointer i
        for (int j = 0; j < a.length; j++) {    // Fast Pointer j （注意这里不能用foreach，因为涉及到修改iterator数值无法做到）
            if (a[j] != 0) {
                a[j] ^= a[i];
                a[i] ^= a[j];
                a[j] ^= a[i];
                i++;
            }
        }
    }

    /** 解法2：双指针（快慢指针），元素覆盖，两次扫描。Time - o(n). */
    // 快指针停在非零元素，覆盖至慢指针所在元素。最后把慢指针右侧的元素全部清零。
    static void moveZeroes2(int[] a) {
        if (a == null) return;
        int i = 0;                                // slow
        for (int j = 0; j < a.length; j++)        // fast
            if (a[j] != 0) a[i++] = a[j];
        while (i < a.length) a[i++] = 0;          // 清理尾零
    }

    /** 解法3：单指针，逆序扫描并依次平移，类似于插入排序。Time - o(n^2). 不如前两种方法。 */
    // 逆序扫描，遇到0元素就把右侧所有元素左移一位，并在最后置0，直至数组开头。
    static void moveZeroes3(int[] a) {
        int count = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] != 0) continue;
            for (int j = i + 1; j < a.length; j++) {
                a[j - 1] = a[j];
            }
            a[a.length - (count++) - 1] = 0;
        }
    }
}
