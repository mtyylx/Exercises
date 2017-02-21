package com.leetcode.math;

/**
 * Created by Michael on 2017/2/20.
 *
 * Implement int sqrt(int x). (x >= 0)
 * Compute and return the square root of x.
 *
 * Function Signature:
 * public void sqrt(int x) {...}
 *
 * <Tags>
 * - Binary Search: 二分法将运算规模从o(N)降低至o(logN).
 * - Two Pointers: 左右指针实现二分法扫描。[left → → → mid ← ← ← right]
 * - Integer Overflow: Use "/" instead of "*".
 */
public class E69_Square_Root {
    public static void main(String[] args) {
        System.out.println(sqrt(18));
        System.out.println(sqrt2(18));
    }

    /** 解法1：Binary Search分段扫描。Time - o(logN), Space - o(1). */
    // 难点1：避免整形溢出。使用 mid <= x / mid 而不是 mid * mid <= x，因为后者会出现整形溢出。
    // 难点2：开平方应该向下取整。8虽然已经离9很近了，但是开平方依然应该得到2而不是3。
    // 1  2  3  4  5  6  7  8  9    mid = 4 > 8 / 4, right = mid - 1 = 3
    // ↑        ↑           ↑
    // l        m           r
    // 1  2  3  4  5  6  7  8  9    mid = 2 < 8 / 2, left = mid + 1 = 3, res = 2
    // ↑  ↑  ↑
    // l  m  r
    // 1  2  3  4  5  6  7  8  9    mid = 3 > 8 / 3, right = mid - 1 = 2
    //       ↑
    //     l,m,r
    // 1  2  3  4  5  6  7  8  9    left > right 结束循环.
    //    ↑  ↑
    //    r  l
    static int sqrt(int x) {
        if (x <= 0) return 0;
        int left = 1;
        int right = x;
        int res = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid <= x / mid) {                   // 从左侧逐渐逼近时，实时记录当前中点值
                left = mid + 1;
                res = mid;                          // 记忆
            }
            else right = mid - 1;                   // 从右侧逐渐逼近时，不实时记录。因为开平方应该向下（从左侧）取整而不是向上（从右侧）取整
        }
        return res;
    }

    /** 解法2：常规线性扫描。Time - o(n), Space - o(1). */
    // 需要考虑到 (i + 1) * (i + 1) 溢出的情况，这时候得到的积会小于0。
    static int sqrt2(int x) {
        if (x <= 0) return 0;
        for (int i = 1; i <= x; i++) {
            int square = i * i;
            int next = (i + 1) * (i + 1);
            if (square <= x && (next < 0 || next > x)) return i;
        }
        return -1;
    }
}
