package com.leetcode.dp;

import java.util.Arrays;

/**
 * Created by Michael on 2016/11/6.
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 *
 * For example, given [10, 9, 2, 5, 3, 7, 101, 18],
 * The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4.
 * Note that there may be more than one LIS combination, it is only necessary for you to return the length.
 * Your algorithm should run in O(n2) complexity.
 * Follow up: Could you improve it to O(n log n) time complexity?
 *
 * Function Signature:
 * public int longestIncreasingSeq(int[] a) {...}
 */
public class M300_Longest_Increasing_Subsequence {
    public static void main(String[] args) {
        int[] a = new int[] {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(longestIncreasingSeq3(a));
        System.out.println(Arrays.binarySearch(a, 0, a.length, 8));
    }

    /** DP解法1，逆序扫描，Memoization，Time - o(n^2), Space - o(n) */
    // 通过观察发现，从左向右扫描的过程中，后面的元素会被更频繁的扫描，因此可以把这部分重复的运算用DP备忘起来。
    // 逆序扫描，最后一个元素起始的最长上升序列一定是0 + 1. 而对于之前的节点，当前轮状态与之前（位置上是之后）所有轮状态都有关。
    // 只要后面的元素值大于当前元素，就可以构成一个上升序列，因此就可以用到后面元素之前算出来的dp值。
    // 不过即使是这样，也是双for循环，按求和公式有时间复杂度n(n+1)/2
    //   {10,   9,   2,   5,   3,   7,  101,  18}
    //                                         1   |→
    //                                    1    |→
    //                              2     |→    →
    //                         3    |→     →    →
    //                    3    |→    →     →    →
    //               4    |→    →    →     →    →
    //          2    |→    →    →    →     →    →
    //     2    |→    →    →    →    →     →    →
    static int longestIncreasingSeq(int[] a) {
        if (a == null || a.length == 0) return 0;
        int[] dp = new int[a.length + 1];
        int max;
        int result = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            max = 0;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] > a[i]) max = Math.max(dp[j], max);
            }
            dp[i] = max + 1;
            result = Math.max(dp[i], result);
        }
        return result;
    }

    /** DP解法2，正序扫描，Memoization，同样是Time - o(n^2) */
    // 思路与上面解法1类似，
    // 只不过对于正序扫描，每个循环的任务都是把当前元素和前面元素一一比较，找到那些小于当前元素的dp值，取最大值加一，如果没有就直接赋成1
    static int longestIncreasingSeq2(int[] a) {
        if (a == null || a.length == 0) return 0;
        int[] dp = new int[a.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i]) dp[i] = Math.max(dp[j] + 1, dp[i]);
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /** DP解法3，结合Binary Search，可以简化时间复杂度至o(nlogn). */
    // 由于上面的两种算法都涉及到每一轮都要遍历之前i - 1个元素的dp值，然后再选择dp值加一或者1本身。
    // [10, 9, 2, 5, 3, 7, 101, 18, 1]
    // insert at 0, len = 1          10
    // insert at 0, len = 1           9
    // insert at 0, len = 1           2
    // insert at 1, len = 2           2, 5
    // insert at 1, len = 2           2, 3
    // insert at 2, len = 3           2, 3, 7
    // insert at 3, len = 4           2, 3, 7, 101
    // insert at 3, len = 4           2, 3, 7, 18
    // insert at 0, len = 4           1, 3, 7, 18
    static int longestIncreasingSeq3(int[] a) {
        int[] dp = new int[a.length];
        int len = 0;    // 最长上升序列长度初始值

        for(int x : a) {
            int i = Arrays.binarySearch(dp, 0, len, x);     // 确定插入位置
            if(i < 0) i = -(i + 1);                         // 对于没有查到结果的情况，二分查找API会返回-(insertPos + 1)，还原插入位置，直接插入覆盖。
            dp[i] = x;
            if(i == len) len++;                             // 如果插入位置就是当前位置，说明存在上升序列。
        }

        return len;
    }
}
