package com.leetcode.array;

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
 * */
public class E283_Move_Zeroes {
    public static void main(String[] args) {
        int[] a = {0, 1, 0, 2, 13, 0, 4, 5, 6, 0, 0, 0};
        moveZeroes2(a);
    }

    // 双指针解法，o(n)
    // 自由指针j计算当下已扫描的非零元素长度
    // 循环指针i依次扫描每个元素，遇到非零元素就把该元素与当前已扫描部分的最后一个元素交换，并增加已扫描长度
    static void moveZeroes(int[] a) {
        int temp;
        int j = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != 0) {
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                j++;
            }
        }
    }

    // 常规的插入修改法，需要收尾归零。
    static void moveZeroes2(int[] a) {
        int insert = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != 0)
                a[insert++] = a[i];
        }
        while (insert < a.length) {
            a[insert++] = 0;
        }
    }
}
