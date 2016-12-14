package com.leetcode.array;

/**
 * Created by Michael on 2016/12/14.
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
 * Note: You may not 倾斜 the container.
 *
 * Function Signature:
 * public int maxContainer(int[] a) {...}
 *
 */
public class M11_Container_With_Most_Water {
    public static void main(String[] args) {
        System.out.println(maxContainer(new int[] {5, 3, 6, 6, 8, 6, 12, 8}));
    }

    /** 解法1：最朴素解法。双指针遍历。Time - o(n^2), Space - o(1) */
    static int maxContainer(int[] a) {
        int max = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                max = Math.max(max, (j - i) * Math.max(a[i], a[j]));
            }
        }
        return max;
    }
}
