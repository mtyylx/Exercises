package com.leetcode.dp;

/**
 * Created by Michael on 2016/10/29.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
 *
 * For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
 * the contiguous subarray [4,-1,2,1] has the largest sum = 6.
 * click to show more practice.
 * If you have figured out the O(n) solution,
 * try coding another solution using the divide and conquer approach, which is more subtle.
 *
 * Function Signature:
 * public int maxSubarray(int[] a) {...}
 */
public class M53_Maximum_Subarray {
    public static void main(String[] args) {
        System.out.println(maxSubarray(new int[] {-32,-54,-36,62,20,76}));
        System.out.println(maxSubarray2(new int[] {-32,-54,-36,62,20,76}));
    }

    static int maxSubarray2(int[] a) {
        if (a == null || a.length == 0) return 0;
        if (a.length == 1) return a[0];
        if (a.length == 2) return Math.max(Math.max(a[0], a[1]), Math.max(a[1], a[0] + a[1]));
        int prevMax = Math.max(Math.max(a[0], a[1]), Math.max(a[1], a[0] + a[1]));
        int disconnect = 0;
        int connect = 0;
        int prevConn = a[0] + a[1];
        for (int i = 2; i < a.length; i++) {
            disconnect = Math.max(prevMax, a[i]);
            connect = Math.max(prevMax, prevConn + a[i]);
            prevConn = connect;
            prevMax = Math.max(connect, disconnect);
        }
        return prevMax;
    }

    static int maxSubarray(int[] a) {
        if (a == null || a.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            max = Math.max(max, a[i]);
            int sum = a[i];
            for (int j = i + 1; j < a.length; j++) {
                sum += a[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }
}
