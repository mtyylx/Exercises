package com.leetcode.dp;

/**
 * Created by Michael on 2016/11/1.
 * Find the contiguous subarray within an array (containing at least one number) which has the largest product.
 *
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product = 6.
 *
 * Function Signature:
 * public int maxProduct(int[] a) {...}
 */
public class M152_Maximum_Product_Subarray {
    public static void main(String[] args) {
        System.out.println(maxProduct(new int[] {2, -3, -4, -5}));
    }

    /** DP解法，Time - o(n) */
    // 如果a < b，那么一定有a + x < b + x
    // 如果a < b，那么并没有a * x < b * x，反例：-2 * -5 > 2 * -5
    // 乘法和加法最大的不同就在于屌丝(即上个局部最小值)也可以瞬间逆袭(即成为新的局部最大/最小值)。
    // 不仅跟踪最大值，也跟踪最小值
    // 问题转换成为：
    // 已知一个数组的子数组最大积和最小积，如果再向这个数组添加一个元素，那么求新数组的最大积和最小积。
    // 局部最大值只有三个可能：
    // 1. 原数组的最大积prevMaxLocal乘新元素
    // 2. 原数组的最小积prevMinLocal乘新元素
    // 3. 新元素本身
    // 为了使递推能够继续下去，也要求局部最小值，同样是上面的三种可能。
    static int maxProduct(int[] a) {
        if (a == null || a.length == 0) return 0;
        int prevMaxLocal = a[0];
        int prevMinLocal = a[0];
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            int maxLocal = Math.max(Math.max(prevMaxLocal * a[i], prevMinLocal * a[i]), a[i]);
            int minLocal = Math.min(Math.min(prevMaxLocal * a[i], prevMinLocal * a[i]), a[i]);
            max = Math.max(max, maxLocal);
            prevMaxLocal = maxLocal;        // 需要中转变量，因为求maxLocal和minLocal时会修改自己。
            prevMinLocal = minLocal;
        }
        return max;
    }
}
