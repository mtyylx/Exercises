package com.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2016/12/16.
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * nums1 = [1, 3]
 * nums2 = [2]
 * The median is 2.0
 *
 * Example 2:
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 * The median is (2 + 3)/2 = 2.5
 *
 * Function Signature:
 * public double findMedianSortedArrays(int[] nums1, int[] nums2) {...}
 */
public class H4_Median_of_Two_Sorted_Arrays {
    public static void main(String[] args) {
        System.out.println(findMedian(new int[] {2, 3, 5, 7}, new int[] {1, 4, 6}));
        System.out.println(findMedian2(new int[] {2, 3, 5, 7}, new int[] {1, 4, 6}));
        System.out.println(findMedian3(new int[] {2, 3, 5, 7}, new int[] {1, 4, 6}));
    }

    /** 解法2：Binary Search。Time - o(log(m + n)). */
    // 这个问题的主要障碍在于两个已排序数组合并后的中位数与单独两个已排序数组都没有直接关系，而是由这两个数组中元素整体混合的结果决定。
    // 例如[2 3 5 7] [1 4 6]
    //     2   3   5   7
    //   1       4   6        median = 4
    // 例如[1 2 3] [4 5 6]
    //  1  2  3
    //           4  5  6      median = 3.5

    /** 关键1：发掘“中位数”概念的隐藏特性 */
    // 即不管序列长度是奇数还是偶数，一定有下面几个特性：
    // 1. 中位数左右两侧的元素个数一定相等。
    // 2. 中位数左侧的元素一定小于等于中位数，中位数右侧的元素一定大于等于中位数。
    // 3. 基于第二条特性可知，中位数是左侧区域的最大值，是右侧区域的最小值。

    /** 关键2：定义“分割位置”，从而将奇偶长度的情况统一成一种情况处理。 */
    // 虽然通常我们说奇数长度的序列中位数只有一个，而偶数长度序列的中位数则是中间两个的平均值，
    // 但实际上我们可以将奇数长度的序列中位数<看作中间元素与自己的平均值>，即将自己分割为相同的两个。
    // ODD:  [1 7 8]   -> [1 (7|7) 8]
    // EVEN: [1 7 8 9] -> [1 (7|8) 9]
    // 因此对于一个长度为N的序列，它实际可以被分割的方式就应该有(2N + 1)种。
    //     1   7   8      Length = 3
    //   ↑ ↑ ↑ ↑ ↑ ↑ ↑    Cut Ways = 2*3+1 = 7

    /** 关键3：将二分查找应用于确定分割位置上。 */
    // 二分查找本身逻辑很简单，难点在于将它应用在对的地方。
    // 现在我们已经知道序列长度为N的数组的分割位置有2N+1个，但是我们并不需要用线性的顺序一个一个的去尝试着2N+1个选择，而是用分而治之的方式选择。
    static double findMedian2(int[] a, int[] b) {
        int m = a.length;
        int n = b.length;
        if (m > n) return findMedian2(b, a);        // 将情况全统一成a比b短。

        int left = 0;
        int right = m;
        int leftMax;
        int rightMin;
        int i = (left + right) / 2;
        int j = (m + n + 1) / 2 - i;
        while (left <= right) {
            i = (left + right) / 2;
            j = (m + n + 1) / 2 - i;
            if (i > 0 && a[i - 1] > b[j]) right = i - 1;
                //it means i is too large, so decrease i
                //m <= n(we have assumed), i < m ==> j = (m+n+1)/2 - i > (m+n+1)/2 - m >= (2*m+1)/2 - m >= 0
            else if (i < m && b[j - 1] > a[i]) left = i + 1;
                //it means i is too smore.
                //m <= n(we have assumed), i > 0 ==> j = (m+n+1)/2 - i < (m+n+1)/2 <= (2*n+1)/2 <= n
            else break;
        }
        //find left maximum value and find right maximum value
        if (i == 0)         leftMax = b[j-1];
        else if (j == 0)    leftMax = a[i-1];
        else                leftMax = Math.max(a[i - 1], b[j - 1]);
        if ((m + n) % 2 == 1) return leftMax;

        if (i == m)         rightMin = b[j];
        else if (j == n)    rightMin = a[i];
        else                rightMin = Math.min(a[i],b[j]);

        return ((double)leftMax + rightMin) / 2;        // Avoid Overflow
    }

    // 简化解法
    static double findMedian3(int[] a, int[] b) {
        int N1 = a.length;
        int N2 = b.length;
        if (N1 < N2) return findMedian3(b, a);	// Make sure b is the shorter one.

        int lo = 0, hi = N2 * 2;
        while (lo <= hi) {
            int mid2 = (lo + hi) / 2;   // Try Cut 2
            int mid1 = N1 + N2 - mid2;  // Calculate Cut 1 accordingly

            double L1 = (mid1 == 0) ?      Integer.MIN_VALUE : a[(mid1 - 1) / 2];
            double L2 = (mid2 == 0) ?      Integer.MIN_VALUE : b[(mid2 - 1) / 2];
            double R1 = (mid1 == N1 * 2) ? Integer.MAX_VALUE : a[mid1 / 2];
            double R2 = (mid2 == N2 * 2) ? Integer.MAX_VALUE : b[mid2 / 2];

            if (L1 > R2)        lo = mid2 + 1;		// A1's lower half is too big; need to move C1 left (C2 right)
            else if (L2 > R1)   hi = mid2 - 1;	    // A2's lower half too big; need to move C2 left.
            else return (Math.max(L1, L2) + Math.min(R1, R2)) / 2;	// Otherwise, that's the right cut.
        }
        return -1;
    }


    /** 解法1：Naive最简单解法，按顺序合并两个数组，再找中间元素。Time - o(n + m), Space - o(n + m) */
    static double findMedian(int[] a, int[] b) {
        List<Integer> comb = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < a.length || j < b.length) {                  // 管用伎俩：同时扫描两个数组，直至两个数组都扫描完毕。
            int x = i < a.length ? a[i] : Integer.MAX_VALUE;
            int y = j < b.length ? b[j] : Integer.MAX_VALUE;
            if (x < y)  comb.add(a[i++]);
            else        comb.add(b[j++]);
        }
        int size = comb.size();
        if (size == 0) return 0;                                                                        // empty
        if (size % 2 == 0) return ((double) comb.get(size / 2) + (double) comb.get(size / 2 - 1)) / 2;  // even
        else return comb.get(size / 2);                                                                 // odd
    }
}
